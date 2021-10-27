package com.cs601.project3.server.controllers;

import com.cs601.project3.ClientRequest;
import com.cs601.project3.server.models.*;
import com.cs601.project3.server.views.Html;
import org.junit.jupiter.api.*;

import java.io.IOException;

/**
 * @author Alberto Delgado
 * <p>
 * Testing that both HttpLambdaHandlers and HttpHandlers work.
 * Lambda handlers work with functional interface therefore logic can be
 * directly implemented in handler definition.
 * <p>
 * HttpHandlers are more elegant and are defined seperately
 */
class RouterTest {
    // app 
    Server app;
    private static final int PORT = 5000;
    final String PATH = "/path";
    final String URL = "http://localhost:" + PORT + PATH;

    // testing outputs
    static String HANDLER_OUTPUT = "OUTPUT";
    static String handlerOutput = null;
    static String RESPONSE = Html.build("RESPONSE");

    // threads
    Thread serverThread;
    Thread clientThread;

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        serverThread = new Thread(app);
    }

    @AfterEach
    void clean() throws IOException, InterruptedException {
        handlerOutput = null;
        if (app != null) app.shutdown();
        if (serverThread != null) serverThread.stop();
        if (clientThread != null) clientThread.stop();
    }

    @Test
    @DisplayName("should not throw error when adding lambdad handler")
    void addHttpHandler() {
        Assertions.assertDoesNotThrow(() -> app.get(PATH, (req, res) -> UserApi.updateUser.handle(req, res)));
    }

    @Test
    @DisplayName("should not throw error when adding handler")
    void addHttpClassHandler() {
        Assertions.assertDoesNotThrow(() -> app.get(PATH, UserApi.getUser));
    }

    @Test
    @DisplayName("Should create GET handler with http handler")
    void getHttpHandler() {
        app.get(PATH, UserApi.getUser); // add handler
        mockRequest(CRUD.GET); // make request

        Assertions.assertEquals(HANDLER_OUTPUT, handlerOutput);
    }

    @Test
    @DisplayName("Should create GET handler with http lambda handler")
    void getHttpLambdaHandler() {
        // add handler
        app.get(PATH, (req, res) -> {
            handlerOutput = HANDLER_OUTPUT;

            try {
                res.status(HttpStatus.OK).send(RESPONSE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mockRequest(CRUD.GET); // mock request

        Assertions.assertEquals(HANDLER_OUTPUT, handlerOutput);
    }

    @Test
    @DisplayName("Should create POST handler with http handler")
    void postHttpHandler() {
        app.post(PATH, UserApi.updateUser);
        mockRequest(CRUD.POST); // mock request

        Assertions.assertEquals(HANDLER_OUTPUT, handlerOutput);
    }

    @Test
    @DisplayName("Should create POST handler with lambda http handler")
    void postHttpLambdaHandler() {
        app.post(PATH, (req, res) -> {
            handlerOutput = HANDLER_OUTPUT;

            try {
                res.status(HttpStatus.OK).send(RESPONSE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mockRequest(CRUD.POST); // mock request

        Assertions.assertEquals(HANDLER_OUTPUT, handlerOutput);
    }

    /**
     * Performs client mock request
     *
     * @param operation
     */
    void mockRequest(CRUD operation) {
        clientThread = new Thread(() -> {
            try {
                if (operation.equals(CRUD.GET)) ClientRequest.get(URL);
                if (operation.equals(CRUD.POST)) ClientRequest.post(URL, "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // run server && client request
        execute();
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
     * Mock handlers
     */
    private static class GetUser extends HttpHandler {
        public void handle(Request req, Response res) {
            handlerOutput = HANDLER_OUTPUT;
            try {
                res.status(HttpStatus.OK).send(RESPONSE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mock handlers
     */
    private static class UpdateUser extends HttpHandler {
        public void handle(Request req, Response res) {
            handlerOutput = HANDLER_OUTPUT;
            try {
                res.status(HttpStatus.OK).send(RESPONSE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mock handler group
     */
    private static class UserApi {
        public static GetUser getUser = new GetUser();
        public static UpdateUser updateUser = new UpdateUser();
    }
}