package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.strandls.landscape.pojo.FieldContent;

public interface FieldContentService {

	public FieldContent findById(Long id);

	public FieldContent save(FieldContent entity);

	public FieldContent update(FieldContent entity);

	public FieldContent delete(Long id);

	public List<FieldContent> findAll();

	public FieldContent getFieldContent(Long id, Long languageId);

	FieldContent update(String jsonString) throws IOException, JSONException;

	FieldContent saveOrUpdate(Long pageFieldId, Long languageId, String content)
			throws IOException;
}
