package org.demo.project.resource;

import org.demo.project.model.ClubClient;
import org.demo.project.model.VisitDate;
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

    //возвращает список всех клиентов
    @GET
    @Path("/getAllClients")
    public List<ClubClient> getListOfClients() {
        return clientService.getListOfClients();
    }

    //возвращает список клиентов по ФИО
    @GET
    @Path("/getClientsByFIO")
    public List<ClubClient> getListOfClientsByFIO(
            @QueryParam("lastname") String lastName,
            @QueryParam("firstname") String firstName,
            @QueryParam("middlename") String middleName) {
        return clientService.getListOfClientsByFIO(lastName, firstName, middleName);
    }

    //добавляет клиента
    @POST
    @Path("/addByFIO")
    public void createClient(ClubClient clubClient) {
        clientService.createClient(clubClient);
    }

    //редактирует клиента
    @PUT
    @Path("update/")
    public void updateClient(ClubClient clubClient, @QueryParam("id") int id) {
        clientService.updateClient(clubClient, id);
    }

    //удаление клиента
    @DELETE
    @Path("deleteById/{id}")
    public void deleteClientById(@PathParam("id") Integer clientId) {
        clientService.deleteClientById(clientId);
    }


    //ниже методы для с таблицой посещаемости. надо вынести в отдельный класс

    //отметить сегодняшнее посещение
    @POST
    @Path("/confirmClientVisit")
    public void confirmClientVisit(VisitDate visitDate) {
        clientService.confirmClientVisit(visitDate);
    }

    //получить список посещений одного клиента по его id
    @GET
    @Path("/viewVisits")
    public List<VisitDate> getListOfVisitsDates(@QueryParam("id") int id) {
        return clientService.getListOfVisitsDates(id);
    }

    //получить количество посещений клиента за год начиная с сегодняшнего дня по его id
    @GET
    @Path("/calcMembershipCard")
    public int getNumberOfVisitsDays(@QueryParam("id") int id) {
        return clientService.getNumberOfVisitsDays(id);
    }

}
