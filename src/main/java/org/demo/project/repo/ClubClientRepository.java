package org.demo.project.repo;

import org.demo.project.DataBase.DBUtils;
import org.demo.project.model.ClubClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ClubClientRepository {

    @Inject
    DBUtils dbUtils;

    //возвращает список всех клиентов
    public List<ClubClient> getListOfClients() throws SQLException {
        String sql = "SELECT * FROM clients";
        List<ClubClient> clients;
        clients = dbUtils.select(sql, resultSet -> {
            try {
                return clientMapper(resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        });
        return clients;
    }

    //возвращает список клиентов соответствующих параметрам
    public List<ClubClient> getListOfClientsByParams(String lastName, String firstName, String middleName,
                                                     String phoneNumber, String eMail) throws SQLException {
        List<ClubClient> clients;
        String sql = "SELECT * FROM clients WHERE last_name = ? AND first_name = ? AND middle_name = ? AND phone_number = ? AND e_mail = ?";
        List<Object> params = new ArrayList<>();
        params.add(lastName);
        params.add(firstName);
        params.add(middleName);
        params.add(phoneNumber);
        params.add(eMail);
        clients = dbUtils.select(sql, params, resultSet -> {
            try {
                return clientMapper(resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        });
        return clients;
    }

    private ClubClient clientMapper(ResultSet resultSet) throws SQLException {
        ClubClient client = new ClubClient();
        client.setId(resultSet.getInt("id"));
        client.setClubCardNumber(resultSet.getInt("club_card_number"));
        client.setLastName(resultSet.getString("last_name"));
        client.setFirstName(resultSet.getString("first_name"));
        client.setMiddleName(resultSet.getString("middle_name"));
        client.setPhoneNumber(resultSet.getString("phone_number"));
        client.setMail(resultSet.getString("e_mail"));
        return client;
    }

    //удаляет клиента по id
    public void deleteClientById(int clientId) throws SQLException {
        String sql = "DELETE FROM clients WHERE id=?";
        List<Object> params = new ArrayList<>();
        params.add(clientId);
        dbUtils.insert(sql, params);
    }

    //добавляет клиента
    public void createClient(ClubClient clubClient) throws SQLException {
        String sql = "INSERT INTO clients (last_name, first_name, middle_name, phone_number, e_mail)" +
                " VALUES (?, ?, ?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(clubClient.getLastName());
        params.add(clubClient.getFirstName());
        params.add(clubClient.getMiddleName());
        params.add(clubClient.getPhoneNumber());
        params.add(clubClient.getMail());
        dbUtils.insert(sql, params);
    }

    //редактирует клиента
    public void updateClient(ClubClient clubClient, int id) throws SQLException {
        String sql = "UPDATE clients SET last_name=?, first_name=?, middle_name=?, phone_number=?, e_mail=? WHERE id=?";
        List<Object> params = new ArrayList<>();
        params.add(clubClient.getLastName());
        params.add(clubClient.getFirstName());
        params.add(clubClient.getMiddleName());
        params.add(clubClient.getPhoneNumber());
        params.add(clubClient.getMail());
        params.add(id);
        dbUtils.insert(sql, params);
    }

}
