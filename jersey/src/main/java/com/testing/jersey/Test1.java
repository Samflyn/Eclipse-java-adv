package com.testing.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test1")
public class Test1 {
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Sample getSample() {
		Sample s = new Sample();
		s.setName("sam");
		s.setNo(10);
		s.setNum(100);
		return s;
	}
}
