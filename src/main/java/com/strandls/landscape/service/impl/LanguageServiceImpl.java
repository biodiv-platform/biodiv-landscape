package com.strandls.landscape.service.impl;

import java.io.IOException;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strandls.landscape.dao.LanguageDao;
import com.strandls.landscape.pojo.Language;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.LanguageService;

public class LanguageServiceImpl extends AbstractService<Language> implements LanguageService {

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	public LanguageServiceImpl(LanguageDao dao) {
		super(dao);
	}

	public Language save(String jsonString) throws IOException {
		Language fieldTemplate = objectMapper.readValue(jsonString, Language.class);
		fieldTemplate = save(fieldTemplate);
		return fieldTemplate;
	}
}
