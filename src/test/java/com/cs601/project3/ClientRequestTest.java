package com.cs601.project3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ClientRequestTest {
    @Test
    void clientRequest() throws IOException {
        final String expectedResponse = "{  \"userId\": 1,  \"id\": 1,  \"title\": \"delectus aut autem\",  \"completed\": false}";
        String jsonPlaceholderApi = "https://jsonplaceholder.typicode.com/todos/1";
        String response = ClientRequest.fetch(jsonPlaceholderApi);

        Assertions.assertEquals(expectedResponse, response);
    }
}