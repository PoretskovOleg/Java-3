package server.auth;

public interface AuthService {

    void start();
    void stop();
    String getUserNameByLoginAndPass(String login, String password);
}
