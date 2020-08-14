package com.strandls.landscape.controller;

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

import org.pac4j.core.profile.CommonProfile;

import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.geoentities.ApiException;
import com.strandls.landscape.ApiConstants;
import com.strandls.landscape.pojo.DownloadLog;
import com.strandls.landscape.service.DownloadLogService;
import com.strandls.landscape.util.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path(ApiConstants.DOWNLOAD)
@Api("DownloadLog")
public class DownloadLogController {

	@Inject
	private DownloadLogService downloadLogService;
	
	@GET
	@Path(ApiConstants.PING)
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.status(Status.OK).entity("PONG").build();
	}
	
	@POST
	@Path("log")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add the download log ", response = DownloadLog.class)
	public Response saveDownloadLog(@ApiParam(name = "downloadLog") DownloadLog downloadLog) throws ApiException {
		downloadLog = downloadLogService.save(downloadLog);
		return Response.ok().entity(downloadLog).build();
	}
	
	@GET
	@Path("log/{autherId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the download log by autherId", response = DownloadLog.class)
	@ValidateUser
	public Response getDownloadLog(@Context HttpServletRequest request, @PathParam("autherId") Long autherId) throws ApiException {
		
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		if(!UserUtil.isAdmin(request) && !autherId.equals(profile.getId())) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		List<DownloadLog> downloadLog = downloadLogService.getDownloadLogByAutherId(autherId);
		return Response.ok().entity(downloadLog).build();
	}
	
	@GET
	@Path("log/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the download log ", response = DownloadLog.class, responseContainer = "List")
	@ValidateUser
	public Response getAllDownloadLog(@Context HttpServletRequest request) throws ApiException {
		if(!UserUtil.isAdmin(request)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		List<DownloadLog> downloadLogs = downloadLogService.getAllDownloadLogs();
		return Response.ok().entity(downloadLogs).build();
	}
}
