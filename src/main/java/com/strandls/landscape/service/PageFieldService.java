package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.strandls.landscape.pojo.PageField;

public interface PageFieldService {

	public PageField findById(Long id);
	
	public PageField save(String jsonString) throws IOException;
	public PageField save(PageField entity);
	public PageField update(PageField entity);
	public PageField delete(Long id);
	
	public List<PageField> findAll();
	public PageField getPageField(Long protectedAreaId, Long templateId);
}
