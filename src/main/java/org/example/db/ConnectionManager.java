package org.example.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ConnectionManager {
    String url;
    String user;
    String password;

    //TODO: think about DbParameters file (tests needs other params that normal work)
    public ConnectionManager()  throws IOException {
        try {
            List<String> strings = Files.readAllLines(Path.of("src", "main", "java", "org", "example", "db", "DbParameters"));

            url = strings.get(0);
            user = strings.get(1);
            password = strings.get(2);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public ConnectionManager(String url, String user, String password) throws IOException {
        Path path = Path.of("src", "main", "java", "org", "example", "db", "DbParameters");

        byte[] buffer;
        buffer = (url + "\n").getBytes();
        Files.write(path, buffer);

        buffer = (user + "\n").getBytes();
        Files.write(path, buffer, StandardOpenOption.APPEND);

        buffer = (password).getBytes();
        Files.write(path, buffer, StandardOpenOption.APPEND);

        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
}