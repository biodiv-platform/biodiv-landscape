package com.strandls.landscape.service;

import java.io.IOException;
import java.util.List;

import com.strandls.landscape.pojo.DownloadLog;

public interface DownloadLogService {
	
	public DownloadLog save(DownloadLog downloadLog);
	public DownloadLog save(String jsonString) throws IOException;
	
	public List<DownloadLog> findAll();
	
	public List<DownloadLog> getDownloadLogByAutherId(Long autherId);
	public List<DownloadLog> getAllDownloadLogs();

}
