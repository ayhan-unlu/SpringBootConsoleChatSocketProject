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

            bufferedWriter.newLine();
            bufferedWriter.write("[ " + createTimestamp() + " ] " + sender + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logChatHeader() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("chat-log.txt", true))) {
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t\tNew Chat Session " + createTimestamp());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createTimestamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
