package org.demo.project.resource;

import org.demo.project.model.ClubClient;
import org.demo.project.service.ClubClientService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClubClientResource {

    @Inject
    ClubClientService clubClientService;

    //возвращает список всех клиентов, если вызов без параметров
    //или список клиентов по введённым в клиенте параметрам
    @GET
    public List<ClubClient> getListOfClients(
            @QueryParam("lastname") String lastName,
            @QueryParam("firstname") String firstName,
            @QueryParam("middlename") String middleName,
            @QueryParam("phonenumber") String phoneNumber,
            @QueryParam("email") String eMail) {
        //если был вызван метод без параметров, то возвращается список всех клиентов.
        //Достаточно проверки одного параметра на null
        if (lastName == null) {
            return clubClientService.getListOfAllClients();
        } else {
            return clubClientService.getListOfClientsByParams(lastName, firstName, middleName, phoneNumber, eMail);
        }
    }

    //добавляет клиента
    @POST
    public void createClient(ClubClient clubClient) {
        clubClientService.createClient(clubClient);
    }

    //редактирует клиента по его id
    @PUT
    @Path("/{id}")
    public void updateClient(ClubClient clubClient, @PathParam("id") int clientId) {
        clubClientService.updateClient(clubClient, clientId);
    }

    //удаляет клиента по его id
    @DELETE
    @Path("/{id}")
    public void deleteClientById(@PathParam("id") int clientId) {
        clubClientService.deleteClientById(clientId);
    }

}
