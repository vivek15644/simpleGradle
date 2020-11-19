package resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import models.Greeting;

@Path("/hello")
public class HelloResource {

	@GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting hello(@PathParam("param") String name) {
		System.out.println(name);
        return new Greeting(name);
    }
}
