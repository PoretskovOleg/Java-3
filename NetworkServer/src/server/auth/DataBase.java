package server.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DB_NAME = "java3_db";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private Connection dbConnection;

    public void connection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, LOGIN, PASSWORD);
    }

    public void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDbConnection() {
        return dbConnection;
    }
}
