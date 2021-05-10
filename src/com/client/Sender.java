package com.client;

import com.data.BadDataFormatException;
import com.data.DateWrapper;
import com.data.Notification;

import java.io.*;

import java.net.Socket;

public class Sender extends Thread {

    private final ObjectOutputStream connOutput;
    private final BufferedReader stdInput;

    public Sender(Socket clientSocket) throws IOException {
        connOutput = new ObjectOutputStream(clientSocket.getOutputStream());
        stdInput = new BufferedReader(new InputStreamReader(System.in));
    }

    private String getFromInput() throws IOException {
        return stdInput.readLine();
    }

    private void sendMessage(Notification notification) throws IOException {
        try {
            connOutput.writeObject(notification);
        } catch (InvalidClassException e) {
            System.out.println("Zła klasa");
        }
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("Podaj treść wiadomości: ");
                String message = getFromInput();
                if (message.equals("exit")) {
                    break;
                }
                System.out.print("Podaj czas wykonania [hh:mm]: ");
                String timeToParse = getFromInput();
                if (timeToParse.equals("exit")) {
                    break;
                }
                Notification notification = new Notification(message, DateWrapper.parseDate(timeToParse));
                sendMessage(notification);
            } catch (IOException e) {
                isRunning = false;
                System.out.println("Bład w trakcie wysyłania wiadomości");
            } catch (BadDataFormatException e) {
                System.out.println("Zły format daty");
            }
        }
        close();
    }

    private void close() {
        try {
            stdInput.close();
            connOutput.close();
        } catch (IOException e) {
            System.out.println("Bład w trakcie zamykania strumienia wyjścia");
        }
    }


}
