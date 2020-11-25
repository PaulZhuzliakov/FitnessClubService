package org.demo.project.resource;

import org.demo.project.model.ClubClient;
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
    public ClubClient getClientById(@PathParam("id") Integer clientId) {
        return clientService.getClientById(clientId);
    }

    @GET
    @Path("/searchByFIO")
    public ClubClient getClientByFIO(
            @QueryParam("lastname") String lastName,
            @QueryParam("firstname") String firstName,
            @QueryParam("middlename") String middleName) {
        return clientService.getClientByFIO(lastName, firstName, middleName);
    }

    @GET
    @Path("/getClientsByFIO")
    public List<ClubClient> getListOfClientsByFIO(
            @QueryParam("lastname") String lastName,
            @QueryParam("firstname") String firstName,
            @QueryParam("middlename") String middleName) {
        return clientService.getListOfClientsByFIO(lastName, firstName, middleName);
    }

    @GET
    @Path("/getAllClients")
    public List<ClubClient> getListOfClients() {
        return clientService.getListOfClients();
    }

    @POST
    @Path("/addByFIO")
    public void createClient(ClubClient clubClient) {
        clientService.createClient(clubClient);
    }

    @PUT
    @Path("update/")
    public void updateClient(ClubClient clubClient, @QueryParam("id") int id) {
        clientService.updateClient(clubClient, id);
    }

    @DELETE
    @Path("deleteById/{id}")
    public ClubClient deleteClientById(@PathParam("id") Integer clientId) {
        ClubClient client = clientService.getClientById(clientId);
        if (client.getId() != 0) {
            clientService.deleteClientById(clientId);
        }
        return client;
    }

}
