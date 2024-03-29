package com.strandls.landscape.service.impl;

import java.io.IOException;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strandls.landscape.dao.PageFieldDao;
import com.strandls.landscape.pojo.PageField;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.PageFieldService;

public class PageFieldServiceImpl extends AbstractService<PageField> implements PageFieldService {

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	public PageFieldServiceImpl(PageFieldDao dao) {
		super(dao);
	}

	@Override
	public PageField save(String jsonString) throws IOException {
		PageField pageField = objectMapper.readValue(jsonString, PageField.class);
		pageField = save(pageField);
		return pageField;
	}

	@Override
	public PageField getPageField(Long protectedAreaId, Long templateId) {
		return ((PageFieldDao) dao).getPageField(protectedAreaId, templateId);
	}
}
