package server;

import org.apache.log4j.Logger;
import server.auth.AuthService;
import server.auth.BaseAuthService;
import server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkServer {

    private static final Logger logger = Logger.getLogger(NetworkServer.class);
    private final int port;
    private AuthService authService;
    private final List<ClientHandler> clients = new ArrayList<>();

    public NetworkServer(int port) {
        this.port = port;
        this.authService = new BaseAuthService();
    }

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер был успешно запущен на порту " + port);
            logger.info("Сервер был успешно запущен на порту " + port);
            authService.start();
            while (true) {
                System.out.println("Ожидание клиентского подключения...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился");
                logger.info("Клиент подключился");
                createClientHandler(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера");
            logger.error("Ошибка при работе сервера", e);
            e.printStackTrace();
        } finally {
            authService.stop();
        }
    }

    private void createClientHandler(Socket clientSocket) {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.run();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void broadcastMessage(String message, ClientHandler owner, boolean isAddToHistory) throws IOException {
        owner.writeHistory("Я: " + message, isAddToHistory);
        logger.info("Пользователь " + owner.getNickname() + " прислал сообщение: " + message);
        String sendMessage = owner.getNickname() + ": " + message;
        for (ClientHandler client : clients) {
            if (client != owner) {
                client.sendMessage(sendMessage);
                client.writeHistory(sendMessage, isAddToHistory);
            }
        }
    }

    public synchronized void sendClientMessage(String message, String nickname, ClientHandler owner) throws IOException {
        owner.writeHistory("Я: " + message, true);
        logger.info("Пользователь " + owner.getNickname() + " прислал сообщение: " + message);
        String sendMessage = owner.getNickname() + ": " + message;
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nickname)) {
                client.sendMessage(sendMessage);
                client.writeHistory(sendMessage, true);
                break;
            }
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
