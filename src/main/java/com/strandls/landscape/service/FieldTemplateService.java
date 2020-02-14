package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.landscape.pojo.FieldTemplate;

public interface FieldTemplateService {

	public FieldTemplate findById(Long id);
	public FieldTemplate findByPropertyWithCondtion(String property, String value, String condition);
	
	public FieldTemplate save(String jsonString) throws JsonParseException, JsonMappingException, IOException;
	public FieldTemplate save(FieldTemplate entity);
	public FieldTemplate update(FieldTemplate entity);
	public FieldTemplate delete(Long id);
	
	public List<FieldTemplate> findAll();
	public List<FieldTemplate> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset);
}
