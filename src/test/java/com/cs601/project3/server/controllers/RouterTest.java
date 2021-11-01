package com.cs601.project3.server.controllers;

import com.cs601.project3.Mock;
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
    Server app;
    private static final int PORT = 5000;
    final String PATH = "/path";
    final String URL = "http://localhost:" + PORT + PATH;

    // testing outputs
    static String HANDLER_OUTPUT = "OUTPUT";
    static String handlerOutput = null;
    static String RESPONSE = Html.build("RESPONSE");

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
    }

    @AfterEach
    void cleanUp() {
        handlerOutput = null;
        app.shutdown();
    }

    @Test
    @DisplayName("should not throw error when adding lambda handler")
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
        Mock.request(app, CRUD.GET, URL); // make request

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

        Mock.request(app, CRUD.GET, URL); // mock request

        Assertions.assertEquals(HANDLER_OUTPUT, handlerOutput);
    }

    @Test
    @DisplayName("Should create POST handler with http handler")
    void postHttpHandler() {
        app.post(PATH, UserApi.updateUser);
        Mock.request(app, CRUD.POST, URL); // mock request

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

        Mock.request(app, CRUD.POST, URL); // mock request

        Assertions.assertEquals(HANDLER_OUTPUT, handlerOutput);
    }


    /**
     * Mock handlers
     */
    private static class GetUser implements HttpHandler {
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
    private static class UpdateUser implements HttpHandler {
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