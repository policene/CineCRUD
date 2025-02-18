package com.policene.cinecrud.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFabric {


    public static Connection createConnection() {
        Connection conn = null;
        try {
            Properties properties = new Properties();

            InputStream input = ConnectionFabric.class.getClassLoader().getResourceAsStream("db_config.properties");


            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");


            conn = DriverManager.getConnection(url, user, password);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }

}
