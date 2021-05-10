package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    public static final int PORT = 6666;

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server example = new Server();
        try {
            System.out.println("Oczekiwanie na pierwszego klienta");
            if (example.startServer(PORT) == null) {
                System.out.println("Nie udało się utworzyc polaczenia");
            } else {
                example.runServer();
            }
        } finally {
            System.out.println("Serwer wyłączony");
            example.closeServer();
        }
    }

    public ServerSocket startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            serverSocket = null;
        }
        return serverSocket;
    }

    public void runServer() {

        boolean isRunning = true;
        System.out.println("Server uruchomiony");

        while (isRunning) {
            try {
                Socket newClientSocket = serverSocket.accept(); // zwraca socket w momencie połaczenia klienta z serwerem
                ClientHandler newClientHandler = new ClientHandler(newClientSocket);
                newClientHandler.start();
            } catch (UnknownHostException e) {
                System.err.println("Błędne IP");
                isRunning = false;
            } catch (IOException e) {
                System.err.println("Bład w trakcie tworzenia połączenia");
            }
        }
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Bład w trakcie zamykania");
        }
    }

}
