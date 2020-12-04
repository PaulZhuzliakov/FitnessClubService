package org.demo.project.repo;

import org.demo.project.DataBase.DBCredentialInit;
import org.demo.project.model.VisitDate;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class VisitDateRepository {

    String url = DBCredentialInit.getProperties("url");
    String user = DBCredentialInit.getProperties("user");
    String pass = DBCredentialInit.getProperties("pass");

    //добавляет в таблицу посещаемости дату сегодняшнего посещения
    public void confirmClientVisit(VisitDate visitDate) {
        //переписать полученте даты
        java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        int clientId = visitDate.getClientId();
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(
                     new StringBuilder()
                     .append("INSERT INTO attendance (date, client_id)\n")
                     .append("SELECT ? AS date, ? AS client_id FROM attendance\n")
                     .append("WHERE NOT EXISTS(\n")
                     .append("SELECT id FROM attendance WHERE client_id = ? AND date = ?\n")
                     .append("    )\n")
                     .append("LIMIT 1;")
                     .toString()
             )) {
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
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM attendance WHERE client_id=?")) {
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

    //возвращает количество посещений клиента за год начиная с сегодняшнего дня по его id
    public int getNumberOfVisitsDays(int id) {
        int numberOfVisitsDays = 0;
//        String sql = new StringBuilder()
//                .append("SELECT * FROM attendance WHERE client_id=")
//                .append(id)
//                .toString();
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM attendance WHERE client_id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numberOfVisitsDays++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return numberOfVisitsDays;
    }
}
