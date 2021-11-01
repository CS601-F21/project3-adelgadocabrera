package com.cs601.project3.amazon.api.reviewsQA;

import com.cs601.project3.HtmlValidator;
import com.cs601.project3.Mock;
import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.api.ReviewsQA;
import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.HttpStatus;
import org.junit.jupiter.api.*;

import java.io.IOException;

class SearchBarReviewSearchTest {
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
        AmazonSearch.initInvertedIndex(args);
    }

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        app.get(PATH, ReviewsQA.searchBarReviewSearch);
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
                res = ClientRequest.get(URL);
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
