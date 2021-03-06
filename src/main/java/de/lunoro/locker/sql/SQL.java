package de.lunoro.locker.sql;

import de.lunoro.locker.config.Config;

import java.sql.*;

public final class SQL {

    private Connection connection;
    private String database, host, port, username, password;

    private static SQL instance;

    private SQL() {
        instance = this;
        Config config = Config.getInstance();
        this.database = config.getNode("mysql").getNode("database").getValue().toString();
        this.host = config.getNode("mysql").getNode("host").getValue().toString();
        this.port = config.getNode("mysql").getNode("port").getValue().toString();
        this.username = config.getNode("mysql").getNode("username").getValue().toString();
        this.password = config.getNode("mysql").getNode("password").getValue().toString();
        if (!isConnected()) {
            connect();
            System.out.println("New Connection opened!");
        }
    }

    public void connect() {
        System.out.println("BEFORE CON " + isConnected());
        if (!isConnected()) {
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                System.out.println("AFTER CON " + isConnected());
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
        if (Config.getInstance().getNode("useSql").getBoolean()) {
            try {
                Statement statement = this.connection.createStatement();
                statement.executeUpdate(qry);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getResult(String query) {
        if (Config.getInstance().getNode("useSql").getBoolean()) {
            try {
                return this.connection.createStatement().executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isConnected() {
        System.out.println(this.connection);
        return this.connection != null;
    }

    public static SQL getInstance() {
        if (instance == null) {
            return new SQL();
        }
        return instance;
    }
}
