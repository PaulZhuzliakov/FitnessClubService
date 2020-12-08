package org.demo.project.service;

import org.demo.project.repo.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class TestService {

    @Inject
    TestRepository testRepository;


    public void addVisits(int id, int days) {
        try {
            testRepository.addVisits(id, days);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }
}
