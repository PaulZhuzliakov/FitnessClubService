package org.demo.project.resource;

import org.demo.project.model.MembershipCardCost;
import org.demo.project.service.MembershipCardCostService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/card-cost")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class MembershipCardCostResource {

    @Inject
    MembershipCardCostService membershipCardCostService;

    @GET
    @Path("/{id}")
    public MembershipCardCost getMembershipCardCost(
            @PathParam("id") int clientId) {
            return membershipCardCostService.getMembershipCardCost(clientId);
    }

}
