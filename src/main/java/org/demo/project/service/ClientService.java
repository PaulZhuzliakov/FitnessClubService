package org.demo.project.service;

import org.demo.project.model.Client;
import org.demo.project.repo.ClientRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
}
