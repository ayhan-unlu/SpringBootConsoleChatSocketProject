package com.ayhanunlu.client;

import com.ayhanunlu.utils.ChatLogger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Service
public class ChatClient {
    private final int port = 1206;

    public static void main(String[] args) {
        try {
            Thread.sleep(2000);
            ChatClient chatClient = new ChatClient();
            chatClient.connectServer();
        } catch (RuntimeException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connectServer() {

        try (Socket socket = new Socket("localhost", port);) {
            System.out.println("Connected to the server on port: " + port);

            System.out.println("Please enter your message:");
            ChatLogger.logChatHeader();
            handleServer(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleServer(Socket socket) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            Scanner scanner = new Scanner(System.in);
            while (true) {

                String messageFromClient = scanner.nextLine();
                bufferedWriter.write(messageFromClient);
                bufferedWriter.newLine();
                bufferedWriter.flush();


                ChatLogger.logMessage("Client", messageFromClient);

                if (messageFromClient.equalsIgnoreCase("bye")) {
                    break;
                }

                String messageFromServer = bufferedReader.readLine();
                System.out.println("Server: " + messageFromServer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Disconnected from the server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
