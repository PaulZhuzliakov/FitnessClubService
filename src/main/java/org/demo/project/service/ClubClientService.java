package org.demo.project.service;

import org.demo.project.model.ClubClient;
import org.demo.project.repo.ClubClientRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class ClubClientService {

    @Inject
    ClubClientRepository clubClientRepository;

    public List<ClubClient> getListOfClientsByParams(String lastName, String firstName, String middleName,
                                                     String phoneNumber, String eMail) {
        try {
            return clubClientRepository.getListOfClientsByParams(lastName, firstName, middleName, phoneNumber, eMail);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public List<ClubClient> getListOfAllClients() {
        try {
            return clubClientRepository.getListOfClients();
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void createClient(ClubClient clubClient) {
        try {
            clubClientRepository.createClient(clubClient);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void deleteClientById(Integer clientId) {
        try {
            clubClientRepository.deleteClientById(clientId);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }


    public void updateClient(ClubClient clubClient, int id) {
        try {
            clubClientRepository.updateClient(clubClient, id);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }


}
