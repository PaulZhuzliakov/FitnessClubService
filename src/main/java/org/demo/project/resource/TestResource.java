package org.demo.project.resource;

import org.demo.project.service.TestService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {


    @Inject
    TestService testService;

    //добавить требуемое количество посещений для клиента по его id
    @GET
    public void addVisits(@QueryParam("id") int id,
                          @QueryParam("days") int days) {
        testService.addVisits(id, days);
    }
}
