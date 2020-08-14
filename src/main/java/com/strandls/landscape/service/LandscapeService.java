package com.strandls.landscape.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.geoentities.ApiException;
import com.strandls.landscape.pojo.Landscape;
import com.strandls.landscape.pojo.response.LandscapeShow;
import com.strandls.landscape.pojo.response.TemplateTreeStructure;

public interface LandscapeService {

	public Landscape findById(Long id);
	public Landscape findByPropertyWithCondtion(String property, Object value, String condition);
	
	public Landscape save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException, ApiException;
	public Landscape save(Landscape entity);
	public Landscape update(Landscape entity);
	public Landscape delete(Long id);
	
	public List<Landscape> findAll();
	public List<Landscape> findAll(int limit, int offset);
	public List<Landscape> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset);
	
	public TemplateTreeStructure getPageStructure(Long id, Long languageId);
	public TemplateTreeStructure saveField(HttpServletRequest request, String jsonString) throws JSONException, JsonParseException, JsonMappingException, IOException;
	public List<List<Object>> getBoundingBox(Long protectedAreaId) throws ApiException;
	
	public String getWKT(Long protectedAreaId) throws ApiException;
	public Landscape updateWKT(Long protectedAreaId, String wkt) throws ApiException;
	public File downloadWKT(HttpServletRequest request, Long protectedAreaId, String type) throws ApiException, IOException;
	
	public LandscapeShow getShowPage(Long id, Long languageId) throws ApiException;
	public LandscapeShow showPageBySiteNumber(Long id, Long languageId) throws ApiException;
	public Landscape updateThumbnail(Long protectedAreaId) throws ApiException;
	public List<Landscape> updateThumbnailForAllLandscape() throws ApiException;
}
