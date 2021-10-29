package com.cs601.project3.amazon.api.reviews;

import com.cs601.project3.HtmlValidator;
import com.cs601.project3.Mock;
import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.api.Reviews;
import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.HttpStatus;
import org.junit.jupiter.api.*;

import java.io.IOException;

class ReviewSearchTest {
    // inverted index
    static AmazonSearch amazonSearch;

    // app
    Server app;
    private static final int PORT = 5000;
    final String PATH = "/reviewsearch";
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
        app.post(PATH, Reviews.reviewSearch);
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
                res = ClientRequest.post(URL, "query=fantastically");
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
    @DisplayName("should have correct xhtml body response")
    void hasCorrectBody() throws IOException, InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "query=B001QXDSMW");
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
    @DisplayName("should have correct xhtml body the BAD REQUEST response")
    void hasCorrectBodyBadRequestResponse() throws IOException, InterruptedException {
        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res = null;
            try {
                res = ClientRequest.post(URL, "wrong=B001QXDSMW");
                Assertions.assertEquals(400, res.statusCode);
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
