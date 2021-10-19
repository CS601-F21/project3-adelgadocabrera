package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandlerTest {
    final CRUD operation = CRUD.GET;
    final String path = "/chat";
    Handler correctHandler;
    Handler incorrectHandler;

    void helloWorld(){
        System.out.println("hello world!");
    }

    @BeforeEach
    void setUp() {
        correctHandler = new Handler(operation, "/", null);
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
}