package org.demo.project.service;

import org.demo.project.repo.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class TestService {

    TestRepository testRepository = new TestRepository();

    public void addVisits(Integer id, Integer days) {
        try {
            testRepository.addVisits(id, days);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }
}
