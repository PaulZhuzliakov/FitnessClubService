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
                client.setPhoneNumber(resultSet.getString(6));
                client.setMail(resultSet.getString(7));
                clients.add(client);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return clients;
    }

    //возвращает список клиентов по ФИО
    public List<ClubClient> getListOfClientsByFIO(String lastName, String firstName, String middleName,
                                                  String phoneNumber, String eMail) {
        List<ClubClient> clients = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        boolean isFirst =true;
        sb.append("SELECT * FROM clients WHERE ");
        if (!lastName.equals("")) {
            sb.append("last_name = '" + lastName + "'");
            isFirst = false;
        }
        if (!firstName.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            sb.append(" first_name = '" + firstName + "'");
        }
        if (!middleName.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            sb.append(" middle_name = '" + middleName + "'");
        }
        if (!phoneNumber.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            sb.append(" phone_number = '" + phoneNumber + "'");
        }
        if (!eMail.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            sb.append(" e_mail = '" + eMail + "'");
        }

//        StringBuffer sb = new StringBuffer();
//        boolean isFirst =true;
//        sb.append("SELECT * FROM clients WHERE ");
//        if (!lastName.equals("")) {
//            sb.append("last_name = '" + lastName + "'");
//            isFirst = false;
//        }
//        if (!firstName.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            sb.append(" first_name = '" + firstName + "'");
//        }
//        if (!middleName.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            sb.append(" middle_name = '" + middleName + "'");
//        }
//        if (!phoneNumber.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            sb.append(" phone_number = '" + phoneNumber + "'");
//        }
//        if (!eMail.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            sb.append(" e_mail = '" + eMail + "'");
//        }

        String sql =sb.toString();
        System.out.println(sql);

        String sql1 = "SELECT * FROM clients WHERE last_name = '" + lastName + "'"
                + " AND first_name = '" + firstName + "'"
                + " AND middle_name = '" + middleName + "'";

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

                client.setPhoneNumber(resultSet.getString("phone_number"));
                client.setMail(resultSet.getString("e_mail"));
                clients.add(client);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return clients;
    }

    //добавляет клиента
    public void createClient(ClubClient clubClient) {
        String sql = "INSERT INTO clients (last_name, first_name, middle_name, phone_number, e_mail)" +
                " VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setString(1, clubClient.getLastName());
            preparedSt.setString(2, clubClient.getFirstName());
            preparedSt.setString(3, clubClient.getMiddleName());
            preparedSt.setString(4, clubClient.getPhoneNumber());
            preparedSt.setString(5, clubClient.getMail());
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //редактирует клиента
    public void updateClient(ClubClient clubClient, int id) {
        String sql = "UPDATE clients SET last_name=?, first_name=?, middle_name=?, phone_number=?, e_mail=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setString(1, clubClient.getLastName());
            preparedSt.setString(2, clubClient.getFirstName());
            preparedSt.setString(3, clubClient.getMiddleName());
            preparedSt.setString(4, clubClient.getPhoneNumber());
            preparedSt.setString(5, clubClient.getMail());
            preparedSt.setInt(6, id);
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
