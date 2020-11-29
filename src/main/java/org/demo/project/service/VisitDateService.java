package org.demo.project.service;

import org.demo.project.model.VisitDate;
import org.demo.project.repo.ClubClientRepository;
import org.demo.project.repo.VisitDateRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class VisitDateService {



    @Inject
    VisitDateRepository visitDateRepository;

    public void confirmClientVisit(VisitDate visitDate) {
        try {
            visitDateRepository.confirmClientVisit(visitDate);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public List<VisitDate> getListOfVisitsDates(int id) {
        try {
            return visitDateRepository.getListOfVisitsDates(id);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public int getNumberOfVisitsDays(int id) {
        try {
            return visitDateRepository.getNumberOfVisitsDays(id);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

}
