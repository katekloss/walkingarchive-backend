package org.walkingarchive.backend;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.walkingarchive.backend.ServerStatusResource;

public class RestApi extends Application
{
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(ServerStatusResource.class);
		return classes;
	}
}