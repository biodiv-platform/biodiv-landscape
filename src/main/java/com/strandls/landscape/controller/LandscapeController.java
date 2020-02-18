/**
 * 
 */
package com.strandls.landscape.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.pojo.TemplateHeader;
import com.strandls.landscape.pojo.response.TemplateTreeStructure;
import com.strandls.landscape.service.LandscapeService;
import com.strandls.landscape.service.TemplateHeaderService;

import io.swagger.annotations.Api;
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
	@Path("{protectedAreaId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the landscape model", response = TemplateTreeStructure.class)
	public Response getLandScape(@PathParam("protectedAreaId") Long id, @QueryParam("languageId")Long languageId) {
		TemplateTreeStructure treeStructure = landscapeService.getPageStructure(id, languageId);
		return Response.ok().entity(treeStructure).build();
	}
	
	@GET
	@Path("template/header")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add template header")
	public Response getTemplateHeader(@QueryParam("languageId") Long languageId) {
		List<TemplateHeader> templateHeaders = templateHeaderService.getByPropertyWithCondtion("languageId", languageId, "=", -1, -1);
		return Response.ok().entity(templateHeaders).build();
	}

	@POST
	@Path("field/content")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add field content to Landscape", response = TemplateTreeStructure.class)
	public Response addField(@QueryParam("protectedAreaId") Long protectedAreaId,
			@QueryParam("languageId") Long languageId, @QueryParam("templateId") Long templateId,
			@QueryParam("content") String content) {
		TemplateTreeStructure rootNode = landscapeService.saveField(protectedAreaId, languageId, templateId, content);
		return Response.ok().entity(rootNode).build();
	}

	@POST
	@Path("template/header")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add template header", response = TemplateTreeStructure.class)
	public Response addTemplateHeader(@QueryParam("templateId") Long templateId, @QueryParam("languageId") Long languageId,
			@QueryParam("header") String header) {
		TemplateHeader templateHeader = new TemplateHeader();
		templateHeader.setTemplateId(templateId);
		templateHeader.setLanguageId(languageId);
		templateHeader.setHeader(header);
		templateHeader.setIsDeleted(false);
		templateHeader = templateHeaderService.save(templateHeader);
		return Response.ok().entity(templateHeader).build();
	}
}
