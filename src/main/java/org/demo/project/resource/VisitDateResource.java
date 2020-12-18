package org.demo.project.resource;

import org.demo.project.model.VisitDate;
import org.demo.project.service.VisitDateService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/visits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitDateResource {

    @Inject
    VisitDateService visitDateService;

    //отметить сегодняшнее посещение
    @POST
    public void confirmClientVisit(VisitDate visitDate) {
        visitDateService.confirmClientVisit(visitDate);
    }

    //получить список посещений одного клиента по его id
    @GET
    @Path("/{clientId}")
    public List<VisitDate> getListOfVisitsDates(@PathParam("clientId") int clientId) {
        return visitDateService.getListOfVisitsDates(clientId);
    }

}
