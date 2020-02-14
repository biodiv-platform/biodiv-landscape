package com.strandls.landscape.service.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.strandls.landscape.dao.FieldTemplateDao;
import com.strandls.landscape.pojo.FieldTemplate;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.FieldTemplateService;

public class FieldTemplateServiceImpl extends AbstractService<FieldTemplate> implements FieldTemplateService{

	@Inject private ObjectMapper objectMapper;
	
	@Inject
	public FieldTemplateServiceImpl(FieldTemplateDao dao) {
		super(dao);
	}
	
	public FieldTemplate save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		FieldTemplate fieldTemplate = objectMapper.readValue(jsonString, FieldTemplate.class);	
		fieldTemplate = save(fieldTemplate);
		return fieldTemplate;
	}
}
