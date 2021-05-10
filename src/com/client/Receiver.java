package com.client;

import com.data.Notification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {

    private final ObjectInputStream connInput;

    public Receiver(Socket clientSocket) throws IOException {
        connInput = new ObjectInputStream(clientSocket.getInputStream());
    }

    private Notification receiveMessage() throws IOException, ClassNotFoundException {
        return (Notification)connInput.readObject();
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                Notification notification = receiveMessage();
                if (notification != null)
                    System.out.print("\n" + notification.getMessage() + "\nPodaj treść wiadomości: ");
            } catch (IOException e) {
                isRunning = false;
            } catch (ClassNotFoundException e) {
                isRunning = false;
                System.out.println("Podana klasa nie została odnaleziona");
            }
        }
        System.out.println("\nWatek sie zakonczył");
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
