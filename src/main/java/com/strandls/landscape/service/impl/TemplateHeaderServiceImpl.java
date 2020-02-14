package com.strandls.landscape.service.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.strandls.landscape.dao.TemplateHeaderDao;
import com.strandls.landscape.pojo.TemplateHeader;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.TemplateHeaderService;

public class TemplateHeaderServiceImpl extends AbstractService<TemplateHeader> implements TemplateHeaderService{

	@Inject private ObjectMapper objectMapper;
	
	@Inject
	public TemplateHeaderServiceImpl(TemplateHeaderDao dao) {
		super(dao);
	}

	@Override
	public TemplateHeader save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		TemplateHeader templateHeader = objectMapper.readValue(jsonString, TemplateHeader.class);	
		templateHeader = save(templateHeader);
		return templateHeader;
	}

	@Override
	public TemplateHeader getHeader(Long templateId, Long languageId) {
		return ((TemplateHeaderDao) dao).getHeader(templateId, languageId);
	}
}
