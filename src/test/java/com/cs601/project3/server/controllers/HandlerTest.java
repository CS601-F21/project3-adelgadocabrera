package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.Handler;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerTest {
    final CRUD operation = CRUD.GET;
    final String path = "/chat";
    Handler correctHandler;
    Handler incorrectHandler;

    final String CALLBACK_OUTPUT = "OUTPUT";
    private static String callbackOutput = "";

    @BeforeEach
    void setUp() {
        correctHandler = new Handler("/chat");
        incorrectHandler = new Handler(null);

        correctHandler.setGETCallback(ExampleHandler::handle);
    }

    @Test
    @DisplayName("Should pass path correctly")
    void getPath() {
        Assertions.assertEquals(path, correctHandler.getPath());
    }

    @Test
    @DisplayName("Should have null path")
    void getNullPath() {
        Assertions.assertNull(incorrectHandler.getPath());
    }

    @Test
    @DisplayName("Should update callbackOutput variable correctly")
    void runnableWorks() {
        Thread t1 = new Thread(() -> correctHandler.getGETCallback().handle(null, null));
        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(CALLBACK_OUTPUT, callbackOutput);
    }

    @Test
    @DisplayName("Should have null Runnable callback")
    void nullRunnable() {
//        Assertions.assertNull(incorrectHandler.getCallback());
    }

    private static class ExampleHandler {
        public static void handle(Request request, Response res) {
            callbackOutput = "OUTPUT";
        }
    }
}