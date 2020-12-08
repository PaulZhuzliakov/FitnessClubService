package org.demo.project.DataBase;

import org.demo.project.CredentialInit;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;

@ApplicationScoped
public class DBUtils {

    final String url = CredentialInit.getProperty("url");
    final String user = CredentialInit.getProperty("user");
    final String pass = CredentialInit.getProperty("pass");

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}