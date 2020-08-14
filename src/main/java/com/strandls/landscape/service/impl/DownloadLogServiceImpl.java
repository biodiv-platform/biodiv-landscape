package com.strandls.landscape.service.impl;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strandls.landscape.dao.DownloadLogDao;
import com.strandls.landscape.pojo.DownloadLog;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.DownloadLogService;

public class DownloadLogServiceImpl extends AbstractService<DownloadLog> implements DownloadLogService {

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	public DownloadLogServiceImpl(DownloadLogDao dao) {
		super(dao);
	}

	public DownloadLog save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		DownloadLog downloadLog = objectMapper.readValue(jsonString, DownloadLog.class);
		downloadLog = save(downloadLog);
		return downloadLog;
	}

	@Override
	public List<DownloadLog> getDownloadLogByAutherId(Long autherId) {
		return ((DownloadLogDao) dao).getDownloadLogByAutherId(autherId);
	}

	@Override
	public List<DownloadLog> getAllDownloadLogs() {
		return ((DownloadLogDao) dao).getAllDownloadLogs();
	}
}
