package org.demo.project.repo;

import org.demo.project.ConfigInit;
import org.demo.project.DataBase.DBUtils;
import org.demo.project.model.MembershipCardCost;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class MembershipCardCostRepository {

    @Inject
    DBUtils dbUtils;

    //возвращает объект MembershipCardCost с данными о стоимости абонемент
    public MembershipCardCost getMembershipCardCost(int clientId) {
        MembershipCardCost cardCost = new MembershipCardCost();
        int periodOfTimeInDays = ConfigInit.periodOfTimeInDays;
        int membershipCardCostWithoutDiscount = ConfigInit.membershipCardCostWithoutDiscount;
        LocalDate today = LocalDate.now();
        int daysVisitedInPeriodOfTime = getNumberOfVisitedDaysInPeriodOfTime(clientId, periodOfTimeInDays, today);
        float percentageOfVisitedDaysInPeriodOfTime = (float) daysVisitedInPeriodOfTime / periodOfTimeInDays * 100;

        int membershipCardCostWithDiscount;
        int discount;
        cardCost.setPeriodOfTimeInDays(periodOfTimeInDays);
        cardCost.setDaysVisitedInPeriodOfTime(daysVisitedInPeriodOfTime);
        cardCost.setPercentageOfVisitedDaysInPeriodOfTime(percentageOfVisitedDaysInPeriodOfTime);
        cardCost.setMembershipCardCostWithoutDiscount(membershipCardCostWithoutDiscount);
        if (percentageOfVisitedDaysInPeriodOfTime < 35) {
            discount = 0;
            membershipCardCostWithDiscount = membershipCardCostWithoutDiscount;
        } else if (percentageOfVisitedDaysInPeriodOfTime >= 35 && percentageOfVisitedDaysInPeriodOfTime < 60) {
            discount = 10;
            membershipCardCostWithDiscount = (int) (membershipCardCostWithoutDiscount * 0.9);
        } else {
            discount = 15;
            membershipCardCostWithDiscount = (int) (membershipCardCostWithoutDiscount * 0.85);
        }
        cardCost.setDiscount(discount);
        cardCost.setMembershipCardCostWithDiscount(membershipCardCostWithDiscount);
        return cardCost;
    }

    //возвращает количество посещений по id клиента за период времени, начиная с заданной даты, отсчёт идет в обатную сторону
    //например, если задан период 365 с сегодняшнейго дня -> вернет кол-во посещений за последний год
    public int getNumberOfVisitedDaysInPeriodOfTime(int clientId, int periodOfTimeInDays, LocalDate date) {
        String sql = new StringBuilder().append("SELECT COUNT(attendance.date ) \n")
                .append("FROM attendance\n")
                .append("WHERE client_id = ?")
                .append(" AND attendance.date > date '").append(date).append("' - INTERVAL '").append(periodOfTimeInDays).append(" DAYS'")
                .toString();
        List<Object> params = new ArrayList<>();
        params.add(clientId);
        try {
            return dbUtils.selectCount(sql, params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clientId;
    }

}