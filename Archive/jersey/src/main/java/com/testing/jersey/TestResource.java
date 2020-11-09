package com.testing.jersey;

import java.sql.SQLException;
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

@Path("emp")
public class TestResource {
	TestRepository repo = new TestRepository();

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Employee> getSamples() throws SQLException {
		return repo.getEmployees();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Employee getSample(@PathParam("id") int i) throws SQLException {
		return repo.getEmployee(i);
	}

	@POST
	@Path("add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String createSample(Employee e) throws SQLException {
		int i = repo.create(e);
		String result = null;
		if (i == 1) {
			result = "Successfully added to database";
		} else {
			result = "Failed adding to database";
		}
		return result;
	}

	@PUT
	@Path("update")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String updateSample(Employee e) throws SQLException {
		int i = repo.update(e);
		String result1 = null;
		if (i == 1) {
			result1 = "Successfully updated";
		} else {
			result1 = "Failed to update";
		}
		return result1;
	}

	@DELETE
	@Path("delete/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String deleteSample(@PathParam("id") int i) throws SQLException {
		int j = repo.delete(i);
		String result = null;
		if (j == 1) {
			result = "Successfully deleted";
		} else {
			result = "Failed to delete";
		}
		return result;
	}
}