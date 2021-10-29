package com.cs601.project3.amazon.api.reviews;

import com.cs601.project3.ClientRequest;
import com.cs601.project3.ClientResponse;
import com.cs601.project3.HtmlValidator;
import com.cs601.project3.Mock;
import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.api.Reviews;
import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.HttpStatus;
import org.junit.jupiter.api.*;

import java.io.IOException;

class FindAsinTest {
    // inverted index
    static AmazonSearch amazonSearch;

    // app 
    Server app;
    private static final int PORT = 5000;
    final String PATH = "/find";
    final String URL = "http://localhost:" + PORT + PATH;

    @BeforeAll
    static void setUpInvertedIndex() {
        String[] args = new String[4];
        args[0] = "-reviews";
        args[1] = "mock_reviews.txt";
        args[2] = "-qa";
        args[3] = "mock_qa.txt";
        amazonSearch = new AmazonSearch();
        AmazonSearch.initInvertedIndex(args);
    }

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        app.post(PATH, Reviews.findAsin);
    }

    @AfterEach
    void clean() throws IOException, InterruptedException {
        app.shutdown();
    }

    @Test
    @DisplayName("should not throw when calling endpoint")
    void postRequestDoesntThrow() {
        Assertions.assertDoesNotThrow(() -> Mock.request(app, CRUD.POST, URL));
    }

    @Test
    @DisplayName("should have correct headers")
    void hasCorrectHeaders() throws IOException, InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "asin=A24SYO10RBABLT");
                Assertions.assertEquals(200, res.statusCode);
                Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
                Assertions.assertEquals("POST", res.method);
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
    void hasCorrectBody() throws IOException, InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "asin=B001QXDSMW");
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
    @DisplayName("should return BAD REQUEST because incorrect post body")
    void badRequest() throws IOException, InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "wrong=A24SYO10RBABLT");
                Assertions.assertEquals(400, res.statusCode);
                Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
                Assertions.assertEquals("POST", res.method);
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
    @DisplayName("should have xhtml bad request response with proper xhtml format")
    void badRequestBodyFormat() throws IOException, InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "wrong=A24SYO10RBABLT");
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