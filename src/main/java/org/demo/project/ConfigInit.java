package org.demo.project;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigInit {
    public final static int membershipCardCostWithoutDiscount = Integer.parseInt(System.getProperty("membershipCardCostWithoutDiscount"));
    public final static int periodOfTimeInDays = Integer.parseInt(System.getProperty("periodOfTimeInDays"));
}
