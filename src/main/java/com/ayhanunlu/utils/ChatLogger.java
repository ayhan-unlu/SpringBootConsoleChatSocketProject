package com.ayhanunlu.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatLogger {

    public static void logMessage(LocalDateTime now, String sender, String message) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("chat-log.txt", true))
        ) {
            String timestamp = createStringTimestamp(now);
            bufferedWriter.newLine();
            bufferedWriter.write("[ " + timestamp + " ] " + sender + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logChatHeader(LocalDateTime now) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("chat-log.txt", true))) {
            String timestamp = createStringTimestamp(now);
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t\tNew Chat Session " + timestamp);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createStringTimestamp(LocalDateTime now) {
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
