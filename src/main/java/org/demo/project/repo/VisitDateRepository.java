package org.demo.project.repo;

import org.demo.project.DataBase.DBUtils;
import org.demo.project.model.VisitDate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
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


//    //Эксперимент со Spring JDBC
//
//    //возвращает список посещений одного клиента по его id
//    public List<VisitDate> getListOfVisitsDates(int id) {
//        String sql = new StringBuilder("SELECT * FROM attendance WHERE client_id=").append(id).toString();
//        DataSource dataSource = dbUtils.getDataSource();
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        return jdbcTemplate.query(sql, (rs, rowNum) ->
//                new VisitDate(
//                        rs.getInt("client_id"),
//                        rs.getDate("date").toLocalDate()
//                )
//        );
//    }


    //возвращает список посещений одного клиента по его id
    public List<VisitDate> getListOfVisitsDates(int clientId) {
        List<VisitDate> visitDates = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE client_id=?";
        try (Connection connection = dbUtils.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VisitDate visitDate = new VisitDate();
                visitDate.setDate(resultSet.getDate("date").toLocalDate());
                visitDate.setClientId(resultSet.getInt("client_id"));
                visitDates.add(visitDate);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return visitDates;
    }

}