package com.strandls.landscape.controller;

import java.io.IOException;
import java.util.List;

import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.pojo.Language;
import com.strandls.landscape.service.LanguageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// OpenAPI 3 for Jakarta
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

@Tag(name = "Language")
@Path(ApiConstants.LANGUAGE)
public class LanguageController {

	private LanguageService languageService;

	@Inject
	public LanguageController(LanguageService languageService) {
		this.languageService = languageService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the Language by its id", description = "Returns a Language object by id")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Language found", content = @Content(schema = @Schema(implementation = Language.class))),
			@ApiResponse(responseCode = "404", description = "Language not found", content = @Content(schema = @Schema(type = "string"))) })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Language language = languageService.findById(id);
		if (language != null)
			return Response.status(Status.CREATED).entity(language).build();
		return Response.status(Status.NOT_FOUND).entity("Language not found").build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the languages", description = "Returns a list of all languages")
	@ApiResponse(responseCode = "200", description = "List of languages", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Language.class))))
	public Response findAll(@Context HttpServletRequest request) {
		List<Language> languages = languageService.findAll();
		return Response.ok().entity(languages).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the language", description = "Save a new language and return the created Language object", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(type = "string", description = "Language as a JSON string"))))
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Language created", content = @Content(schema = @Schema(implementation = Language.class))),
			@ApiResponse(responseCode = "204", description = "No content (error)", content = @Content(schema = @Schema(type = "string"))) })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Language JSON String", required = true, content = @Content(schema = @Schema(type = "string"))) String jsonString) {
		try {
			Language language = languageService.save(jsonString);
			return Response.status(Status.CREATED).entity(language).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Could not create language").build();
	}
}
