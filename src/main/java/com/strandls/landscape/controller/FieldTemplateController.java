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
import com.strandls.landscape.pojo.FieldTemplate;
import com.strandls.landscape.service.FieldTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author vilay
 *
 */
@Path(ApiConstants.FIELD_TEMPLATE)
@Api("Field Template")
public class FieldTemplateController {

	private FieldTemplateService fieldTemplateService;
	
	@Inject
	public FieldTemplateController(FieldTemplateService batchProductionService) {
		this.fieldTemplateService = batchProductionService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the Field by its id", response = FieldTemplate.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		FieldTemplate fieldTemplate = fieldTemplateService.findById(id);
		return Response.status(Status.CREATED).entity(fieldTemplate).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of all the pages", response = FieldTemplate.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request) {
		List<FieldTemplate> fieldTemplates = fieldTemplateService.findAll();
		return Response.ok().entity(fieldTemplates).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the page", response = FieldTemplate.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		FieldTemplate fieldTemplate;
		try {
			fieldTemplate = fieldTemplateService.save(jsonString);
			return Response.status(Status.CREATED).entity(fieldTemplate).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).build();
	}
}
