/**
 * 
 */
package com.strandls.landscape.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.pojo.Language;
import com.strandls.landscape.service.LanguageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author vilay
 *
 */
@Path(ApiConstants.LANGUAGE)
@Api("Language")
public class LanguageController {

	private LanguageService LanguageService;
	
	@Inject
	public LanguageController(LanguageService languageService) {
		this.LanguageService = languageService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the Language by its id", response = Language.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Language language = LanguageService.findById(id);
		return Response.status(Status.CREATED).entity(language).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the languages", response = Language.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request) {
		List<Language> languages = LanguageService.findAll();
		return Response.ok().entity(languages).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the language", response = Language.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		try {
			Language language = LanguageService.save(jsonString);
			return Response.status(Status.CREATED).entity(language).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).build();
	}
}
