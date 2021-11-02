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
    Thread serverThread;

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        app.get(PATH, Bot.messageForm);
        app.post(PATH, Bot.publishMessage);

        serverThread = new Thread(app);
        serverThread.start();
    }

    @AfterEach
    void clean() throws InterruptedException {
        app.shutdown();
        serverThread.join();
    }

    @Test
    @DisplayName("should not throw when calling endpoint")
    void postRequestDoesntThrow() {
        Assertions.assertDoesNotThrow(() -> Mock.request(app, CRUD.GET, URL));
    }

    @Test
    @DisplayName("should have correct headers")
    void hasCorrectHeaders() {
        ClientResponse res = null;
        try {
            res = ClientRequest.get(URL);
            Assertions.assertEquals(200, res.statusCode);
            Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
            Assertions.assertEquals("GET", res.method);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("should have correct xhtml body")
    void hasCorrectBody() {
        ClientResponse res = null;
        try {
            res = ClientRequest.post(URL, "message=test+message");
            Assertions.assertEquals(200, res.statusCode);
            Assertions.assertTrue(HtmlValidator.isValid(res.body));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("should return bad request because bad form message submission")
    void badRequest() {
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
    }


    @Test
    @DisplayName("should have correct xhtml body the 404 NOT FOUND response")
    void hasCorrectBodyBadRequestResponse() {
        ClientResponse res = null;
        try {
            res = ClientRequest.get(URL + "/hello");
            Assertions.assertEquals(404, res.statusCode);
            Assertions.assertTrue(HtmlValidator.isValid(res.body));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
