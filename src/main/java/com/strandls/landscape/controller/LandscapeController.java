/**
 * 
 */
package com.strandls.landscape.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

	@Inject
	private FieldContentService fieldContentService;

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
	@ApiOperation(value = "Get the landscape model", response = Landscape.class)
	public Response getLandScape(@PathParam("id") Long id) {
		Landscape landscape = landscapeService.findById(id);
		return Response.ok().entity(landscape).build();
	}

	@GET
	@Path(ApiConstants.SHOW + ApiConstants.SITE_NUMBER + "/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the landscape model by site number", response = LandscapeShow.class)
	public Response getLandScapeBySiteNumber(@PathParam("id") Long id, @QueryParam("languageId") Long languageId)
			throws ApiException {
		LandscapeShow landscapeShow = landscapeService.showPageBySiteNumber(id, languageId);
		return Response.ok().entity(landscapeShow).build();
	}

	@GET
	@Path(ApiConstants.SHOW + "/{protectedAreaId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the show page data for landscape model", response = LandscapeShow.class)
	public Response getLandScape(@PathParam("protectedAreaId") Long id, @QueryParam("languageId") Long languageId)
			throws ApiException {
		LandscapeShow landscapeShow = landscapeService.getShowPage(id, languageId);
		return Response.ok().entity(landscapeShow).build();
	}

	@GET
	@Path("all")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list page for the landscape model", response = Landscape.class, responseContainer = "List")
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
	@ApiOperation(value = "Get list page for the landscape model", response = Landscape.class)
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
	@ApiOperation(value = "Get list page for the landscape model", response = Landscape.class)
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
	@ApiOperation(value = "Save the landscape page", response = Landscape.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@ValidateUser
	public Response save(@Context HttpServletRequest request, String jsonString) throws JSONException, ApiException {
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
	@ApiOperation(value = "Add template header")
	public Response getTemplateHeader(@QueryParam("languageId") Long languageId) {
		List<TemplateHeader> templateHeaders = templateHeaderService.getByLanguageId(languageId);
		return Response.ok().entity(templateHeaders).build();
	}

	@POST
	@Path("template/header")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add template header", response = TemplateHeader.class)
	@ValidateUser
	public Response addTemplateHeader(@Context HttpServletRequest request, String jsonString) {
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
	@ApiOperation(value = "Upload the file for taxon definition", notes = "Returns succuess failure", response = FieldContent.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "file not present", response = String.class),
			@ApiResponse(code = 500, message = "ERROR", response = String.class) })
	//@ValidateUser
	public Response upload(@Context HttpServletRequest request, final FormDataMultiPart multiPart) {
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
	@ApiOperation(value = "update field content to Landscape", response = TemplateTreeStructure.class)
	@ValidateUser
	public Response saveField(@Context HttpServletRequest request, FieldContentData fieldContentData) {

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
	@ApiOperation(value = "update field content to Landscape", response = TemplateTreeStructure.class)
	@ValidateUser
	public Response updateField(@Context HttpServletRequest request, String jsonString) {

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
	@ApiResponses(value = { @ApiResponse(code = 401, message = "User profile is required", response = String.class) })
	@ValidateUser
	public Response downloadLandscape(@Context HttpServletRequest request, @QueryParam("protectedAreaId") Long protectedAreaId,
			@DefaultValue("wkt") @QueryParam("type") String type) throws ApiException, IOException {
		if (protectedAreaId == null) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
		
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		if(profile == null)
			throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).build());
		
		File file = landscapeService.downloadLandscape(request, protectedAreaId, type);
			
        InputStream in = new FileInputStream(file);
        StreamingOutput sout;
        sout = new StreamingOutput() {
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
        if("PNG".equalsIgnoreCase(type))
        	return Response.ok(sout).type("image/png").build();
        return Response.ok(sout).build();
	}

	@PUT
	@Path("update/wkt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	@ApiOperation(value = "update field content to Landscape", response = TemplateTreeStructure.class)
	public Response getBoundingBox(@PathParam("protectedAreaId") Long protectedAreaId) throws ApiException {
		List<List<Object>> boundingBox = landscapeService.getBoundingBox(protectedAreaId);
		return Response.ok().entity(boundingBox).build();
	}
}
