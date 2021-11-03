package com.cs601.project3.server.controllers;

import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;

class ServerTest {
    private static final String GET_OUTPUT = "get_output";
    private static final String POST_OUTPUT = "post_output";
    private static final String LOCAL_HOST = "http://localhost:";
    private static final String POST_BODY = "query=term\nfind=anotherTerm\ndata=thirdTerm";
    final static String PATH = "/reviewsearch";
    private static final int PORT = 5000;
    final static String URL = LOCAL_HOST + PORT + PATH;

    // app && client threads
    Server app;
    Thread serverThread;

    // data to be Asserted
    private static String outputExample = null;
    private static String postBody = null;
    private static volatile boolean concurrent = true;

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        serverThread = new Thread(app);
        serverThread.start();
    }

    /**
     * Making sure no threads are running after completing test
     */
    @AfterEach
    void cleanUp() throws InterruptedException {
        outputExample = "";
        postBody = null;
        app.shutdown();
        serverThread.join();
    }

    @Test
    @DisplayName("Should return METHOD NOT ALLOWED when performing a non GET/POST request")
    void notAllowed() {
        try {
            ClientResponse response = ClientRequest.put(URL, POST_BODY);
            Assertions.assertEquals(405, response.statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should return NOT FOUND when performing a request with no defined handler")
    void notFound() {
        try {
            ClientResponse response = ClientRequest.get("http://localhost:5000");
            Assertions.assertEquals(404, response.statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should return NOT FOUND when performing request to path that exists but not the method")
    void badRequest() {
        app.get(PATH, TestExample.get);
        try {
            ClientResponse response = ClientRequest.post(URL, "");
            Assertions.assertEquals(404, response.statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Should create GET handler and update outputExample value")
    void getServer() {
        // create router && set routes
        app.get(PATH, TestExample.get);

        try {
            ClientRequest.get(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert get handler changed value of outputExample
        Assertions.assertEquals(GET_OUTPUT, outputExample);
    }

    @Test
    @DisplayName("Should create POST handler and send data to server")
    void postServer() {
        // create router && set routes
        app.post(PATH, TestExample.post);

        try {
            ClientRequest.post(URL, POST_BODY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert post handler changed value of outputExample
        Assertions.assertEquals(POST_OUTPUT, outputExample);

        // Assert post handler got body of POST request
        Assertions.assertEquals(POST_BODY, postBody);
    }

    /**
     * Mock handler group
     */
    private static class TestExample {
        static Get get = new Get();
        static Post post = new Post();
    }

    @Test
    @DisplayName("should handle multiple concurrent requests fine")
    void concurrency() throws InterruptedException {
        app.get(PATH, TestExample.get);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Thread t = new Thread(() -> {
                try {
                    ClientResponse res = ClientRequest.get(URL);
                    if (res.statusCode != 200) {
                        concurrent = false;
                    }
                } catch (IOException e) {
                    concurrent = false;
                    e.printStackTrace();
                }
            });

            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();
        Assertions.assertTrue(concurrent);
    }

    /**
     * Mock handler
     */
    public static class Post implements HttpHandler {
        public void handle(Request req, Response res) {
            outputExample = POST_OUTPUT;
            postBody = POST_BODY;

            try {
                String body = "Your POST has been received!";
                res.status(HttpStatus.OK).send(Html.build(body));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mock handler
     */
    public static class Get implements HttpHandler {
        public void handle(Request req, Response res) {
            outputExample = GET_OUTPUT;
            try {
                String body = "Your GET has been received!";
                res.status(HttpStatus.OK).send(Html.build(body));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}