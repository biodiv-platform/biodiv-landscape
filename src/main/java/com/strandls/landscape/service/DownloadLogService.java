package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.strandls.landscape.pojo.DownloadLog;

public interface DownloadLogService {
	
	public DownloadLog save(DownloadLog downloadLog);
	public DownloadLog save(String jsonString) throws JsonParseException, JsonMappingException, IOException;
	
	public List<DownloadLog> findAll();
	
	public List<DownloadLog> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset);
	
	public List<DownloadLog> getDownloadLogByAutherId(Long autherId);
	public List<DownloadLog> getAllDownloadLogs();

}
