package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.strandls.landscape.pojo.Language;

public interface LanguageService {

	public Language findById(Long id);
	
	public Language save(String jsonString) throws IOException;
	public Language save(Language entity);
	public Language update(Language entity);
	public Language delete(Long id);
	
	public List<Language> findAll();
}
