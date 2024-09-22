package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private ConnectionManager() {}

    public static void setConfig() {
        try (InputStream inputStream = ConnectionManager.class.getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            config.setDriverClassName(prop.getProperty("driverClassName"));
            config.setJdbcUrl(prop.getProperty("jdbcUrl"));
            config.setUsername(prop.getProperty("username"));
            config.setPassword(prop.getProperty("password"));

            ds = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setConfig(String url, String user, String password) {
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (ds != null) {
            return ds.getConnection();
        } else {
            throw new SQLException("Need to call setConfig() first");
        }
    }

    public static void close() {
        ds.close();
    }
}