package com.strandls.landscape.service.impl;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.landscape.dao.FieldContentDao;
import com.strandls.landscape.pojo.FieldContent;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.FieldContentService;

public class FieldContentServiceImpl extends AbstractService<FieldContent> implements FieldContentService{

	@Inject
	public FieldContentServiceImpl(FieldContentDao dao) {
		super(dao);
	}
	
	@Override
	public FieldContent saveOrUpdate(Long pageFieldId, Long languageId, String content) throws JsonParseException, JsonMappingException, IOException, JSONException {
		FieldContent fieldContent;
		try {
			fieldContent = getFieldContent(pageFieldId, languageId);
			fieldContent.setContent(content);
			fieldContent = update(fieldContent);
		} catch(NoResultException e) {
			fieldContent = new FieldContent(null, pageFieldId, languageId, content, false);
			fieldContent = save(fieldContent);
		}
		return fieldContent;
	}
	
	@Override
	public FieldContent update(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		Long pageFieldId = jsonObject.getLong("id");
		Long languageId = jsonObject.getLong("languageId");
		String content = jsonObject.getString("content");
		return saveOrUpdate(pageFieldId, languageId, content);
	}

	@Override
	public FieldContent getFieldContent(Long pageFieldId, Long languageId) {
		return ((FieldContentDao) dao).getFieldContent(pageFieldId, languageId);
	}
}
