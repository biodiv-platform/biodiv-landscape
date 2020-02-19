package com.strandls.landscape.service;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.strandls.landscape.service.impl.FieldContentServiceImpl;
import com.strandls.landscape.service.impl.FieldTemplateServiceImpl;
import com.strandls.landscape.service.impl.LandscapeServiceImpl;
import com.strandls.landscape.service.impl.LanguageServiceImpl;
import com.strandls.landscape.service.impl.PageFieldServiceImpl;
import com.strandls.landscape.service.impl.TemplateHeaderServiceImpl;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LanguageService.class).to(LanguageServiceImpl.class).in(Scopes.SINGLETON);
		bind(LandscapeService.class).to(LandscapeServiceImpl.class).in(Scopes.SINGLETON);
		bind(FieldContentService.class).to(FieldContentServiceImpl.class).in(Scopes.SINGLETON);
		bind(FieldTemplateService.class).to(FieldTemplateServiceImpl.class).in(Scopes.SINGLETON);
		bind(PageFieldService.class).to(PageFieldServiceImpl.class).in(Scopes.SINGLETON);
		bind(TemplateHeaderService.class).to(TemplateHeaderServiceImpl.class).in(Scopes.SINGLETON);
	}
}
