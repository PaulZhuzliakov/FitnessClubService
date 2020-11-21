package org.demo.project.resource;

import org.demo.project.model.Client;
import org.demo.project.service.ClientService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

    @GET
    public List<Client> getListOfClients() {
        return clientService.getListOfClients();
    }

    @POST
    public Client createClient(Client client) {
        clientService.createClient(client);
        return client;
    }


    @PUT
    public Client updateClient(Client client) {
        clientService.updateClient(client);
        return client;
    }

    @DELETE
    @Path("/{id}")
    public Client deleteClientById(@PathParam("id") Integer clientId) {
        Client client = clientService.getClientById(clientId);
        if (client.getId() != 0) {
            clientService.deleteClientById(clientId);
        }
        return client;
    }

}
