package server.client;

import server.NetworkServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {

    private final NetworkServer networkServer;
    private final Socket clientSocket;

    private DataInputStream in;
    private DataOutputStream out;

    private String nickname;

    public ClientHandler(NetworkServer networkServer, Socket socket) {
        this.networkServer = networkServer;
        this.clientSocket = socket;
    }

    public void run() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            new Thread(() -> {
                try {
                    authentication();
                    readMessages();
                } catch (IOException | SQLException e) {
                    System.out.println("Соединение с клиентом " + nickname + " было закрыто!");
                } finally {
                    closeConnection();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        networkServer.unsubscribe(this);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            String message = in.readUTF();
            if ("/end".equals(message)) {
                break;
            } else if (message.startsWith("/w")) {
                String[] arMessage = message.split("\\s+", 3);
                String toNickName = arMessage[1];
                String messageForClient = arMessage[2];
                networkServer.sendClientMessage(messageForClient, toNickName);
            } else {
                networkServer.broadcastMessage(nickname + ": " + message, this);
            }
        }
    }

    private void authentication() throws IOException, SQLException {
        while (true) {
            String message = in.readUTF();
            if (message.startsWith("/auth")) {
                String[] arMessage = message.split("\\s+", 3);
                String login = arMessage[1];
                String password = arMessage[2];
                String userName = networkServer.getAuthService().getUserNameByLoginAndPass(login, password);

                if (userName == null) {
                    sendMessage("Не верно введены логин или пароль");
                } else {
                    nickname = userName;
                    networkServer.broadcastMessage(String.format("Пользователь %s зашел в чат", userName), this);
                    sendMessage("/auth " + userName);
                    networkServer.subscribe(this);
                    break;
                }
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public String getNickname() {
        return nickname;
    }
}
