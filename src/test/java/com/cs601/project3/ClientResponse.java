package com.cs601.project3;

public class ClientResponse {
    public final int statusCode;
    public final String body;

    public ClientResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
}
