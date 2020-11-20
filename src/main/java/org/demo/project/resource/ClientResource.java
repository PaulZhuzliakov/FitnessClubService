package org.demo.project.resource;

import org.demo.project.model.Client;
import org.demo.project.service.ClientService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    ClientService clientService;

    @GET
    @Path("/{id}")
    public Client getClientById(@PathParam("id") Integer clientId) {
        return clientService.getClientById(clientId);
    }

}
