/**
 * 
 */
package com.strandls.landscape.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * 
 * @author vilay
 *
 */
public class ControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LanguageController.class).in(Scopes.SINGLETON);
		bind(LandscapeController.class).in(Scopes.SINGLETON);
		bind(FieldTemplateController.class).in(Scopes.SINGLETON);
		bind(DownloadLogController.class).in(Scopes.SINGLETON);
	}

}
