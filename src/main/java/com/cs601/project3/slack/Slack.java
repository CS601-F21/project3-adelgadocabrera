package com.cs601.project3.slack;

import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.slack.api.Bot;
import com.cs601.project3.slack.api.Shutdown;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Slack BOT
 * Creates a website to generate messages and upon submission
 * are sent to a Slack channel anonymously
 */
public class Slack {
    private static Server app;
    private static final int PORT = 9090;

    /**
     * Starts the server
     *
     * @param args
     */
    public static void main(String[] args) {
        initServer();

        System.out.println("App listening in port " + PORT);
        app.run();
        System.out.println("Server shutdown.");
    }

    /**
     * Initiates server && defines endpoints
     */
    private static void initServer() {
        app = new Server(PORT);

        // slackbot endpoints && shutdown
        app.get("/slackbot", Bot.messageForm);
        app.post("/slackbot", Bot.publishMessage);
        app.get("/shutdown", new Shutdown());
    }

    /**
     * Shuts down the server
     */
    public static void shutdown() {
        app.shutdown();
    }
}