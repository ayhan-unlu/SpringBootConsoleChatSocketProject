package com.ayhanunlu.server;

import com.ayhanunlu.SpringBootConsoleChatSocketProjectApplication;
import com.ayhanunlu.business.dto.ChatMessageDto;
import com.ayhanunlu.business.service.IChatMessageService;
import com.ayhanunlu.business.service.impl.ChatMessageServiceImpl;
import com.ayhanunlu.utils.ChatLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

@Service
public class ChatServer {

    private final int port = 1206;

    @Autowired
    IChatMessageService service;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleChatSocketProjectApplication.class, args).getBean(ChatServer.class).startServer();
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

            while (true) {
                String messageFromClient = bufferedReader.readLine();
                System.out.println("Client: " + messageFromClient);

                if (messageFromClient.equalsIgnoreCase("bye")) {
                    break;
                }

                String messageFromServer = scanner.nextLine();
                bufferedWriter.write(messageFromServer);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                LocalDateTime now = LocalDateTime.now();
                try {
                    logMessage(now, messageFromServer);
                    saveMessage(now, messageFromServer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void logMessage(LocalDateTime now, String message) {
        ChatLogger.logMessage(now, "Server", message);
    }

    private void saveMessage(LocalDateTime now, String messageFromServer) {
        service.saveMessage(
                ChatMessageDto
                        .builder()
                        .sender("Server").message(messageFromServer).timestamp(now)
                        .build());
    }
}
