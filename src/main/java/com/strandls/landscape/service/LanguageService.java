package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.landscape.pojo.Language;

public interface LanguageService {

	public Language findById(Long id);
	public Language findByPropertyWithCondtion(String property, String value, String condition);
	
	public Language save(String jsonString) throws JsonParseException, JsonMappingException, IOException;
	public Language save(Language entity);
	public Language update(Language entity);
	public Language delete(Long id);
	
	public List<Language> findAll();
	public List<Language> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset);
}
