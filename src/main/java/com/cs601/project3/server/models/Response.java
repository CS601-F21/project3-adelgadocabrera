package com.cs601.project3.server.models;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
    OutputStream outputStream;
    String statusCode;
    HashMap<String, String> headers = new HashMap<>();

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Response status(String code) {
        this.statusCode = code;
        return this;
    }

    public void send(String body) throws IOException {
        StringBuilder header = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String headerKey = entry.getKey();
            String headerValue = entry.getValue();
            header.append(headerKey).append(":").append(headerValue).append("\n");
        }

        String output = statusCode + header + "\n" + body;
        outputStream.write(output.getBytes());
        outputStream.close();
    }

    public Response setheaders(String headerName, String headerBody) {
        headers.put(headerName, headerBody);
        return this;
    }
}
