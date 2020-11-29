package org.demo.project.resource;

import org.demo.project.model.VisitDate;
import org.demo.project.service.TestService;
import org.demo.project.service.VisitDateService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {

    TestService testService = new TestService();

    //добавить требуемое количество посещений для клиента по его id
    @GET
    public void addVisits(@QueryParam("id") Integer id,
                          @QueryParam("days") Integer days) {
        testService.addVisits(id, days);
    }
}
