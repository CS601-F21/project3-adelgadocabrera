package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestParserTest {
    private static final String HTTP_HEADLINE_CORRECT = "GET / HTTP/1.1";
    private static final String HTTP_HEADLINE_INCORRECT = "GET / HTTPS/3.0";

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Should have correct http protocol")
    void correctProtocol() throws IllegalAccessException {
        Request request = RequestParser.get(HTTP_HEADLINE_CORRECT, "", "");
        Assertions.assertEquals(HttpStatus.VERSION, request.getProtocol());
    }

    @Test
    @DisplayName("should throw because of incorrect protocol")
    void incorrectProtocol() {
        Assertions.assertThrows(IllegalAccessException.class, () ->
                RequestParser.get(HTTP_HEADLINE_INCORRECT, "", ""));
    }

    @Test
    @DisplayName("should return BAD REQUEST because of incorrect protocol")
    void incorrectProtocolResponse() {
        try {
            RequestParser.get(HTTP_HEADLINE_INCORRECT, "", "");
        } catch (IllegalAccessException e) {
            Assertions.assertEquals(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    @DisplayName("should throw because null arguments")
    void nullArguments() {
        Assertions.assertThrows(NullPointerException.class, () ->
                RequestParser.get(null, null, null));
    }

    @Test
    @DisplayName("headline should have 3 parts")
    void headline() {
        Assertions.assertThrows(IllegalAccessException.class,
                () -> RequestParser.get("GET /", "", ""));
    }

    @Test
    @DisplayName("should throw BAD REQUEST because bad formation of request headline")
    void headlineMessage() {
        try {
            RequestParser.get("GET /", "", "");
        } catch (IllegalAccessException e) {
            Assertions.assertEquals(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    @DisplayName("should throw NOT ALLOWED becuase unsupportd CRUD operation")
    void crudOperationError() {
        try {
            RequestParser.get("PUT / HTTP/1.1", "", "");
        } catch (IllegalAccessException e) {
            Assertions.assertEquals(e.getMessage(), HttpStatus.NOT_ALLOWED);
        }
    }
}