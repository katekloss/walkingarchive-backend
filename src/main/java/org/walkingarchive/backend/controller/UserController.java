package org.walkingarchive.backend.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getAll() {
        throw new RuntimeException("You do not have permissions to access all users.");
    }

    @GET
    @Path("id/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUserById(@PathParam("userId") String userId) throws JSONException {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserById(Integer.parseInt(userId));
        
        return createCensoredUser(user);
    }
    
    private JSONObject createCensoredUser(User user) throws JSONException {
        JSONObject cu = new JSONObject();

        cu.put("id", user.getId());
        cu.put("name", user.getName());
        cu.put("email", user.getEmail());
        
        return cu;
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUserByName(@PathParam("name") String name) throws JSONException {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserByName(name);
        
        return createCensoredUser(user);
    }
    
    @PUT
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        User user = new User(jsonObject.get("name").toString(), 
                jsonObject.get("password").toString(), 
                jsonObject.get("email").toString());
        
        user = SecurityFactory.getInstance().addUser(user);
        
        System.out.println("Created user " + user.getId());
        
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }
}
