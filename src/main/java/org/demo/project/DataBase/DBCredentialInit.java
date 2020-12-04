package org.demo.project.DataBase;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@ApplicationScoped
public class DBCredentialInit {
    public static String getProperties(String propertyKey) {
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
