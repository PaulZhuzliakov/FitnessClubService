package org.demo.project.repo;

import org.demo.project.model.ClubClient;
import org.demo.project.model.VisitDate;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientRepository {
    final String url = "jdbc:postgresql://localhost:5432/fitness_club";
    final String user = "postgres";
    final String pass = "postgres";

    //ниже методы для работы работы с таблицей

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
        String sql = "SELECT * FROM clients WHERE last_name = '" + lastName + "'"
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

    //удаляет клиента
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


    //ниже методы для с таблицой посещаемости. надо вынести в отдельный класс

    //добавляет в таблицу посещаемости дату сегодняшнего посещения
    public void confirmClientVisit(VisitDate visitDate) {

        String sql = "INSERT INTO attendance (date, client_id)\n" +
                "SELECT '" + visitDate.getDate() + "' AS date, " + visitDate.getClientId() + " AS client_id FROM attendance\n" +
                "WHERE NOT EXISTS(\n" +
                "        SELECT id FROM attendance WHERE client_id = " + visitDate.getClientId() + " AND date = '" + visitDate.getDate() + "'\n" +
                "    )\n" +
                "LIMIT 1;";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fitness_club",
                "postgres", "postgres");
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
