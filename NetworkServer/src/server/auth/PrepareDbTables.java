package server.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDbTables {

    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        try{
            dataBase.connection();
            Connection connection = dataBase.getDbConnection();
            createUsersTable(connection);
            addUsers(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            dataBase.closeConnection();
        }
    }

    private static void createUsersTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String query = "CREATE  TABLE IF NOT EXISTS users(" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "login VARCHAR(100) NOT NULL, " +
                "password VARCHAR(40) NOT NULL, " +
                "nickname VARCHAR(100) NOT NULL, " +
                "PRIMARY KEY ( id )" +
                ")";

        statement.execute(query);
    }

    private static void addUsers(Connection connection) throws SQLException {
        String query = "INSERT INTO users(login, password, nickname) VALUES(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 1; i < 6; i++) {
            statement.setString(1, "user" + i);
            statement.setString(2, "pass" + i);
            statement.setString(3, "username" + i);
            statement.addBatch();
        }
        statement.executeBatch();
    }
}
