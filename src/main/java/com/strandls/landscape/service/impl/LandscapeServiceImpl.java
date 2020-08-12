package com.strandls.landscape.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strandls.geoentities.ApiException;
import com.strandls.geoentities.controllers.GeoentitiesServicesApi;
import com.strandls.geoentities.pojo.GeoentitiesWKTData;
import com.strandls.landscape.dao.LandscapeDao;
import com.strandls.landscape.pojo.FieldContent;
import com.strandls.landscape.pojo.FieldTemplate;
import com.strandls.landscape.pojo.Landscape;
import com.strandls.landscape.pojo.PageField;
import com.strandls.landscape.pojo.TemplateHeader;
import com.strandls.landscape.pojo.response.LandscapeShow;
import com.strandls.landscape.pojo.response.TemplateTreeStructure;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.FieldContentService;
import com.strandls.landscape.service.FieldTemplateService;
import com.strandls.landscape.service.LandscapeService;
import com.strandls.landscape.service.PageFieldService;
import com.strandls.landscape.service.TemplateHeaderService;

public class LandscapeServiceImpl extends AbstractService<Landscape> implements LandscapeService {

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
	
	private static final Long PARENT_ID = 0L;

	@Inject
	public LandscapeServiceImpl(LandscapeDao dao) {
		super(dao);
	}
	
	@Override
	public LandscapeShow showPageBySiteNumber(Long siteNumber, Long languageId) throws ApiException {
		Landscape  landscape = findByPropertyWithCondtion("siteNumber", siteNumber, "=");
		return getShowPage(landscape.getId(), languageId);
	}
	
	@Override
	public LandscapeShow getShowPage(Long protectedAreaId, Long languageId) throws ApiException {
		String wktData = getWKT(protectedAreaId);
		List<List<Object>> boundingBox = getBoundingBox(protectedAreaId);
		TemplateTreeStructure treeStructure = getPageStructure(protectedAreaId, languageId);
		LandscapeShow landscapeShow = new LandscapeShow(wktData, boundingBox, treeStructure);
		return landscapeShow;
	}

	public TemplateTreeStructure getPageStructure(Long protectedAreaId, Long languageId) {

		List<FieldTemplate> fieldTemplate = fieldTemplateService.findAll();

		// sent -1 as template Id as initial parentId
		TemplateTreeStructure treeStructure = getTreeStructure(fieldTemplate, PARENT_ID, languageId, protectedAreaId);
		return treeStructure;
	}

	private TemplateTreeStructure getTreeStructure(List<FieldTemplate> fieldTemplates, Long templateId, Long languageId,
			Long protectedAreaId) {

		List<FieldTemplate> childs = new ArrayList<FieldTemplate>();

		for (FieldTemplate fieldTemplate : fieldTemplates) {
			if (fieldTemplate.getParentId().equals(templateId))
				childs.add(fieldTemplate);
		}

		childs.sort(new Comparator<FieldTemplate>() {
			@Override
			public int compare(FieldTemplate o1, FieldTemplate o2) {
				return (int) (o1.getFieldIndex() - o2.getFieldIndex());
			}
		});

		TemplateTreeStructure treeStructure = new TemplateTreeStructure(templateId);
		for (FieldTemplate fieldTemplate : childs) {
			treeStructure
					.addChild(getTreeStructure(fieldTemplates, fieldTemplate.getId(), languageId, protectedAreaId));
		}

		if (templateId == PARENT_ID) {
			treeStructure.setHeader("root");
			treeStructure.setContent("root");
			return treeStructure;
		}
		System.out.println(protectedAreaId + " " + templateId);
		PageField pageField = pageFieldService.getPageField(protectedAreaId, templateId);
		treeStructure.setPageFieldId(pageField.getId());
		if (pageField != null) {
			try {
				FieldContent fieldContent = fieldContentService.getFieldContent(pageField.getId(), languageId);
				treeStructure.setContent(fieldContent.getContent());
			} catch (NoResultException e) {
				treeStructure.setContent("");
			}
		}

		TemplateHeader templateHeader = templateHeaderService.getHeader(templateId, languageId);
		if (templateHeader != null)
			treeStructure.setHeader(templateHeader.getHeader());

		return treeStructure;
	}

	@Override
	public TemplateTreeStructure saveField(HttpServletRequest request, String jsonString)
			throws JSONException, JsonParseException, JsonMappingException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		Long languageId = Long.parseLong(jsonObject.get("languageId").toString());
		String content = jsonObject.get("content").toString();
		Long templateId = Long.parseLong(jsonObject.get("templateId").toString());
		Long protectedAreaId = Long.parseLong(jsonObject.get("protectedAreaId").toString());

		// PageField pageField = pageFieldService.save(jsonObject.toString());
		PageField pageField = pageFieldService.getPageField(protectedAreaId, templateId);
		fieldContentService.saveOrUpdate(pageField.getId(), languageId, content);
		//fieldContentService.save(new FieldContent(null, pageField.getId(), languageId, content, false));

		TemplateTreeStructure rootNode = new TemplateTreeStructure(pageField.getTemplateId());
		TemplateHeader header = templateHeaderService.getHeader(pageField.getTemplateId(), languageId);
		rootNode.setHeader(header.getHeader());
		rootNode.setContent(content);
		rootNode.setPageFieldId(pageField.getId());
		return rootNode;
	}

	@Override
	public Landscape save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException, ApiException {

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
			System.out.println(placeName);
			System.out.println(wkt);
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
		if(geoEntityId != null) {
			Map<String, Object> thumbnailPath = geoentitiesServicesApi.getImagePathFromGeoEntities(geoEntityId+"");
			String url = thumbnailPath.get("url").toString();
			landscape.setThumbnailPath(url);
		}
		update(landscape);
		return landscape;
	}
	
	@Override
	public List<Landscape> updateThumbnailForAllLandscape() throws ApiException {
		List<Landscape> landscapes = findAll();
		for(Landscape landscape : landscapes) {
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
		geoentitiesServicesApi.updateGeoentitiesById(geoEntityId+"", wkt);
		return landscape;
	}

	@Override
	public String getWKT(Long protectedAreaId) throws ApiException {
		Landscape landscape = findById(protectedAreaId);
		Long geoEntityId = landscape.getGeoEntityId();
		GeoentitiesWKTData geoEntity = geoentitiesServicesApi.findGeoentitiesById(geoEntityId+"");
		return geoEntity.getWktData();
	}
}
