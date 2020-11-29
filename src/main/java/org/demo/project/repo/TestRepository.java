package org.demo.project.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestRepository {
    public void addVisits(Integer id, Integer days) {
        final String url = "jdbc:postgresql://localhost:5432/fitness_club";
        final String user = "postgres";
        final String pass = "postgres";
        java.sql.Date setDate = new java.sql.Date(new java.util.Date().getTime());
        for (int i = 0; i < days; i++, setDate = new java.sql.Date( setDate.getTime() - 24*60*60*1000)) {
            String sql = "INSERT INTO attendance (date, client_id)\n" +
                    "SELECT '" + setDate + "' AS date, " + id + " AS client_id FROM attendance\n" +
                    "WHERE NOT EXISTS(\n" +
                    "        SELECT id FROM attendance WHERE client_id = " + id + " AND date = '" + setDate + "'\n" +
                    "    )\n" +
                    "LIMIT 1;";
            try (Connection connection = DriverManager.getConnection(url, user, pass);
                 PreparedStatement preparedSt = connection.prepareStatement(sql)) {
                preparedSt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
