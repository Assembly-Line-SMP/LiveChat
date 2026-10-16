package dev.kubabin.livechat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscordWebhook {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    public static String webhook = "";
    public static boolean enabled = false;

    private static void send(String message) throws Exception {
        String json = "{\"content\":\"" +
                message.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n") + "\"}";

        var request = HttpRequest.newBuilder(URI.create(webhook))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
    }

    public static void asyncSend(String message) {
        if (!enabled || webhook.isEmpty()) {
            return;
        }
        new Thread(() -> {
            try {
                send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
