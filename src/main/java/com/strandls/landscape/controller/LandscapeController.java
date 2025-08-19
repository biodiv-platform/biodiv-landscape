package com.strandls.landscape.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.json.JSONException;
import org.pac4j.core.profile.CommonProfile;

import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.geoentities.ApiException;
import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.pojo.FieldContent;
import com.strandls.landscape.pojo.Landscape;
import com.strandls.landscape.pojo.TemplateHeader;
import com.strandls.landscape.pojo.request.FieldContentData;
import com.strandls.landscape.pojo.response.LandscapeShow;
import com.strandls.landscape.pojo.response.TemplateTreeStructure;
import com.strandls.landscape.service.FieldContentService;
import com.strandls.landscape.service.LandscapeService;
import com.strandls.landscape.service.TemplateHeaderService;
import com.strandls.landscape.util.UserUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.StreamingOutput;

@Tag(name = "Landscape")
@Path(ApiConstants.LANDSCAPE)
public class LandscapeController {

	@Inject
	private LandscapeService landscapeService;

	@Inject
	private TemplateHeaderService templateHeaderService;

	@Inject
	private FieldContentService fieldContentService;

	@GET
	@Path(ApiConstants.PING)
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Ping", description = "Application liveness check")
	@ApiResponse(responseCode = "200", description = "PONG", content = @Content(schema = @Schema(type = "string", example = "PONG")))
	public Response ping() {
		return Response.status(Status.OK).entity("PONG").build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the landscape model", description = "Returns a landscape model by id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Landscape returned", content = @Content(schema = @Schema(implementation = Landscape.class))) })
	public Response getLandScape(@PathParam("id") Long id) {
		Landscape landscape = landscapeService.findById(id);
		return Response.ok().entity(landscape).build();
	}

	@GET
	@Path(ApiConstants.SHOW + ApiConstants.SITE_NUMBER + "/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the landscape model by site number", description = "Returns a LandscapeShow response")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "LandscapeShow returned", content = @Content(schema = @Schema(implementation = LandscapeShow.class))) })
	public Response getLandScapeBySiteNumber(@PathParam("id") Long id, @QueryParam("languageId") Long languageId)
			throws ApiException {
		LandscapeShow landscapeShow = landscapeService.showPageBySiteNumber(id, languageId);
		return Response.ok().entity(landscapeShow).build();
	}

	@GET
	@Path(ApiConstants.SHOW + "/{protectedAreaId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the show page data for landscape model", description = "Returns a LandscapeShow response")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "LandscapeShow returned", content = @Content(schema = @Schema(implementation = LandscapeShow.class))) })
	public Response getLandScape(@PathParam("protectedAreaId") Long id, @QueryParam("languageId") Long languageId)
			throws ApiException {
		LandscapeShow landscapeShow = landscapeService.getShowPage(id, languageId);
		return Response.ok().entity(landscapeShow).build();
	}

	@GET
	@Path("all")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get list page for the landscape model", description = "Returns list of landscapes")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Landscapes returned", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Landscape.class)))) })
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

	@PUT
	@Path("thumbnail/all")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update thumbnail for all landscapes", description = "Updates and returns all landscapes")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Landscapes updated", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Landscape.class)))) })
	@ValidateUser
	public Response updateThumbnailForAllLandscape(@Context HttpServletRequest request) throws ApiException {
		if (!UserUtil.isAdmin(request))
			return Response.status(Status.UNAUTHORIZED).build();
		List<Landscape> landscapes = landscapeService.updateThumbnailForAllLandscape();
		return Response.ok().entity(landscapes).build();
	}

	@PUT
	@Path("thumbnail")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update thumbnail", description = "Updates and returns the landscape")
	@ApiResponse(responseCode = "200", description = "Thumbnail updated", content = @Content(schema = @Schema(implementation = Landscape.class)))
	@ValidateUser
	public Response updateThumbnail(@Context HttpServletRequest request,
			@QueryParam("protectedAreaId") Long protectedAreaId) throws ApiException {
		if (!UserUtil.isAdmin(request))
			return Response.status(Status.UNAUTHORIZED).build();
		Landscape landscape = landscapeService.updateThumbnail(protectedAreaId);
		return Response.ok().entity(landscape).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the landscape page", description = "Create new landscape", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(type = "string", description = "Landscape as JSON string"))))
	@ApiResponse(responseCode = "201", description = "Landscape created", content = @Content(schema = @Schema(implementation = Landscape.class)))
	@ValidateUser
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Landscape JSON string", required = true) String jsonString)
			throws JSONException, ApiException {
		try {
			if (!UserUtil.isAdmin(request))
				return Response.status(Status.UNAUTHORIZED).build();
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
	@Operation(summary = "Get template header", description = "Returns template headers")
	@ApiResponse(responseCode = "200", description = "List of template headers", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TemplateHeader.class))))
	public Response getTemplateHeader(@QueryParam("languageId") Long languageId) {
		List<TemplateHeader> templateHeaders = templateHeaderService.getByLanguageId(languageId);
		return Response.ok().entity(templateHeaders).build();
	}

	@POST
	@Path("template/header")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Add template header", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(type = "string", description = "template header JSON string"))))
	@ApiResponse(responseCode = "200", description = "Template header created", content = @Content(schema = @Schema(implementation = TemplateHeader.class)))
	@ValidateUser
	public Response addTemplateHeader(@Context HttpServletRequest request,
			@RequestBody(description = "template header JSON", required = true) String jsonString) {
		try {
			if (!UserUtil.isAdmin(request))
				return Response.status(Status.UNAUTHORIZED).build();
			TemplateHeader templateHeader = templateHeaderService.save(jsonString);
			return Response.ok().entity(templateHeader).build();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@Path("upload/field/content")
	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Upload a file for taxon definition", description = "Returns success or failure along with list of field content", requestBody = @RequestBody(required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA)))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Result map", content = @Content(schema = @Schema(implementation = Map.class))),
			@ApiResponse(responseCode = "400", description = "file not present", content = @Content(schema = @Schema(type = "string"))),
			@ApiResponse(responseCode = "500", description = "ERROR", content = @Content(schema = @Schema(type = "string"))) })
	// @ValidateUser
	public Response upload(@Context HttpServletRequest request,
			@Parameter(description = "multipart body", required = true) FormDataMultiPart multiPart) {
		try {
			Map<String, Object> result = landscapeService.uploadFile(request, multiPart);
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@POST
	@Path("field/content")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "update field content to Landscape", description = "Update field content to Landscape", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = FieldContentData.class))))
	@ApiResponse(responseCode = "200", description = "Template tree structure", content = @Content(schema = @Schema(implementation = TemplateTreeStructure.class)))
	@ValidateUser
	public Response saveField(@Context HttpServletRequest request,
			@RequestBody(description = "fieldContentData", required = true) FieldContentData fieldContentData) {
		try {
			if (!UserUtil.isAdmin(request))
				return Response.status(Status.UNAUTHORIZED).build();
			TemplateTreeStructure node = landscapeService.saveField(request, fieldContentData);
			return Response.ok().entity(node).build();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@PUT
	@Path("field/content")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "update field content to Landscape", description = "Update field content to landscape", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(type = "string"))))
	@ApiResponse(responseCode = "200", description = "Field content updated", content = @Content(schema = @Schema(implementation = FieldContent.class)))
	@ValidateUser
	public Response updateField(@Context HttpServletRequest request,
			@RequestBody(description = "field content JSON string", required = true) String jsonString) {
		try {
			if (!UserUtil.isAdmin(request))
				return Response.status(Status.UNAUTHORIZED).build();
			FieldContent fieldContent = fieldContentService.update(jsonString);
			return Response.ok().entity(fieldContent).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@POST
	@Path("download")
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Download landscape", description = "Download geodata for the landscape in wkt or PNG format.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Streamed file"),
			@ApiResponse(responseCode = "401", description = "User profile is required", content = @Content(schema = @Schema(type = "string"))) })
	@ValidateUser
	public Response downloadLandscape(@Context HttpServletRequest request,
			@QueryParam("protectedAreaId") Long protectedAreaId, @DefaultValue("wkt") @QueryParam("type") String type)
			throws ApiException, IOException {
		if (protectedAreaId == null) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}

		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		if (profile == null)
			throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).build());

		File file = landscapeService.downloadLandscape(request, protectedAreaId, type);

		InputStream in = new FileInputStream(file);
		StreamingOutput sout = new StreamingOutput() {
			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException {
				byte[] buf = new byte[8192];
				int c;
				while ((c = in.read(buf, 0, buf.length)) > 0) {
					out.write(buf, 0, c);
					out.flush();
				}
				out.close();
			}
		};
		if ("PNG".equalsIgnoreCase(type))
			return Response.ok(sout).type("image/png").build();
		return Response.ok(sout).build();
	}

	@PUT
	@Path("update/wkt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update WKT", description = "Updates geometry WKT for a landscape")
	@ApiResponse(responseCode = "200", description = "WKT updated", content = @Content(schema = @Schema(implementation = Landscape.class)))
	@ValidateUser
	public Response updateWKT(@Context HttpServletRequest request, @QueryParam("protectedAreaId") Long protectedAreaId,
			@QueryParam("wkt") String wkt) throws ApiException {
		if (!UserUtil.isAdmin(request))
			return Response.status(Status.UNAUTHORIZED).build();
		if (protectedAreaId == null || wkt == null) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
		Landscape landscape = landscapeService.updateWKT(protectedAreaId, wkt);
		return Response.ok().entity(landscape).build();
	}

	@GET
	@Path("boundingBox/{protectedAreaId}")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get bounding box by protected area id", description = "Returns the bounding box as an array of arrays")
	@ApiResponse(responseCode = "200", description = "Bounding box returned", content = @Content(array = @ArraySchema(schema = @Schema(type = "array", implementation = Object.class))))
	public Response getBoundingBox(@PathParam("protectedAreaId") Long protectedAreaId) throws ApiException {
		List<List<Object>> boundingBox = landscapeService.getBoundingBox(protectedAreaId);
		return Response.ok().entity(boundingBox).build();
	}
}
