package org.example.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static DriverManagerDataSource ds;

    private ConnectionManager() {}

    public static void setConfig(String url, String user, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        ds = dataSource;
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        if (ds != null) {
            return ds.getConnection();
        } else {
            throw new SQLException("Need to call setConfig() first");
        }
    }

    public static void close() throws SQLException {
        getConnection().close();
    }
}