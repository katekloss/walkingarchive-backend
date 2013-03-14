package org.walkingarchive.backend.controller;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.walkingarchive.backend.model.security.SecurityFactory;
import org.walkingarchive.backend.model.security.User;

@Path("/user/")
public class UserController {
    @Context
    protected ServletContext context;
    @Context
    protected HttpServletRequest request;
    @Context
    protected HttpServletResponse response;
    @Context
    protected UriInfo uriInfo;

    @GET
    public void getAll() {
        throw new RuntimeException("You do not have permissions to access all users.");
    }

    @GET
    @Path("{userId}")
    public JSONObject getTradeByUser(@PathParam("userId") String userId) throws JSONException {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserById(UUID.fromString(userId));
        return user.toJson();
    }
}
