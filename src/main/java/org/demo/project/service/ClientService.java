package org.demo.project.service;

import org.demo.project.model.ClubClient;
import org.demo.project.model.VisitDate;
import org.demo.project.repo.ClientRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

//Класс получает запросы от методов из класса ClientResource, который принимает HTML-запросы
//и передаёт в класс ClientRepository, который работает с базой данных)
@RequestScoped
public class ClientService {

    @Inject
    ClientRepository clientRepository;

    public List<ClubClient> getListOfClientsByFIO(String lastName, String firstName, String middleName) {
        try {
            return clientRepository.getListOfClientsByFIO(lastName, firstName, middleName);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void createClient(ClubClient clubClient) {
        try {
            clientRepository.createClient(clubClient);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void deleteClientById(Integer clientId) {
        try {
            clientRepository.deleteClientById(clientId);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public List<ClubClient> getListOfClients() {
        try {
            return clientRepository.getListOfClients();
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void updateClient(ClubClient clubClient, int id) {
        try {
            clientRepository.updateClient(clubClient, id);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }


//ниже методы для с таблицой посещаемости. надо вынести в отдельный класс

    public void confirmClientVisit(VisitDate visitDate) {
        try {
            clientRepository.confirmClientVisit(visitDate);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public List<VisitDate> getListOfVisitsDates(int id) {
        try {
            return clientRepository.getListOfVisitsDates(id);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public int getNumberOfVisitsDays(int id) {
        try {
            return clientRepository.getNumberOfVisitsDays(id);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

}
