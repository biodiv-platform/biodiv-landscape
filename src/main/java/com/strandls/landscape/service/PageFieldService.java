package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.landscape.pojo.PageField;

public interface PageFieldService {

	public PageField findById(Long id);
	public PageField findByPropertyWithCondtion(String property, Object value, String condition);
	
	public PageField save(String jsonString) throws JsonParseException, JsonMappingException, IOException;
	public PageField save(PageField entity);
	public PageField update(PageField entity);
	public PageField delete(Long id);
	
	public List<PageField> findAll();
	public PageField getPageField(Long protectedAreaId, Long templateId);
	public List<PageField> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset);
}
