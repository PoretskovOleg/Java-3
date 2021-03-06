package server.client;

import server.NetworkServer;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class ClientHandler {

    private final NetworkServer networkServer;
    private final Socket clientSocket;

    private DataInputStream in;
    private DataOutputStream out;

    private String nickname;
    private static final String DIR_HISTORY = "NetworkServer/src/server/client/history/";

    public ClientHandler(NetworkServer networkServer, Socket socket) {
        this.networkServer = networkServer;
        this.clientSocket = socket;
    }

    public void run(ExecutorService executorService) {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            executorService.execute(() -> {
                try {
                    authentication();
                    readMessages();
                } catch (IOException | SQLException e) {
                    System.out.println("Соединение с клиентом " + nickname + " было закрыто!");
                } finally {
                    closeConnection();
                }
            });

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
                networkServer.sendClientMessage(messageForClient, toNickName, this);
            } else {
                networkServer.broadcastMessage(message, this, true);
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
                    networkServer.broadcastMessage(String.format("Пользователь %s зашел в чат", userName), this, false);
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

    public void writeHistory(String message, boolean writeHistory) throws IOException {
        if (writeHistory) {
            FileWriter fileWriter = new FileWriter(DIR_HISTORY + "history_" + nickname + ".txt", true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(message);
            writer.newLine();
            writer.close();
        }
    }
}
