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
public class LandscapeControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LandscapeController.class).in(Scopes.SINGLETON);
		bind(FieldTemplateController.class).in(Scopes.SINGLETON);
	}

}
