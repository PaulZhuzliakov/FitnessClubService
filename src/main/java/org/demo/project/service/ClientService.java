package org.demo.project.service;

import org.demo.project.model.ClubClient;
import org.demo.project.repo.ClientRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class ClientService {

    @Inject
    ClientRepository clientRepository;

    public ClubClient getClientByFIO(String lastName, String firstName, String middleName) {
        try {
            return clientRepository.getClientByFIO(lastName, firstName, middleName);
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

    public ClubClient getClientById(Integer clientId) {
        try {
            return clientRepository.getClientById(clientId);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public List<ClubClient> getListOfClientsByFIO(String lastName, String firstName, String middleName) {
        try {
            return clientRepository.getListOfClientsByFIO(lastName, firstName, middleName);
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

    public void deleteClientById(Integer clientId) {
        try {
            clientRepository.deleteClientById(clientId);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

}
