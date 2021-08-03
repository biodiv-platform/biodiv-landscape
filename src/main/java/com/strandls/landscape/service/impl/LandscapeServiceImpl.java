package com.strandls.landscape.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.json.JSONException;
import org.json.JSONObject;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.geoentities.ApiException;
import com.strandls.geoentities.controllers.GeoentitiesServicesApi;
import com.strandls.geoentities.pojo.GeoentitiesWKTData;
import com.strandls.landscape.dao.LandscapeDao;
import com.strandls.landscape.pojo.DownloadLog;
import com.strandls.landscape.pojo.FieldContent;
import com.strandls.landscape.pojo.FieldTemplate;
import com.strandls.landscape.pojo.Landscape;
import com.strandls.landscape.pojo.PageField;
import com.strandls.landscape.pojo.TemplateHeader;
import com.strandls.landscape.pojo.request.FieldContentData;
import com.strandls.landscape.pojo.response.LandscapeShow;
import com.strandls.landscape.pojo.response.TemplateTreeStructure;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.DownloadLogService;
import com.strandls.landscape.service.FieldContentService;
import com.strandls.landscape.service.FieldTemplateService;
import com.strandls.landscape.service.LandscapeService;
import com.strandls.landscape.service.PageFieldService;
import com.strandls.landscape.service.TemplateHeaderService;

public class LandscapeServiceImpl extends AbstractService<Landscape> implements LandscapeService {

	private static final Logger logger = LoggerFactory.getLogger(LandscapeServiceImpl.class);

	@Inject
	private ObjectMapper objectMapper;
	@Inject
	private PageFieldService pageFieldService;
	@Inject
	private FieldContentService fieldContentService;
	@Inject
	private FieldTemplateService fieldTemplateService;
	@Inject
	private TemplateHeaderService templateHeaderService;

	@Inject
	private GeoentitiesServicesApi geoentitiesServicesApi;

	@Inject
	private DownloadLogService downloadLogService;

	private static final Long PARENT_ID = 0L;

	private static Properties properties;
	private static String rootPath;

	static {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");

		properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		rootPath = (String) properties.get("download.path");
	}

	@Inject
	public LandscapeServiceImpl(LandscapeDao dao) {
		super(dao);
	}

	@Override
	public LandscapeShow showPageBySiteNumber(Long siteNumber, Long languageId) throws ApiException {
		Landscape landscape = ((LandscapeDao) dao).findBySiteNumber(siteNumber);
		return getShowPage(landscape.getId(), languageId);
	}

	@Override
	public LandscapeShow getShowPage(Long protectedAreaId, Long languageId) throws ApiException {
		String wktData = getWKT(protectedAreaId);
		List<List<Object>> boundingBox = getBoundingBox(protectedAreaId);
		TemplateTreeStructure treeStructure = getPageStructure(protectedAreaId, languageId);
		return new LandscapeShow(wktData, boundingBox, treeStructure);
	}

	public TemplateTreeStructure getPageStructure(Long protectedAreaId, Long languageId) {

		List<FieldTemplate> fieldTemplate = fieldTemplateService.findAll();

		// sent -1 as template Id as initial parentId
		return getTreeStructure(fieldTemplate, PARENT_ID, languageId, protectedAreaId);
	}

	private TemplateTreeStructure getTreeStructure(List<FieldTemplate> fieldTemplates, Long templateId, Long languageId,
			Long protectedAreaId) {

		List<FieldTemplate> childs = new ArrayList<>();

		for (FieldTemplate fieldTemplate : fieldTemplates) {
			if (fieldTemplate.getParentId().equals(templateId))
				childs.add(fieldTemplate);
		}

		childs.sort((o1, o2) -> (int) (o1.getFieldIndex() - o2.getFieldIndex()));

		TemplateTreeStructure treeStructure = new TemplateTreeStructure(templateId);
		for (FieldTemplate fieldTemplate : childs) {
			treeStructure
					.addChild(getTreeStructure(fieldTemplates, fieldTemplate.getId(), languageId, protectedAreaId));
		}

		if (PARENT_ID.equals(templateId)) {
			treeStructure.setHeader("root");
			treeStructure.setContent("root");
			return treeStructure;
		}
		PageField pageField = pageFieldService.getPageField(protectedAreaId, templateId);
		treeStructure.setPageFieldId(pageField.getId());
		try {
			FieldContent fieldContent = fieldContentService.getFieldContent(pageField.getId(), languageId);
			treeStructure.setContent(fieldContent.getContent());
		} catch (NoResultException e) {
			treeStructure.setContent("");
		}

		TemplateHeader templateHeader = templateHeaderService.getHeader(templateId, languageId);
		if (templateHeader != null)
			treeStructure.setHeader(templateHeader.getHeader());

		return treeStructure;
	}

	@Override
	public Map<String, Object> uploadFile(HttpServletRequest request, FormDataMultiPart multiPart) throws IOException {

		FormDataBodyPart formdata = multiPart.getField("csv");
		if (formdata == null) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity("Metadata file not present").build());
		}
		InputStream metaDataInputStream = formdata.getValueAs(InputStream.class);
		InputStreamReader inputStreamReader = new InputStreamReader(metaDataInputStream, StandardCharsets.UTF_8);
		CSVReader reader = new CSVReader(inputStreamReader);

		Iterator<String[]> it = reader.iterator();
		it.next(); // Just iterate it for the header

		Map<String, Object> result = new HashMap<>();
		while (it.hasNext()) {
			String[] data = it.next();
			Long protectedAreaId = Long.parseLong(data[0]);
			Long languageId = Long.parseLong(data[1]);
			Long templateId = Long.parseLong(data[2]);
			String content = data[3];

			FieldContentData field = new FieldContentData(protectedAreaId, languageId, templateId, content);
			try {
				saveField(request, field);
			} catch (IOException e) {
				result.put(content, "Exception while saving content" + e.getMessage());
			}
		}
		reader.close();
		return result;
	}

	@Override
	public TemplateTreeStructure saveField(HttpServletRequest request, FieldContentData fieldContentData)
			throws IOException {

		Long languageId = fieldContentData.getLanguageId();
		String content = fieldContentData.getContent();
		Long templateId = fieldContentData.getTemplateId();
		Long protectedAreaId = fieldContentData.getProtectedAreaId();

		PageField pageField = pageFieldService.getPageField(protectedAreaId, templateId);
		fieldContentService.saveOrUpdate(pageField.getId(), languageId, content);

		TemplateTreeStructure rootNode = new TemplateTreeStructure(pageField.getTemplateId());
		TemplateHeader header = templateHeaderService.getHeader(pageField.getTemplateId(), languageId);
		rootNode.setHeader(header.getHeader());
		rootNode.setContent(content);
		rootNode.setPageFieldId(pageField.getId());
		return rootNode;
	}

	@Override
	public Landscape save(String jsonString) throws IOException, JSONException, ApiException {

		JSONObject jsonObject = new JSONObject(jsonString);

		Long geoEntityId = null;

		String wkt = null;
		if (jsonObject.has("wkt")) {
			wkt = (String) jsonObject.remove("wkt");
		}

		jsonString = jsonObject.toString();
		Landscape landscape = objectMapper.readValue(jsonString, Landscape.class);
		String placeName = landscape.getShortName();

		if (wkt != null) {
			GeoentitiesWKTData geoentitiesWKTData = new GeoentitiesWKTData();
			geoentitiesWKTData.setPlaceName(placeName);
			geoentitiesWKTData.setWktData(wkt);
			GeoentitiesWKTData geoentities = geoentitiesServicesApi.createGeoentities(geoentitiesWKTData);
			geoEntityId = geoentities.getId();
		}

		landscape.setGeoEntityId(geoEntityId);
		landscape = save(landscape);

		// Adding Field templates for each page
		List<FieldTemplate> fieldTemplates = fieldTemplateService.findAll();
		Long authorId = null;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		for (FieldTemplate fieldTemplate : fieldTemplates) {
			pageFieldService.save(new PageField(null, fieldTemplate.getId(), landscape.getId(), authorId, timestamp,
					timestamp, false));
		}
		return landscape;
	}

	@Override
	public Landscape updateThumbnail(Long protectedAreaId) throws ApiException {
		Landscape landscape = findById(protectedAreaId);
		Long geoEntityId = landscape.getGeoEntityId();
		if (geoEntityId != null) {
			Map<String, Object> thumbnailPath = geoentitiesServicesApi.getImagePathFromGeoEntities(geoEntityId + "");
			String uri = thumbnailPath.get("uri").toString();
			landscape.setThumbnailPath(uri);
		}
		update(landscape);
		return landscape;
	}

	@Override
	public List<Landscape> updateThumbnailForAllLandscape() throws ApiException {
		List<Landscape> landscapes = findAll();
		for (Landscape landscape : landscapes) {
			updateThumbnail(landscape.getId());
		}
		return findAll();
	}

	@Override
	public List<List<Object>> getBoundingBox(Long protectedAreaId) throws ApiException {
		Landscape landscape = findById(protectedAreaId);
		Long geoEntityId = landscape.getGeoEntityId();
		return geoentitiesServicesApi.getBoundingBox(geoEntityId);
	}

	@Override
	public Landscape updateWKT(Long protectedAreaId, String wkt) throws ApiException {
		Landscape landscape = findById(protectedAreaId);
		Long geoEntityId = landscape.getGeoEntityId();
		geoentitiesServicesApi.updateGeoentitiesById(geoEntityId + "", wkt);
		return landscape;
	}

	@Override
	public String getWKT(Long protectedAreaId) throws ApiException {
		Landscape landscape = findById(protectedAreaId);
		Long geoEntityId = landscape.getGeoEntityId();
		GeoentitiesWKTData geoEntity = geoentitiesServicesApi.findGeoentitiesById(geoEntityId + "");
		return geoEntity.getWktData();
	}

	@Override
	public File downloadLandscape(HttpServletRequest request, Long protectedAreaId, String type)
			throws ApiException, IOException {

		Landscape landscape = findById(protectedAreaId);
		Long geoEntityId = landscape.getGeoEntityId();
		String data = "";

		File file = null;
		if ("WKT".equalsIgnoreCase(type)) {
			GeoentitiesWKTData geoEntity = geoentitiesServicesApi.findGeoentitiesById(geoEntityId + "");
			data = geoEntity.getWktData();
			file = createNewFile(data, protectedAreaId, type);
		} else if ("GEOJSON".equalsIgnoreCase(type)) {
			data = geoentitiesServicesApi.getGeoJsonById(geoEntityId + "");
			file = createNewFile(data, protectedAreaId, type);
		} else if ("PNG".equalsIgnoreCase(type)) {
			file = geoentitiesServicesApi.getImageFromGeoEntities(geoEntityId, 500, 500, null, null);
		}

		if (file != null)
			logDownload(request, file, protectedAreaId, type);
		return file;
	}

	private void logDownload(HttpServletRequest request, File file, Long protectedAreaId, String type)
			throws IOException {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		if (profile == null)
			throw new IOException("User session is required");
		String paramsAsText = "{protectedAreaId : " + protectedAreaId + "}";
		Long autherId = Long.parseLong(profile.getId());
		Timestamp createdOn = new Timestamp(new Date().getTime());

		DownloadLog downloadLog = new DownloadLog();
		downloadLog.setVersion(2L);
		downloadLog.setAuthorId(autherId);
		downloadLog.setCreatedOn(createdOn);
		downloadLog.setFilePath(file.getAbsolutePath());
		downloadLog.setFilterUrl(request.getRequestURI());
		downloadLog.setNotes("");
		downloadLog.setParamsMapAsText(paramsAsText);
		downloadLog.setStatus("Success");
		downloadLog.setType(type);
		downloadLog.setSourceType("Landscape");
		downloadLog.setOffsetParam(0L);

		downloadLogService.save(downloadLog);
	}

	private File createNewFile(String wktData, Long protecteAreaId, String type) throws IOException {
		String randomUUID = UUID.randomUUID().toString();
		String pathname = rootPath + File.separator + randomUUID;
		File dir = new File(pathname);
		if (!dir.exists())
			dir.mkdir();
		pathname = pathname + File.separator + protecteAreaId + "_" + type + ".txt";
		File file = new File(pathname);
		if (file.exists())
			return file;
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(wktData);
		fileWriter.close();
		return file;
	}
}
