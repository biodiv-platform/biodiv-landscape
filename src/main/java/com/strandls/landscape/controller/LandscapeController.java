/**
 * 
 */
package com.strandls.landscape.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;

import com.google.inject.Inject;
import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.pojo.Landscape;
import com.strandls.landscape.pojo.TemplateHeader;
import com.strandls.landscape.pojo.response.TemplateTreeStructure;
import com.strandls.landscape.service.LandscapeService;
import com.strandls.landscape.service.TemplateHeaderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author vilay
 *
 */
@Path(ApiConstants.LANDSCAPE)
@Api("Landscape")
public class LandscapeController {

	@Inject
	private LandscapeService landscapeService;

	@Inject
	private TemplateHeaderService templateHeaderService;

	@GET
	@Path(ApiConstants.PING)
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.status(Status.OK).entity("PONG").build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the landscape model", response = TemplateTreeStructure.class)
	public Response getLandScape(@PathParam("id") Long id) {
		Landscape landscape = landscapeService.findById(id);
		return Response.ok().entity(landscape).build();
	}
	
	@GET
	@Path("show/{protectedAreaId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the show page data for landscape model", response = TemplateTreeStructure.class)
	public Response getLandScape(@PathParam("protectedAreaId") Long id, @QueryParam("languageId") Long languageId) {
		TemplateTreeStructure treeStructure = landscapeService.getPageStructure(id, languageId);
		return Response.ok().entity(treeStructure).build();
	}

	@GET
	@Path("all")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list page for the landscape model")
	public Response getAllLandScapes(@QueryParam("languageId") Long languageId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Landscape> landscapes;
		if (limit == -1 || offset == -1)
			landscapes = landscapeService.findAll();
		else
			landscapes = landscapeService.findAll(limit, offset);
		return Response.ok().entity(landscapes).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the landscape page", response = Landscape.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		try {
			Landscape landscape = landscapeService.save(jsonString);
			return Response.status(Status.CREATED).entity(landscape).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("template/header")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add template header")
	public Response getTemplateHeader(@QueryParam("languageId") Long languageId) {
		List<TemplateHeader> templateHeaders = templateHeaderService.getByPropertyWithCondtion("languageId", languageId,
				"=", -1, -1);
		return Response.ok().entity(templateHeaders).build();
	}

	@POST
	@Path("field/content")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add field content to Landscape", response = TemplateTreeStructure.class)
	public Response addField(@Context HttpServletRequest request, String jsonString) {

		try {
			TemplateTreeStructure rootNode = landscapeService.saveField(request, jsonString);
			return Response.ok().entity(rootNode).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@POST
	@Path("template/header")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add template header", response = TemplateHeader.class)
	public Response addTemplateHeader(@Context HttpServletRequest request, String jsonString) {
		try {
			TemplateHeader templateHeader = templateHeaderService.save(jsonString);
			return Response.ok().entity(templateHeader).build();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}
}
