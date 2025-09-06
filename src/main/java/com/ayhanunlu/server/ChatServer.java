package com.ayhanunlu.server;

import com.ayhanunlu.utils.ChatLogger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Service
public class ChatServer {

    private final int port = 1206;

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                handleClient(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while(true){
                String messageFromClient = bufferedReader.readLine();
                System.out.println("Client: "+messageFromClient);

                if(messageFromClient.equalsIgnoreCase("bye")){
                    break;
                }

                String messageFromServer = scanner.nextLine();
                bufferedWriter.write(messageFromServer);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                ChatLogger.logMessage("Server",messageFromServer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
