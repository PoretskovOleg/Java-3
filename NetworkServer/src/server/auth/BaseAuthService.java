package server.auth;

import java.sql.*;

public class BaseAuthService implements AuthService {

    private Connection connection;

    @Override
    public void start() {
        DataBase db = new DataBase();
        try {
            db.connection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = db.getDbConnection();
        System.out.println("Сервис атентификации запущен");
    }

    @Override
    public void stop() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public String getUserNameByLoginAndPass(String login, String password) {

        String userName = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                userName = result.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userName;
    }
}
