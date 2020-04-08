package client.controller;

import client.model.NetworkService;
import client.view.AuthDialog;
import client.view.ClientChat;

import java.io.*;
import java.util.ArrayList;

public class ClientController {

    private final NetworkService networkService;
    private final AuthDialog authDialog;
    private final ClientChat clientChat;
    private String nickname;
    private static final String DIR_HISTORY = "NetworkServer/src/server/client/history/";
    private static final int HISTORY_LINES = 10;

    public ClientController(String serverHost, int serverPort) {
        this.networkService = new NetworkService(serverHost, serverPort);
        this.authDialog = new AuthDialog(this);
        this.clientChat = new ClientChat(this);
    }

    public void runApplication() throws IOException {
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() {
        networkService.setSuccessfulAuthEvent(nickname -> {
            setUserName(nickname);
            openChat();
        });
        authDialog.setVisible(true);

    }

    private void openChat() {
        authDialog.dispose();
        networkService.setMessageHandler(clientChat::appendMessage);
        clientChat.setTitle(nickname);
        clientChat.setVisible(true);
        showHistory();
    }

    private void showHistory() {
        StringBuilder history = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(DIR_HISTORY + "history_" + nickname + ".txt");
            BufferedReader reader = new BufferedReader(fileReader);

            ArrayList<String> historyList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                historyList.add(line);
            }

            int startIndex = 0;
            int endIndex = historyList.size();
            if (endIndex > HISTORY_LINES) {
                startIndex = endIndex - HISTORY_LINES;
            }

            for (int i = startIndex; i < endIndex; i++) {
                history.append(historyList.get(i));
                if (i < endIndex - 1) {
                    history.append(System.lineSeparator());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        clientChat.showHistory(history.toString());
    }

    private void setUserName(String nickname) {
        this.nickname = nickname;
    }

    private void connectToServer() throws IOException {
        try {
            networkService.connect();
        } catch (IOException e) {
            System.err.println("Failed to establish server connection");
            throw e;
        }
    }

    public void sendAuthMessage(String login, String pass) throws IOException {
        networkService.sendAuthMessage(login, pass);
    }

    public void sendMessage(String message) {
        try {
            networkService.sendMessage(message);
        } catch (IOException e) {
            clientChat.showError("Failed to send message!");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        networkService.close();
    }

    public String getUsername() {
        return nickname;
    }

    public void sendMessageForUser(String userNick, String message) {
        sendMessage(String.format("/w %s %s", userNick, message));
    }
}
