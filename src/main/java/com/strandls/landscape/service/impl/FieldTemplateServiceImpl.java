package com.strandls.landscape.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strandls.landscape.dao.FieldTemplateDao;
import com.strandls.landscape.pojo.FieldTemplate;
import com.strandls.landscape.pojo.Landscape;
import com.strandls.landscape.pojo.PageField;
import com.strandls.landscape.service.AbstractService;
import com.strandls.landscape.service.FieldTemplateService;
import com.strandls.landscape.service.LandscapeService;
import com.strandls.landscape.service.PageFieldService;

public class FieldTemplateServiceImpl extends AbstractService<FieldTemplate> implements FieldTemplateService{

	@Inject private ObjectMapper objectMapper;
	
	@Inject private LandscapeService landscapeService;
	
	@Inject private PageFieldService pageFieldService;
	
	@Inject
	public FieldTemplateServiceImpl(FieldTemplateDao dao) {
		super(dao);
	}
	
	/**
	 * Here we need to add the fields for all the protected areas.
	 */
	public FieldTemplate save(String jsonString) throws IOException {
		FieldTemplate fieldTemplate = objectMapper.readValue(jsonString, FieldTemplate.class);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		if(fieldTemplate.getCreatedOn() == null)
			fieldTemplate.setCreatedOn(timestamp);
		if(fieldTemplate.getModifiedOn() == null)
			fieldTemplate.setModifiedOn(timestamp);
		fieldTemplate.setIsDeleted(false);
		
		fieldTemplate = save(fieldTemplate);

		List<Landscape> landscapes = landscapeService.findAll();
		Long authorId = null;
		for(Landscape landscape : landscapes) {
			pageFieldService.save(new PageField(null, fieldTemplate.getId(), landscape.getId(), authorId, timestamp, timestamp, false));
		}
		
		return fieldTemplate;
	}
}
