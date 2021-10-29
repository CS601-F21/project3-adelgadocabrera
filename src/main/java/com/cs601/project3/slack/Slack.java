package com.cs601.project3.slack;

import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.slack.api.Bot;

public class Slack {
    private static Server app;
    private static final int PORT = 9090;

    public static void main(String[] args) {
        initServer();

        System.out.println("App listening in port " + PORT);
        app.run();
    }

    private static void initServer() {
        app = new Server(PORT);
        app.get("/slackbot", Bot.messageForm);
        app.post("/slackbot", Bot.publishMessage);
    }
}