package org.demo.project.repo;

import org.demo.project.DataBase.DBCredentialInit;
import org.demo.project.model.ClubClient;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@ApplicationScoped
public class ClubClientRepository {
//    String url = "jdbc:postgresql://localhost:5432/fitness_club";
//    String user = "postgres";
//    String pass = "postgres";

    String url = DBCredentialInit.setProperties("url");
    String user = DBCredentialInit.setProperties("user");
    String pass = DBCredentialInit.setProperties("pass");

    public String setProperties(String propertyKey) {
        Properties prop = new Properties();
        String result = "";
        try (InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(inputStream);
            result = prop.getProperty(propertyKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //возвращает список всех клиентов
    public List<ClubClient> getListOfClients() {
        List<ClubClient> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ClubClient client = new ClubClient();
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

    //возвращает список клиентов по ФИО
    public List<ClubClient> getListOfClientsByFIO(String lastName, String firstName, String middleName) {
        List<ClubClient> clients = new ArrayList<>();
        String sql = "";
        if (!lastName.equals("") && !firstName.equals("") && !middleName.equals("")) {
            sql = "SELECT * FROM clients WHERE last_name = '" + lastName + "'"
                    + " AND first_name = '" + firstName + "'"
                    + " AND middle_name = '" + middleName + "'";
        } else if (!lastName.equals("") && !firstName.equals("") && middleName.equals("")) {
            sql = "SELECT * FROM clients WHERE last_name = '" + lastName + "'"
                    + " AND first_name = '" + firstName + "'";
        } else if (!lastName.equals("") && firstName.equals("") && !middleName.equals("")) {
            sql = "SELECT * FROM clients WHERE last_name = '" + lastName + "'"
                    + " AND middle_name = '" + middleName + "'";
        } else if (!lastName.equals("") && firstName.equals("") && middleName.equals("")) {
            sql = "SELECT * FROM clients WHERE last_name = '" + lastName + "'";
        } else if (lastName.equals("") && !firstName.equals("") && !middleName.equals("")) {
            sql = "SELECT * FROM clients WHERE first_name = '" + firstName + "'"
                    + " AND middle_name = '" + middleName + "'";
        } else if (lastName.equals("") && !firstName.equals("") && middleName.equals("")) {
            sql = "SELECT * FROM clients WHERE first_name = '" + firstName + "'";
        } else if (lastName.equals("") && firstName.equals("") && !middleName.equals("")) {
            sql = "SELECT * FROM clients WHERE middle_name = '" + middleName + "'";
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ClubClient client = new ClubClient();
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

    //добавляет клиента
    public void createClient(ClubClient clubClient) {
        String sql = "INSERT INTO clients (last_name, first_name, middle_name) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setString(1, clubClient.getLastName());
            preparedSt.setString(2, clubClient.getFirstName());
            preparedSt.setString(3, clubClient.getMiddleName());
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //редактирует клиента
    public void updateClient(ClubClient clubClient, int id) {
        String sql = "UPDATE clients SET last_name=?, first_name=?, middle_name=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setString(1, clubClient.getLastName());
            preparedSt.setString(2, clubClient.getFirstName());
            preparedSt.setString(3, clubClient.getMiddleName());
            preparedSt.setInt(4, id);
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //удаляет клиента по id
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
