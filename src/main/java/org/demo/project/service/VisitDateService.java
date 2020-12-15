package org.demo.project.service;

import org.demo.project.model.VisitDate;
import org.demo.project.repo.VisitDateRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class VisitDateService {

    @Inject
    VisitDateRepository getNumberOfVisitsInLastDays;

    public void confirmClientVisit(VisitDate visitDate) {
        try {
            getNumberOfVisitsInLastDays.confirmClientVisit(visitDate);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public List<VisitDate> getListOfVisitsDates(int id) {
        try {
            return getNumberOfVisitsInLastDays.getListOfVisitsDates(id);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

//    public int getNumberOfVisitsInLastDays(int clientId, int days) {
//        try {
//            return getNumberOfVisitsInLastDays.getNumberOfVisitsDays(clientId, days);
//        } catch (Exception e) {
//            throw new RuntimeException("", e);
//        }
//    }

}
