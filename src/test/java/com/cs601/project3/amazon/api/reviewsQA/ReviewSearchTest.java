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
import java.util.concurrent.TimeUnit;

class ReviewSearchTest {
    // app
    Server app;
    private static final int PORT = 5000;
    final String PATH = "/reviewsearch";
    final String URL = "http://localhost:" + PORT + PATH;
    Thread serverThread;

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
        app.post(PATH, ReviewsQA.reviewSearch);

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
        Assertions.assertDoesNotThrow(() -> Mock.request(app, CRUD.POST, URL));
    }

    @Test
    @DisplayName("should have correct headers")
    void hasCorrectHeaders() {
        ClientResponse res = null;
        try {
            res = ClientRequest.post(URL, "query=fantastically");
            Assertions.assertEquals(200, res.statusCode);
            Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
            Assertions.assertEquals("POST", res.method);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("should have correct xhtml body response")
    void hasCorrectBody() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500); // added this because it required some time to start server

        ClientResponse res = null;
        try {
            res = ClientRequest.post(URL, "query=B001QXDSMW");
            Assertions.assertEquals(200, res.statusCode);
            Assertions.assertTrue(HtmlValidator.isValid(res.body));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("should have correct xhtml body the BAD REQUEST response")
    void hasCorrectBodyBadRequestResponse() {
        ClientResponse res = null;
        try {
            res = ClientRequest.post(URL, "wrong=B001QXDSMW");
            Assertions.assertEquals(400, res.statusCode);
            Assertions.assertTrue(HtmlValidator.isValid(res.body));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
