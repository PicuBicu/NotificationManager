package com.server;

import com.data.Notification;
import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final ObjectInputStream connInput;
    private final ObjectOutputStream connOutput;
    private final Timer timer;

    public ClientHandler(Socket clientSocket) throws IOException {
        timer = new Timer();
        this.clientSocket = clientSocket;
        connInput = new ObjectInputStream(clientSocket.getInputStream());
        connOutput = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public Notification getNotification() throws IOException, ClassNotFoundException {
        return (Notification)connInput.readObject();
    }

    public void sendNotification(Notification notification) throws IOException {
        connOutput.writeObject(notification);
    }

    @Override
    public void run() {
        boolean isRunning = true;
        System.out.println("Dołączył nowy klient");
        while (isRunning) {
            try {
                Notification notification = getNotification();
                System.out.println("Otrzymano wiadomośc");
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            sendNotification(notification);
                        } catch (IOException e) {
                            System.out.println("Wystapił bład w trakcie wysyłania powiadomienia");
                        }
                    }
                };
                System.out.println("Data -> " + notification.getDate());
                timer.schedule(task, notification.getDate());
            } catch (IOException e) {
                System.out.println("Klient się rozłączył");
                isRunning = false;
            } catch (ClassNotFoundException e) {
                System.out.println("Podaja klasa nie została odnaleziona");
                isRunning = false;
            }
        }
        close();
    }
    
    public void close() {
        try {
            connInput.close();
            connOutput.close();
            clientSocket.close();
            timer.cancel();
        } catch (IOException e) {
            System.out.println("Bład w trakcie zamykania");
        }
    }
}
