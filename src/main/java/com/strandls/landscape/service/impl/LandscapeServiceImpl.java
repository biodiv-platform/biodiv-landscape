package com.strandls.landscape.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.strandls.landscape.dao.LandscapeDao;
import com.strandls.landscape.pojo.FieldContent;
import com.strandls.landscape.pojo.FieldTemplate;
import com.strandls.landscape.pojo.Landscape;
import com.strandls.landscape.pojo.PageField;
import com.strandls.landscape.pojo.TemplateHeader;
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
	public LandscapeServiceImpl(LandscapeDao dao) {
		super(dao);
	}

	public TemplateTreeStructure getPageStructure(Long protectedAreaId, Long languageId) {

		List<FieldTemplate> fieldTemplate = fieldTemplateService.findAll();

		// sent -1 as template Id as initial parentId
		TemplateTreeStructure treeStructure = getTreeStructure(fieldTemplate, -1L, languageId, protectedAreaId);
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

		if (templateId == -1L) {
			treeStructure.setHeader("root");
			treeStructure.setContent("root");
			return treeStructure;
		}
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
		fieldContentService.save(new FieldContent(null, pageField.getId(), languageId, content, false));

		TemplateTreeStructure rootNode = new TemplateTreeStructure(pageField.getTemplateId());
		TemplateHeader header = templateHeaderService.getHeader(pageField.getTemplateId(), languageId);
		rootNode.setHeader(header.getHeader());
		rootNode.setContent(content);
		return rootNode;
	}

	@Override
	public Landscape save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Landscape landscape = objectMapper.readValue(jsonString, Landscape.class);
		landscape = save(landscape);

		List<FieldTemplate> fieldTemplates = fieldTemplateService.findAll();
		Long authorId = null;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		for (FieldTemplate fieldTemplate : fieldTemplates) {
			pageFieldService.save(new PageField(null, fieldTemplate.getId(), landscape.getId(), authorId, timestamp,
					timestamp, false));
		}
		return landscape;
	}
}
