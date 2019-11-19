package com.testing.jersey;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test")
public class TestResource {
	TestRepository repo = new TestRepository();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Sample> getSamples() {
		return repo.getSamples();
	}

	@GET
	@Path("one/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Sample getSample(@PathParam("id") int i) {
		return repo.getSample(i);
	}

	@POST
	@Path("create")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Sample createSample(Sample s1) {
		repo.create(s1);
		return s1;
	}

	@PUT
	@Path("update")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Sample updateSample(Sample s1) {
		repo.update(s1);
		return s1;
	}

	@DELETE
	@Path("delete/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int deleteSample(@PathParam("id") int i) {
		return i;
	}
}
