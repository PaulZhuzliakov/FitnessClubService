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
        List<ClubClient> clients = new ArrayList<>();
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

    private ClubClient clientMapper (ResultSet resultSet) throws SQLException {
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



    //возвращает список клиентов по параметрам
    //создание SQL запроса по любому количеству любых введенных параметров. например, только отчество и телефон
    //для защиты от SQL инъекций, пришлось сделать код создания SQL-запроса более громоздким.
    public List<ClubClient> getListOfClientsByParams(String lastName, String firstName, String middleName,
                                                     String phoneNumber, String eMail) {

        List<ClubClient> clients = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        sb.append("SELECT * FROM clients WHERE ");
        boolean isLastNameExists = false;
        if (!lastName.equals("")) {
            isLastNameExists = true;
            sb.append(" last_name = ?");
            isFirst = false;
        }
        boolean isFirstNameExists = false;
        if (!firstName.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            isFirstNameExists = true;
            sb.append(" first_name = ?");
        }
        boolean isMiddleNameExists = false;
        if (!middleName.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            isMiddleNameExists = true;
            sb.append(" middle_name = ?");
        }
        boolean isPhoneNumberExists = false;
        if (!phoneNumber.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            isPhoneNumberExists = true;
            sb.append(" phone_number = ?");
        }
        boolean isEMailExists = false;
        if (!eMail.equals("")) {
            if (!isFirst)
                sb.append(" AND");
            isFirst = false;
            isEMailExists = true;
            sb.append(" e_mail = ?");
        }
        String sql = sb.toString();

        try (Connection connection = dbUtils.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int paramIndex = 0;
            if (isLastNameExists) {
                preparedStatement.setString(++paramIndex, lastName);
            }
            if (isFirstNameExists) {
                preparedStatement.setString(++paramIndex, firstName);
            }
            if (isMiddleNameExists) {
                preparedStatement.setString(++paramIndex, middleName);
            }
            if (isPhoneNumberExists) {
                preparedStatement.setString(++paramIndex, phoneNumber);
            }
            if (isEMailExists) {
                preparedStatement.setString(++paramIndex, eMail);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            fillListOfClientsWithResultSet(clients, resultSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        return clients;
    }

    //вспомогательный метод для GET запросов. Заполняет список клиентов данными из ResultSet
    private void fillListOfClientsWithResultSet(List<ClubClient> clients, ResultSet resultSet) throws SQLException {
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
    }

    //добавляет клиента
    public void createClient(ClubClient clubClient) {
        String sql = "INSERT INTO clients (last_name, first_name, middle_name, phone_number, e_mail)" +
                " VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbUtils.connect();
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
        try (Connection connection = dbUtils.connect();
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
    public void deleteClientById(int clientId) {
        String sql = "DELETE FROM clients WHERE id=?";
        try (Connection connection = dbUtils.connect();
             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
            preparedSt.setInt(1, clientId);
            preparedSt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}


//package org.demo.project.repo;
//
//        import org.demo.project.DataBase.DBUtils;
//        import org.demo.project.model.ClubClient;
//
//        import javax.enterprise.context.RequestScoped;
//        import javax.inject.Inject;
//        import java.sql.*;
//        import java.util.ArrayList;
//        import java.util.List;
//
//@RequestScoped
//public class ClubClientRepository {
//
//    @Inject
//    DBUtils dbUtils;
//
//    //возвращает список всех клиентов
//    public List<ClubClient> getListOfClients() {
//        List<ClubClient> clients = new ArrayList<>();
//        String sql = "SELECT * FROM clients";
//        try (Connection connection = dbUtils.connect();
//             Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(sql);
//            fillListOfClientsWithResultSet(clients, resultSet);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return clients;
//    }
//
//    //возвращает список клиентов по параметрам
//    //создание SQL запроса по любому количеству любых введенных параметров. например, только отчество и телефон
//    //для защиты от SQL инъекций, пришлось сделать код создания SQL-запроса более громоздким.
//    public List<ClubClient> getListOfClientsByParams(String lastName, String firstName, String middleName,
//                                                     String phoneNumber, String eMail) {
//
//        List<ClubClient> clients = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        boolean isFirst = true;
//        sb.append("SELECT * FROM clients WHERE ");
//        boolean isLastNameExists = false;
//        if (!lastName.equals("")) {
//            isLastNameExists = true;
//            sb.append(" last_name = ?");
//            isFirst = false;
//        }
//        boolean isFirstNameExists = false;
//        if (!firstName.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            isFirstNameExists = true;
//            sb.append(" first_name = ?");
//        }
//        boolean isMiddleNameExists = false;
//        if (!middleName.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            isMiddleNameExists = true;
//            sb.append(" middle_name = ?");
//        }
//        boolean isPhoneNumberExists = false;
//        if (!phoneNumber.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            isPhoneNumberExists = true;
//            sb.append(" phone_number = ?");
//        }
//        boolean isEMailExists = false;
//        if (!eMail.equals("")) {
//            if (!isFirst)
//                sb.append(" AND");
//            isFirst = false;
//            isEMailExists = true;
//            sb.append(" e_mail = ?");
//        }
//        String sql = sb.toString();
//
//        try (Connection connection = dbUtils.connect();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            int paramIndex = 0;
//            if (isLastNameExists) {
//                preparedStatement.setString(++paramIndex, lastName);
//            }
//            if (isFirstNameExists) {
//                preparedStatement.setString(++paramIndex, firstName);
//            }
//            if (isMiddleNameExists) {
//                preparedStatement.setString(++paramIndex, middleName);
//            }
//            if (isPhoneNumberExists) {
//                preparedStatement.setString(++paramIndex, phoneNumber);
//            }
//            if (isEMailExists) {
//                preparedStatement.setString(++paramIndex, eMail);
//            }
//            ResultSet resultSet = preparedStatement.executeQuery();
//            fillListOfClientsWithResultSet(clients, resultSet);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return clients;
//    }
//
//    //вспомогательный метод для GET запросов. Заполняет список клиентов данными из ResultSet
//    private void fillListOfClientsWithResultSet(List<ClubClient> clients, ResultSet resultSet) throws SQLException {
//        while (resultSet.next()) {
//            ClubClient client = new ClubClient();
//            client.setId(resultSet.getInt("id"));
//            client.setClubCardNumber(resultSet.getInt("club_card_number"));
//            client.setLastName(resultSet.getString("last_name"));
//            client.setFirstName(resultSet.getString("first_name"));
//            client.setMiddleName(resultSet.getString("middle_name"));
//            client.setPhoneNumber(resultSet.getString("phone_number"));
//            client.setMail(resultSet.getString("e_mail"));
//            clients.add(client);
//        }
//    }
//
//    //добавляет клиента
//    public void createClient(ClubClient clubClient) {
//        String sql = "INSERT INTO clients (last_name, first_name, middle_name, phone_number, e_mail)" +
//                " VALUES (?, ?, ?, ?, ?)";
//        try (Connection connection = dbUtils.connect();
//             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
//            preparedSt.setString(1, clubClient.getLastName());
//            preparedSt.setString(2, clubClient.getFirstName());
//            preparedSt.setString(3, clubClient.getMiddleName());
//            preparedSt.setString(4, clubClient.getPhoneNumber());
//            preparedSt.setString(5, clubClient.getMail());
//            preparedSt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//
//    //редактирует клиента
//    public void updateClient(ClubClient clubClient, int id) {
//        String sql = "UPDATE clients SET last_name=?, first_name=?, middle_name=?, phone_number=?, e_mail=? WHERE id=?";
//        try (Connection connection = dbUtils.connect();
//             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
//            preparedSt.setString(1, clubClient.getLastName());
//            preparedSt.setString(2, clubClient.getFirstName());
//            preparedSt.setString(3, clubClient.getMiddleName());
//            preparedSt.setString(4, clubClient.getPhoneNumber());
//            preparedSt.setString(5, clubClient.getMail());
//            preparedSt.setInt(6, id);
//            preparedSt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    //удаляет клиента по id
//    public void deleteClientById(int clientId) {
//        String sql = "DELETE FROM clients WHERE id=?";
//        try (Connection connection = dbUtils.connect();
//             PreparedStatement preparedSt = connection.prepareStatement(sql)) {
//            preparedSt.setInt(1, clientId);
//            preparedSt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//}
