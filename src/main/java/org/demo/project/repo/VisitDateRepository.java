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
    public void confirmClientVisit(VisitDate visitDate) throws SQLException {
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
        List<Object> params = new ArrayList<>();
        params.add(currentDate);
        params.add(clientId);
        params.add(clientId);
        params.add(currentDate);
        dbUtils.insert(sql, params);
    }

    //возвращает список посещений одного клиента по его id
    public List<VisitDate> getListOfVisitsDates(int clientId) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE client_id=?";
        List<VisitDate> visitDates;
        List<Object> params = new ArrayList<>();
        params.add(clientId);
        visitDates = dbUtils.select(sql, params, resultSet -> {
            try {
                return visitDateMapper(resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        });
        return visitDates;
    }

    private VisitDate visitDateMapper (ResultSet resultSet) throws SQLException {
        VisitDate visitDate = new VisitDate();
        visitDate.setDate(resultSet.getDate("date").toLocalDate());
        visitDate.setClientId(resultSet.getInt("client_id"));
        return visitDate;
    }

}