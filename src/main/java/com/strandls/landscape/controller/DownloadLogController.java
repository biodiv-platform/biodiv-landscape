package com.strandls.landscape.controller;

import java.util.List;

import org.pac4j.core.profile.CommonProfile;

import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.Headers;
import com.strandls.landscape.pojo.DownloadLog;
import com.strandls.landscape.service.DownloadLogService;
import com.strandls.landscape.util.UserUtil;
import com.strandls.user.ApiException;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.DownloadLogData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// --- OpenAPI 3 (Swagger for Jakarta compatible) ---
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
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Tag(name = "DownloadLog")
@Path(ApiConstants.DOWNLOAD)
public class DownloadLogController {

	@Inject
	private DownloadLogService downloadLogService;

	@Inject
	private UserServiceApi userServiceApi;

	@Inject
	private Headers headers;

	@GET
	@Path(ApiConstants.PING)
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Ping endpoint", description = "Liveness check")
	@ApiResponse(responseCode = "200", description = "PONG", content = @Content(schema = @Schema(type = "string", example = "PONG")))
	public Response ping() {
		return Response.status(Status.OK).entity("PONG").build();
	}

	@POST
	@Path("log")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Add a download log", description = "Add a download log and returns the created DownloadLog", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = DownloadLog.class))))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully logged", content = @Content(schema = @Schema(implementation = DownloadLog.class))),
			@ApiResponse(responseCode = "400", description = "Error", content = @Content(schema = @Schema(type = "string"))) })
	public Response saveDownloadLog(@Context HttpServletRequest request,
			@Parameter(description = "Download log to be added", required = true) DownloadLog downloadLog) {
		DownloadLogData data = new DownloadLogData();
		data.setFilePath(downloadLog.getFilePath());
		data.setFileType(downloadLog.getType());
		data.setFilterUrl(downloadLog.getFilterUrl());
		data.setStatus(downloadLog.getStatus());
		data.setSourcetype("Landscape");
		userServiceApi = headers.addUserHeaders(userServiceApi, request.getHeader(HttpHeaders.AUTHORIZATION));
		try {
			return Response.ok().entity(userServiceApi.logDocumentDownload(data)).build();
		} catch (ApiException e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("log/{autherId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Get download logs for a user", description = "Get the download log entries for the given autherId")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "List of DownloadLog for the user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DownloadLog.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "400", description = "Error", content = @Content(schema = @Schema(type = "string"))) })
	public Response getDownloadLog(@Context HttpServletRequest request, @PathParam("autherId") Long autherId) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		if (!UserUtil.isAdmin(request) && !autherId.toString().equals(profile.getId())) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		List<DownloadLog> downloadLog = downloadLogService.getDownloadLogByAutherId(autherId);
		return Response.ok().entity(downloadLog).build();
	}

	@GET
	@Path("log/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Get all download logs", description = "Get all download log entries (admin only)")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "All download logs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DownloadLog.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "400", description = "Error", content = @Content(schema = @Schema(type = "string"))) })
	public Response getAllDownloadLog(@Context HttpServletRequest request) {
		if (!UserUtil.isAdmin(request)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		List<DownloadLog> downloadLogs = downloadLogService.getAllDownloadLogs();
		return Response.ok().entity(downloadLogs).build();
	}
}
