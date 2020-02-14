package com.strandls.landscape.service;

import com.strandls.landscape.pojo.response.TemplateTreeStructure;

public interface LandscapeService {

	public TemplateTreeStructure getPageStructure(Long id, Long languageId);

	public TemplateTreeStructure saveField(Long protectedAreaId, Long languageId, Long templateId, String content);
	
}
