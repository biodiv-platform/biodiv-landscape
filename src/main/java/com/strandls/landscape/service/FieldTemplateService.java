package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.strandls.landscape.pojo.FieldTemplate;

public interface FieldTemplateService {

	public FieldTemplate findById(Long id);
	
	public FieldTemplate save(String jsonString) throws IOException;
	public FieldTemplate save(FieldTemplate entity);
	public FieldTemplate update(FieldTemplate entity);
	public FieldTemplate delete(Long id);
	
	public List<FieldTemplate> findAll();
}
