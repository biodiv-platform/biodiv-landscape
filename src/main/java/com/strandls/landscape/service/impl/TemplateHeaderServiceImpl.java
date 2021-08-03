package com.strandls.landscape.service.impl;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	public TemplateHeader save(String jsonString) throws IOException {
		TemplateHeader templateHeader = objectMapper.readValue(jsonString, TemplateHeader.class);	
		templateHeader = save(templateHeader);
		return templateHeader;
	}

	@Override
	public TemplateHeader getHeader(Long templateId, Long languageId) {
		return ((TemplateHeaderDao) dao).getHeader(templateId, languageId);
	}

	@Override
	public List<TemplateHeader> getByLanguageId(Long languageId) {
		return ((TemplateHeaderDao) dao).getByLanguageId(languageId);
	}
}
