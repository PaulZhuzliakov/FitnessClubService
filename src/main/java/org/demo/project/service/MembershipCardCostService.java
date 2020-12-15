package org.demo.project.service;

import org.demo.project.model.MembershipCardCost;
import org.demo.project.repo.MembershipCardCostRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class MembershipCardCostService {

    @Inject
    MembershipCardCostRepository membershipCardCostRepository;


    public MembershipCardCost getMembershipCardCost(int clientId) {
        try {
            return membershipCardCostRepository.getMembershipCardCost(clientId);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }
}
