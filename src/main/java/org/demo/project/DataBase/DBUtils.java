package org.demo.project.DataBase;

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

    public <T> List<T> select(String sql, Function<ResultSet, T> mapper) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapper.apply(rs));
            }
            return result;
        }
    }

    public <T> List<T> select(String sql, List<Object> params, Function<ResultSet, T> mapper) throws SQLException {

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            ResultSet rs = ps.executeQuery();
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapper.apply(rs));
            }
            return result;
        }
    }

    public int selectCount(String sql, List<Object> params) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count;
        }
    }

    public void insert(String sql, List<Object> params) throws SQLException {

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            ps.executeUpdate();
        }
    }

}
