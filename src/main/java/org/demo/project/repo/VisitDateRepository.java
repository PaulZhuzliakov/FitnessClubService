package org.demo.project.repo;

import org.demo.project.DataBase.DBUtils;
import org.demo.project.model.VisitDate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class VisitDateRepository {

    @Inject
    DBUtils dbUtils;

    //добавляет в таблицу посещаемости дату сегодняшнего посещения
    public void confirmClientVisit(VisitDate visitDate) {
        Date currentDate = Date.valueOf(LocalDate.now());
        int clientId = visitDate.getClientId();
        String sql = new StringBuilder()
                .append("INSERT INTO attendance (date, client_id)\n")
                .append("SELECT ? AS date, ? AS client_id FROM attendance\n")
                .append("WHERE NOT EXISTS(\n")
                .append("SELECT id FROM attendance WHERE client_id = ? AND date = ?\n")
                .append("    )\n")
                .append("LIMIT 1;")
                .toString();
        try (Connection connection = dbUtils.connect();
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setDate(1, currentDate);
            preparedSt.setInt(2, clientId);
            preparedSt.setInt(3, clientId);
            preparedSt.setDate(4, currentDate);
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //возвращает список посещений одного клиента по его id
    public List<VisitDate> getListOfVisitsDates(int id) {
        List<VisitDate> visitDates = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE client_id=?";
        try (Connection connection = dbUtils.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VisitDate visitDate = new VisitDate();
                visitDate.setDate(resultSet.getDate("date"));
                visitDate.setClientId(resultSet.getInt("client_id"));
                visitDates.add(visitDate);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return visitDates;
    }

    //возвращает количество посещений клиента по его id за год
    // начиная с сегодняшнего дня за заданное количество дней
    public int getNumberOfVisitsDays(int clientId, int days) {
        int numberOfVisitsDays = 0;
        String sql = new StringBuilder().append("SELECT COUNT(attendance.date ) \n")
                .append("FROM attendance\n")
                .append("WHERE client_id = ?")
                .append(" AND attendance.date > NOW() - INTERVAL '").append(days).append(" DAYS'")
                .toString();
        try (Connection connection = dbUtils.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            numberOfVisitsDays = resultSet.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return numberOfVisitsDays;
    }


}
