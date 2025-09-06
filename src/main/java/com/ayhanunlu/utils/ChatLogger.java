package com.ayhanunlu.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ChatLogger {

    public static void logMessage(String sender, String message) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("chat-log.txt", true));
        ) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);
            bufferedWriter.write(now+sender + ":" + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
