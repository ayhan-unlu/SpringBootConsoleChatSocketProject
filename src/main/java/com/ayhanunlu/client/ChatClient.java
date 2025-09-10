package com.ayhanunlu.client;

import com.ayhanunlu.SpringBootConsoleChatSocketProjectApplication;
import com.ayhanunlu.business.dto.ChatMessageDto;
import com.ayhanunlu.business.service.IChatMessageService;
import com.ayhanunlu.utils.ChatLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

@Service
public class ChatClient {
    private final int port = 1206;

    @Autowired
    IChatMessageService service;

    public static void main(String[] args) {
        try {
            Thread.sleep(2000);
//            SpringApplication.run(SpringBootConsoleChatSocketProjectApplication.class, args).getBean(ChatClient.class).connectServer();
            SpringApplication app = new SpringApplication(SpringBootConsoleChatSocketProjectApplication.class);
            app.setWebApplicationType(WebApplicationType.NONE);
            ConfigurableApplicationContext context = app.run(args);
            context.getBean(ChatClient.class).connectServer();

        } catch (RuntimeException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connectServer() {

        try (Socket socket = new Socket("localhost", port);) {
            System.out.println("Connected to the server on port: " + port);

            System.out.println("Please enter your message:");
            logChatHeader(LocalDateTime.now());
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
                LocalDateTime now = LocalDateTime.now();

                try {
                    logMessage(now, messageFromClient);
                    saveMessage(now, messageFromClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (messageFromClient.equalsIgnoreCase("bye")) {
                    System.out.println("The connection has been closed.");
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

    private void logChatHeader(LocalDateTime now) {
        ChatLogger.logChatHeader(now);
    }

    private void logMessage(LocalDateTime now, String messageFromClient) {
        ChatLogger.logMessage(now, "Client", messageFromClient);
    }

    private void saveMessage(LocalDateTime now, String messageFromClient) {
        service.saveMessage(
                ChatMessageDto
                        .builder()
                        .sender("Client").message(messageFromClient).timestamp(now)
                        .build()
        );
    }
}
