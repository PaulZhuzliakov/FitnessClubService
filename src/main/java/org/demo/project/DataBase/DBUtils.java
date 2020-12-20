package org.demo.project.DataBase;

import org.demo.project.model.ClubClient;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class DBUtils {

    final static String url = System.getProperty("url");
    final static String user = System.getProperty("user");
    final static String pass = System.getProperty("pass");

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

        public List<ClubClient> select(
            String sql, Function<ResultSet, ClubClient> mapper) throws SQLException {

        try(Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<ClubClient> result = new ArrayList<>();
            while(rs.next()) {
                result.add(mapper.apply(rs));
            }
            return result;
        }
    }

}

//package org.demo.project.DataBase;
//
//        import javax.enterprise.context.ApplicationScoped;
//        import java.sql.*;
//
//@ApplicationScoped
//public class DBUtils {
//
//    final static String url = System.getProperty("url");
//    final static String user = System.getProperty("user");
//    final static String pass = System.getProperty("pass");
//
//    public Connection connect() {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(url, user, pass);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return connection;
//    }
//
////    select(String sql, resultSet -> { return new VisitDate(); //тут заполнение VisitDate из resultSet  } )
//
//}
