package org.demo.project.repo;

import org.demo.project.model.Client;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@ApplicationScoped
public class ClientRepository {
    final String url = "jdbc:postgresql://localhost:5432/fitness_club";
    final String user = "postgres";
    final String pass = "postgres";

    public Client getPatientById(Integer clientId) {
        Client client = new Client();
        String sql = "SELECT * FROM clients WHERE id = " + clientId;
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
                client.setClubCardNumber(resultSet.getInt(2));
                client.setLastName(resultSet.getString(3));
                client.setFirstName(resultSet.getString(4));
                client.setMiddleName(resultSet.getString(5));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return client;
    }

}
