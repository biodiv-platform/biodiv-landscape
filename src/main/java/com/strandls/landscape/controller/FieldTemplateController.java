package com.strandls.landscape.controller;

import java.io.IOException;
import java.util.List;

import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.pojo.FieldTemplate;
import com.strandls.landscape.service.FieldTemplateService;
import com.strandls.landscape.util.UserUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// OpenAPI 3 (Jakarta)
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Tag(name = "Field Template")
@Path(ApiConstants.FIELD_TEMPLATE)
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
	@Operation(summary = "Get the FieldTemplate by its id", description = "Returns a FieldTemplate object by id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "FieldTemplate found", content = @Content(schema = @Schema(implementation = FieldTemplate.class))),
			@ApiResponse(responseCode = "404", description = "FieldTemplate not found", content = @Content(schema = @Schema(type = "string"))) })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		FieldTemplate fieldTemplate = fieldTemplateService.findById(id);
		if (fieldTemplate != null)
			return Response.status(Status.OK).entity(fieldTemplate).build();
		return Response.status(Status.NOT_FOUND).entity("FieldTemplate not found").build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the field templates", description = "Returns a list of all FieldTemplate objects")
	@ApiResponse(responseCode = "200", description = "List of FieldTemplates", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FieldTemplate.class))))
	public Response findAll(@Context HttpServletRequest request) {
		List<FieldTemplate> fieldTemplates = fieldTemplateService.findAll();
		return Response.ok().entity(fieldTemplates).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the field template", description = "Save a new FieldTemplate and return the created object", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(type = "string", description = "FieldTemplate as a JSON string"))))
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "FieldTemplate created", content = @Content(schema = @Schema(implementation = FieldTemplate.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(type = "string"))),
			@ApiResponse(responseCode = "204", description = "No content (error)", content = @Content(schema = @Schema(type = "string"))) })
	@ValidateUser
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "FieldTemplate JSON string", required = true, content = @Content(schema = @Schema(type = "string"))) String jsonString) {
		try {
			if (!UserUtil.isAdmin(request))
				return Response.status(Status.UNAUTHORIZED).entity("Unauthorized").build();
			FieldTemplate fieldTemplate = fieldTemplateService.save(jsonString);
			return Response.status(Status.CREATED).entity(fieldTemplate).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Could not create field template").build();
	}
}
