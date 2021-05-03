package com.client;

import com.data.Notification;

import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;


public class Sender extends Thread {

    private ObjectOutputStream connOutput;
    private BufferedReader stdInput;

    public Sender(Socket clientSocket) throws IOException {
        connOutput = new ObjectOutputStream(clientSocket.getOutputStream());
        stdInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getFromInput() throws IOException {
        return stdInput.readLine();
    }

    public Date parseDate(String message) {
        String[] items = message.split(":");
        for (String item : items) {
            System.out.println(Integer.parseInt(item));
        }
        Date date = new Date();
        date.setMonth(Calendar.MAY);
        date.setHours(Integer.parseInt(items[0]));
        date.setMinutes(Integer.parseInt(items[1]));
        date.setSeconds(Integer.parseInt(items[2]));
        return date;
    }
//    wiadmodwa
//    00:02:30
    public void sendMessage(Notification notification) throws IOException {
        try {
            connOutput.writeObject(notification);
        } catch (InvalidClassException e) {
            System.out.println("XDX");
        }
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("Podaj treść wiadomości: ");
                String message = getFromInput();
                System.out.print("Podaj czas wykonania [hh:mm:ss]: ");
                String timeToParse = getFromInput();
                if (message.equals("exit") || timeToParse.equals("exit")) {
                    isRunning = false;
                }
                Notification notification = new Notification(message, parseDate(timeToParse));
                sendMessage(notification);
            } catch (IOException e) {
                isRunning = false;
                System.out.println("Bład w trakcie wysyłania wiadomości");
            }
        }
        close();
    }

    public void close() {
        try {
            stdInput.close();
            connOutput.close();
        } catch (IOException e) {
            System.out.println("Bład w trakcie zamykania strumienia wejścia");
        }
    }


}
