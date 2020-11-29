package org.demo.project.DataBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBCredentialInit {
    public static String setProperties(String propertyKey) {
        Properties prop = new Properties();
        String result = "";
        try (InputStream inputStream = DBCredentialInit.class
                .getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(inputStream);
            result = prop.getProperty(propertyKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
