package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.landscape.pojo.FieldContent;

public interface FieldContentService {

	public FieldContent findById(Long id);

	public FieldContent findByPropertyWithCondtion(String property, String value, String condition);

	public FieldContent save(FieldContent entity);

	public FieldContent update(FieldContent entity);

	public FieldContent delete(Long id);

	public List<FieldContent> findAll();

	public FieldContent getFieldContent(Long id, Long languageId);

	public List<FieldContent> getByPropertyWithCondtion(String property, Object value, String condition, int limit,
			int offset);

	FieldContent update(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException;

	FieldContent saveOrUpdate(Long pageFieldId, Long languageId, String content)
			throws JsonParseException, JsonMappingException, IOException, JSONException;
}
