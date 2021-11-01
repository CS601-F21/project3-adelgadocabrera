package com.cs601.project3.slack.api.bot;

import com.cs601.project3.HtmlValidator;
import com.cs601.project3.Mock;
import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.slack.api.Bot;
import org.junit.jupiter.api.*;

import java.io.IOException;

class PublishMessageTest {
    // app
    Server app;
    private static final int PORT = 8000;
    final String PATH = "/slackbot";
    final String URL = "http://localhost:" + PORT + PATH;

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        app.get(PATH, Bot.messageForm);
        app.post(PATH, Bot.publishMessage);
    }

    @AfterEach
    void clean() {
        app.shutdown();
    }

    @Test
    @DisplayName("should not throw when calling endpoint")
    void postRequestDoesntThrow() {
        Assertions.assertDoesNotThrow(() -> Mock.request(app, CRUD.GET, URL));
    }

    @Test
    @DisplayName("should have correct headers")
    void hasCorrectHeaders() throws InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.get(URL);
                Assertions.assertEquals(200, res.statusCode);
                Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
                Assertions.assertEquals("GET", res.method);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // start threads
        serverThread.start();
        clientThread.start();

        // shut everything down
        clientThread.join();
        app.shutdown();
        serverThread.join();
    }

    @Test
    @DisplayName("should have correct xhtml body")
    void hasCorrectBody() throws InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "message=test+message");
                Assertions.assertEquals(200, res.statusCode);
                Assertions.assertTrue(HtmlValidator.isValid(res.body));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // start threads
        serverThread.start();
        clientThread.start();

        // shut everything down
        clientThread.join();
        app.shutdown();
        serverThread.join();
    }

    @Test
    @DisplayName("should return bad request because bad form message submission")
    void badRequest() throws InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "WRONG=test+message");
                Assertions.assertEquals(400, res.statusCode);
                Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
                Assertions.assertEquals("POST", res.method);
                Assertions.assertTrue(HtmlValidator.isValid(res.body));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // start threads
        serverThread.start();
        clientThread.start();

        // shut everything down
        clientThread.join();
        app.shutdown();
        serverThread.join();
    }


    @Test
    @DisplayName("should have correct xhtml body the 404 NOT FOUND response")
    void hasCorrectBodyBadRequestResponse() throws InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.get(URL + "/hello");
                Assertions.assertEquals(404, res.statusCode);
                Assertions.assertTrue(HtmlValidator.isValid(res.body));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // start threads
        serverThread.start();
        clientThread.start();

        // shut everything down
        clientThread.join();
        app.shutdown();
        serverThread.join();
    }

}
