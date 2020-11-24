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
    public ClubClient updateClient(ClubClient client) {
        clientService.updateClient(client);
        return client;
    }

    @DELETE
    @Path("/{id}")
    public ClubClient deleteClientById(@PathParam("id") Integer clientId) {
        ClubClient client = clientService.getClientById(clientId);
        if (client.getId() != 0) {
            clientService.deleteClientById(clientId);
        }
        return client;
    }

//    @GET
//    public List<Client> getListOfClients(
//            @QueryParam("lastname") String lastName,
//            @QueryParam("firstname") String firstName,
//            @QueryParam("middlename") String middleName)
//    {
//        return clientService.getListOfClients(lastName, firstName, middleName);
//    }

//    @POST
//    public ClubClient createClient(ClubClient client) {
//        clientService.createClient(client);
//        return client;
//    }

}
