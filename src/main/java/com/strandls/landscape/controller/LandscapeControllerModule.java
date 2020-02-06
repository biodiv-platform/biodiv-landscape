/**
 * 
 */
package com.strandls.landscape.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * @author Abhishek Rudra
 *
 */
public class LandscapeControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LandscapeController.class).in(Scopes.SINGLETON);
	}

}
