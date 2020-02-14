package com.strandls.landscape.service.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.strandls.landscape.dao.FieldContentDao;
import com.strandls.landscape.pojo.FieldContent;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.FieldContentService;

public class FieldContentServiceImpl extends AbstractService<FieldContent> implements FieldContentService{

	@Inject private ObjectMapper objectMapper;
	
	@Inject
	public FieldContentServiceImpl(FieldContentDao dao) {
		super(dao);
	}

	@Override
	public FieldContent save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		FieldContent fieldContent = objectMapper.readValue(jsonString, FieldContent.class);	
		fieldContent = save(fieldContent);
		return fieldContent;
	}

	@Override
	public FieldContent getFieldContent(Long pageFieldId, Long languageId) {
		return ((FieldContentDao) dao).getFieldContent(pageFieldId, languageId);
	}
}
