package org.demo.project.DataBase;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;

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

    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .username(user)
                .password(pass)
                .url(url)
                .build();
    }

}
