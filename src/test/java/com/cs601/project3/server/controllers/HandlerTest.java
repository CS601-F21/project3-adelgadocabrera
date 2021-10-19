package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
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
    String callbackOutput = "";

    private class RunnableExample implements Runnable {

        @Override
        public void run() {
            callbackOutput = "OUTPUT";
        }
    }

    @BeforeEach
    void setUp() {
        correctHandler = new Handler(operation, "/chat", new RunnableExample());
        incorrectHandler = new Handler(null, null, null);
    }

    @Test
    @DisplayName("Should pass operation correctly")
    void getOperation() {
        Assertions.assertEquals(operation, correctHandler.getOperation());
    }

    @Test
    @DisplayName("Should pass path correctly")
    void getPath() {
        Assertions.assertEquals(path, correctHandler.getPath());
    }

    @Test
    @DisplayName("Should have null operation type")
    void getNullOperation() {
        Assertions.assertNull(incorrectHandler.getOperation());
    }

    @Test
    @DisplayName("Should have null path")
    void getNullPath() {
        Assertions.assertNull(incorrectHandler.getPath());
    }

    @Test
    @DisplayName("Should update callbackOutput variable correctly")
    void runnableWorks() {
        Thread t1 = new Thread(correctHandler.getCallback());
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
        Assertions.assertNull(incorrectHandler.getCallback());
    }
}