package org.walkingarchive.backend.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
    public User getUserById(@PathParam("userId") String userId) {
        //TODO - validate input
        return SecurityFactory.getInstance().getUserById(Integer.parseInt(userId));
    }
}
