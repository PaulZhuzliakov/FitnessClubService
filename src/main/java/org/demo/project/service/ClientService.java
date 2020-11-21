package org.demo.project.service;

import org.demo.project.model.Client;
import org.demo.project.repo.ClientRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class ClientService {

    @Inject
    ClientRepository clientRepository;

    public Client getClientById(Integer clientId) {
        try {
            return clientRepository.getPatientById(clientId);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }


    public List<Client> getListOfClients() {
        try {
            return clientRepository.getListOfClients();
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void createClient(Client client) {
        try {
            clientRepository.createClient(client);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void updateClient(Client client) {
        try {
            clientRepository.updateClient(client);
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
