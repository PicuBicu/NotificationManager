package com.client;

import com.data.Notification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {

    private ObjectInputStream connInput;

    public Receiver(Socket clientSocket) throws IOException {
        connInput = new ObjectInputStream(clientSocket.getInputStream());
    }

    public Notification receiveMessage() throws IOException, ClassNotFoundException {
        return (Notification)connInput.readObject();
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                Notification notification = receiveMessage();
                if (notification != null)
                    System.out.println(notification.getMessage());
            } catch (IOException e) {
                isRunning = false;
                System.out.println("Bład w trakcie wysyłania");
            } catch (ClassNotFoundException e) {
                System.out.println("Podana klasa nie została odnaleziona");
            }
        }
        close();
    }

    public void close() {
        try {
            connInput.close();
        } catch (IOException e) {
            System.out.println("Bład w trakcie zamykania strumienia wejścia");
        }
    }
}
