package bg.venkov.darin.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dummy")
public class Service {

    @GET
    @Path("/person")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPerson() {
		System.out.println("Request received");
        Person person = new Person();
        person.setName("DummyPerson");
        return Response.ok(person).build();
    }

}
