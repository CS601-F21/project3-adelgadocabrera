package com.cs601.project3.server.controllers;

import com.cs601.project3.ClientRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ServerTest {
    private static final String OUTPUT_EXAMPLE = "output";
    private static String outputExample = null;
    private static final int PORT = 5000;
    Server server;

    @BeforeEach
    void setUp() {
        server = new Server(PORT);
    }

    @Test
    void use() {
    }

    @Test
    void queryServer() {
        try {
            final String path = "/reviewsearch";
           
            // create router && set routes
            Router router = new Router();
            router.get(path, new TestExample());

            // add router to server
            server.use(router);

            // run server in thread
            Thread serverThread = new Thread(() -> {
                try {
                    server.run();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            // make client query in another thread
            Thread clientThread = new Thread(() -> {
                try {
                    String url = "http://localhost:" + PORT + path;
                    System.out.println("url " + url);
                    ClientRequest.fetch(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // start server
            serverThread.start();

            // make client query
            clientThread.start();

            // stop threads
            clientThread.join();
            serverThread.stop();

            // Assert
            Assertions.assertEquals(OUTPUT_EXAMPLE, outputExample);

        } catch (IllegalAccessException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class TestExample implements Runnable {
        @Override
        public void run() {
            System.out.println("I'm here");
            outputExample = "output";
        }
    }

}