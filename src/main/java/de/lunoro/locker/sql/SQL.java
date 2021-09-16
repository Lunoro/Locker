package de.lunoro.locker.sql;

import de.lunoro.locker.config.Config;

import java.sql.*;

public class SQL {

    private Connection connection;
    private String database, host, port, username, password;

    private static SQL instance;

    private SQL() {
        Config config = Config.getInstance();
        this.database = config.getNode("mysql").getNode("database").getValue().toString();
        this.host = config.getNode("mysql").getNode("host").getValue().toString();
        this.port = config.getNode("mysql").getNode("port").getValue().toString();
        this.username = config.getNode("mysql").getNode("username").getValue().toString();
        this.password = config.getNode("mysql").getNode("password").getValue().toString();
        connect();
    }

    public void connect() {
        if (!isConnected()) {
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String qry) {
        try {
            System.out.println(connection);
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String query) {
        try {
            return this.connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public static SQL getInstance() {
        if (instance == null) {
            return new SQL();
        }
        return instance;
    }
}
