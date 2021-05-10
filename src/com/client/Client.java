package com.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static final int PORT = 6666;
    public static final String LOCALHOST = "127.0.0.1";

    private Socket clientSocket;

    public static void main(String[] args) {
        Client client = new Client();
        try {
            System.out.println("Połączenie nawiązywane z [" + LOCALHOST + "] na porcie [" + PORT + "]");
            client.connectTo(LOCALHOST, PORT);

        } finally {
            if (client.clientSocket != null) {
                client.closeConnection();
            }
            System.out.println("Połączenie zakończone");
        }
    }

    public Socket connectTo(String hostName, int portNumber) {
        try {
            clientSocket = new Socket(hostName, portNumber);
            Sender senderThread = new Sender(clientSocket);
            senderThread.start();
            Receiver receiverThread = new Receiver(clientSocket);
            receiverThread.start();
            receiverThread.join();
            senderThread.stop();
            System.out.println("Nacisnij enter");
        } catch (UnknownHostException e) {
            System.out.println("Błędne IP");
        } catch (IOException e) {
            System.out.println("Bład w trakcie tworzenia połączenia");
        } catch (InterruptedException e) {
            System.out.println("Wątek przerwany");
        }
        return clientSocket;
    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Bład w trakcie zamykania połączenia");
        }
    }

}
