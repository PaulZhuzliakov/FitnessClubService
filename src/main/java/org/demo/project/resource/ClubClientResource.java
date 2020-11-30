package org.demo.project.resource;

import org.demo.project.model.ClubClient;
import org.demo.project.service.ClubClientService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClubClientResource {

    @Inject
    ClubClientService clubClientService;

    //возвращает список всех клиентов
    @GET
    public List<ClubClient> getListOfClients() {
        return clubClientService.getListOfClients();
    }

    //возвращает список клиентов по ФИО
    @GET
    @Path("/get-by-fio")
    public List<ClubClient> getListOfClientsByFIO(
            @QueryParam("lastname") String lastName,
            @QueryParam("firstname") String firstName,
            @QueryParam("middlename") String middleName,
            @QueryParam("phonenumber") String phoneNumber,
            @QueryParam("email") String eMail) {
        return clubClientService.getListOfClientsByFIO(lastName, firstName, middleName, phoneNumber, eMail);
    }

    //добавляет клиента
    @POST
    public void createClient(ClubClient clubClient) {
        clubClientService.createClient(clubClient);
    }

    //редактирует клиента
    @PUT
    @Path("/{id}")
    public void updateClient(ClubClient clubClient, @PathParam("id") Integer id) {
        clubClientService.updateClient(clubClient, id);
    }

    //удаление клиента
    @DELETE
    @Path("/{id}")
    public void deleteClientById(@PathParam("id") Integer clientId) {
        clubClientService.deleteClientById(clientId);
    }

}
