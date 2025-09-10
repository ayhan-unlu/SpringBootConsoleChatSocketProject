package com.ayhanunlu.config;

import com.ayhanunlu.client.ChatClient;
import com.ayhanunlu.server.ChatServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class ServerConfig {

    @Bean
    public CommandLineRunner runServer(ChatServer chatServer){
        return args -> chatServer.startServer();
    }

    @Bean
    public CommandLineRunner runClient(ChatClient chatClient){
        return args -> chatClient.connectServer();
    }
}
