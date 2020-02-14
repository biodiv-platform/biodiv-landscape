package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.landscape.pojo.TemplateHeader;

public interface TemplateHeaderService {

	public TemplateHeader findById(Long id);
	public TemplateHeader findByPropertyWithCondtion(String property, String value, String condition);
	
	public TemplateHeader save(String jsonString) throws JsonParseException, JsonMappingException, IOException;
	public TemplateHeader save(TemplateHeader entity);
	public TemplateHeader update(TemplateHeader entity);
	public TemplateHeader delete(Long id);
	
	public TemplateHeader getHeader(Long templateId, Long languageId);
	public List<TemplateHeader> findAll();
	public List<TemplateHeader> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset);
}
