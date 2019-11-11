package com.testing.jersey;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test")
public class TestResource {
	TestRepository repo = new TestRepository();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sample> getSample() {
		return repo.getSamples();
	}
	
	@POST
	@Path("create")
	public Sample createSample(Sample s1) {
		repo.create(s1);
		return s1;
	}
}
