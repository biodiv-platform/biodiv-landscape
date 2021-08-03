package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.strandls.landscape.pojo.TemplateHeader;

public interface TemplateHeaderService {

	public TemplateHeader findById(Long id);
	public List<TemplateHeader> getByLanguageId(Long id);
	
	public TemplateHeader save(String jsonString) throws IOException;
	public TemplateHeader save(TemplateHeader entity);
	public TemplateHeader update(TemplateHeader entity);
	public TemplateHeader delete(Long id);
	
	public TemplateHeader getHeader(Long templateId, Long languageId);
	public List<TemplateHeader> findAll();
}
