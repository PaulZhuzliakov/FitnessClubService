package org.demo.project.repo;

import org.demo.project.DataBase.DBCredentialInit;
import org.demo.project.model.VisitDate;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VisitDateRepository {
//    final String url = "jdbc:postgresql://localhost:5432/fitness_club";
//    final String user = "postgres";
//    final String pass = "postgres";

    String url = DBCredentialInit.setProperties("url");
    String user = DBCredentialInit.setProperties("user");
    String pass = DBCredentialInit.setProperties("pass");

    //добавляет в таблицу посещаемости дату сегодняшнего посещения
    public void confirmClientVisit(VisitDate visitDate) {

        String sql = "INSERT INTO attendance (date, client_id)\n" +
                "SELECT '" + visitDate.getDate() + "' AS date, " + visitDate.getClientId() + " AS client_id FROM attendance\n" +
                "WHERE NOT EXISTS(\n" +
                "        SELECT id FROM attendance WHERE client_id = " + visitDate.getClientId() + " AND date = '" + visitDate.getDate() + "'\n" +
                "    )\n" +
                "LIMIT 1;";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //возвращает список посещений одного клиента по его id
    public List<VisitDate> getListOfVisitsDates(int id) {
        List<VisitDate> visitDates = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE client_id=" + id;
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
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

    //возвращает количество посещений клиента за год начиная с сегодняшнего дня по его id
    public int getNumberOfVisitsDays(int id) {
        int numberOfVisitsDays = 0;
        String sql = "SELECT * FROM attendance WHERE client_id=" + id;
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                numberOfVisitsDays++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return numberOfVisitsDays;
    }
}
