package com.client;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static final int PORT = 6666;
    public static final String LOCALHOST = "127.0.0.1";

    private Socket clientSocket;
    private Sender senderThread;
    private Receiver receiverThread;

    /* Handling client opening. */
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

    /* Creating connection between client and the server */
    public Socket connectTo(String hostName, int portNumber) {
        try {
            clientSocket = new Socket(hostName, portNumber);
            senderThread = new Sender(clientSocket);
            senderThread.start();
            receiverThread = new Receiver(clientSocket);
            receiverThread.start();
            senderThread.join();
            receiverThread.join();
        } catch (UnknownHostException e) {
            System.out.println("Błędne IP");
        } catch (IOException e) {
            System.out.println("Bład w trakcie tworzenia połączenia");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientSocket;
    }

    /* Closing client side connection. */
    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Bład w trakcie zamykania połączenia");
        }
    }

}
