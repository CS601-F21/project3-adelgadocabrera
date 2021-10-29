package com.cs601.project3.server.controllers;

import com.cs601.project3.ClientRequest;
import com.cs601.project3.ClientResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;
import org.junit.jupiter.api.*;

import java.io.IOException;

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
    Thread clientThread;

    // data to be Asserted
    private static String outputExample = null;
    private static String postBody = null;

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        System.setProperty("http.keepAlive", "false");

        // run server in thread
        serverThread = new Thread(app);
    }

    /**
     * Making sure no threads are running after completing test
     */
    @AfterEach
    void cleanUp() throws IOException, InterruptedException {
        outputExample = "";
        postBody = null;
        if (app != null) app.shutdown();
        if (serverThread != null) serverThread.stop();
        if (clientThread != null) clientThread.stop();
    }

    @Test
    @DisplayName("Should return METHOD NOT ALLOWED when performing a non GET/POST request")
    void notAllowed() throws InterruptedException {
        clientThread = new Thread(() -> {
            try {
                ClientResponse response = ClientRequest.put(URL, POST_BODY);
                Assertions.assertEquals(405, response.statusCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        execute();
    }

    @Test
    @DisplayName("Should return METHOD NOT FOUND when performing a request with no defined handler")
    void notFound() throws InterruptedException {
        clientThread = new Thread(() -> {
            try {
                ClientResponse response = ClientRequest.get("http://localhost:5000");
                Assertions.assertEquals(404, response.statusCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        execute();
    }

    @Test
    @DisplayName("Should return METHOD NOT FOUND when performing request to path that exists but bad operation")
    void nullHandler() {
        app.get(PATH, TestExample.get);
        clientThread = new Thread(() -> {
            try {
                ClientResponse response = ClientRequest.post(URL, "");
                Assertions.assertEquals(404, response.statusCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        execute();
    }

    @Test
    @DisplayName("Should return METHOD NOT FOUND when performing request to path that exists but bad operation")
    void badRequest() {
        app.get(PATH, TestExample.get);
        clientThread = new Thread(() -> {
            try {
                ClientResponse response = ClientRequest.post(URL, "");
                Assertions.assertEquals(404, response.statusCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        execute();
    }


    @Test
    @DisplayName("Should create GET handler and update outputExample value")
    void getServer() {
        // create router && set routes
        app.get(PATH, TestExample.get);

        // make client query in another thread
        clientThread = new Thread(() -> {
            try {
                ClientRequest.get(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // run thread && client request
        execute();

        // Assert get handler changed value of outputExample
        Assertions.assertEquals(GET_OUTPUT, outputExample);
    }

    @Test
    @DisplayName("Should create POST handler and send data to server")
    void postServer() {
        // create router && set routes
        app.post(PATH, TestExample.post);

        // make client query in another thread
        clientThread = new Thread(() -> {
            try {
                ClientRequest.post(URL, POST_BODY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // run thread && client request
        execute();

        // Assert post handler changed value of outputExample
        Assertions.assertEquals(POST_OUTPUT, outputExample);

        // Assert post handler got body of POST request
        Assertions.assertEquals(POST_BODY, postBody);
    }

    /**
     * Runs server and client threads and waits for them to finish.
     * Ensures client request to server is performed.
     */
    void execute() {
        try {
            // start server
            serverThread.start();

            // make client query
            clientThread.start();

            // stop threads
            clientThread.join();
            app.shutdown(); // shutdown server before stopping thread
            serverThread.join();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mock handler group
     */
    private static class TestExample {
        static Get get = new Get();
        static Post post = new Post();
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