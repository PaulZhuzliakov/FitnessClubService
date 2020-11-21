package org.demo.project.repo;

import org.demo.project.model.Client;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                client.setId(resultSet.getInt("id"));
                client.setClubCardNumber(resultSet.getInt("club_card_number"));
                client.setLastName(resultSet.getString("last_name"));
                client.setFirstName(resultSet.getString("first_name"));
                client.setMiddleName(resultSet.getString("middle_name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return client;
    }


    public List<Client> getListOfClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setClubCardNumber(resultSet.getInt("club_card_number"));
                client.setLastName(resultSet.getString("last_name"));
                client.setFirstName(resultSet.getString("first_name"));
                client.setMiddleName(resultSet.getString("middle_name"));
                clients.add(client);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return clients;
    }

    public void createClient(Client client) {
        String sql = "INSERT INTO clients VALUES (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setInt(1, client.getId());
            preparedSt.setInt(2, client.getClubCardNumber());
            preparedSt.setString(3, client.getLastName());
            preparedSt.setString(4, client.getFirstName());
            preparedSt.setString(5, client.getMiddleName());
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateClient(Client client) {
        String sql = "UPDATE clients SET last_name=?, first_name=?, middle_name=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setString(1, client.getLastName());
            preparedSt.setString(2, client.getFirstName());
            preparedSt.setString(3, client.getMiddleName());
            preparedSt.setLong(4, client.getId());
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteClientById(Integer clientId) {
        String sql = "DELETE FROM clients WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql);) {
            preparedSt.setInt(1, clientId);
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
