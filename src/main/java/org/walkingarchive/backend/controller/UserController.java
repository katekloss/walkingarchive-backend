package org.walkingarchive.backend.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.walkingarchive.backend.model.card.CardDAO;
import org.walkingarchive.backend.model.card.Deck;
import org.walkingarchive.backend.model.security.SecurityDAO;
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

    /** Retrieve all the users on the system. This is forbidden
     * 
     * @return HTTP Response state forbidden
     */
    @GET
    public Response getAll() {
        return Response.status(Status.FORBIDDEN).build();
    }

    /** Retrieves a user by the given id
     * 
     * @param userId - int representing the id of a User
     * @return User as a JSON object (no password), or 404 if not found
     * @throws JSONException
     */
    @GET
    @Path("id/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") String userId) throws JSONException {
        //TODO - validate input
        Response result;
        User user = SecurityDAO.getInstance().getUserById(Integer.parseInt(userId));
        if(user != null) {
            result = Response.ok(user, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        
        return result;
    }

    /** Retrieve a use by their usename
     * 
     * @param name - String representing the username of a user
     * @return Use as a JSON object (no password) or 404 if not found
     * @throws JSONException
     */
    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByName(@PathParam("name") String name) throws JSONException {
        //TODO - validate input
        Response result;
        User user = SecurityDAO.getInstance().getUserByName(name);
        
        if(user != null) {
            result = Response.ok(user, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        
        return result;
    }
    
    /** Creates a new User from the json provided
     * 
     * @param json - JSON String specifying the username, password, and email
     * @return User as a JSON object (no password)
     * @throws Exception
     */
    @PUT
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        User user = new User(jsonObject.get("name").toString(), 
                jsonObject.get("password").toString(), 
                jsonObject.get("email").toString());
        
        user = SecurityDAO.getInstance().createUser(user);
        
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }
    
    /** Deletes a User with the given id
     * 
     * @param id - int id of User to delete
     * @return HTTP Response ok if deletion is successful, 404 if the User is not found
     */
    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") int id) {
        Response result;
        User user = SecurityDAO.getInstance().getUserById(id);
        if (user != null) {
            SecurityDAO.getInstance().delete(user);
            result = Response.ok().build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        
        return result;
    }
}
