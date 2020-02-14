package com.strandls.landscape.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DaoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FieldContentDao.class).in(Scopes.SINGLETON);
		bind(FieldTemplateDao.class).in(Scopes.SINGLETON);
		bind(PageFieldDao.class).in(Scopes.SINGLETON);
		bind(TemplateHeaderDao.class).in(Scopes.SINGLETON);
	}
}
