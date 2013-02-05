package org.walkingarchive.backend;

import javax.ws.rs.Path;
import javax.ws.rs.GET;

@Path("/status")
public class ServerStatusResource {

	@GET
	public String getPing()
	{
		return "hello";
	}
}
