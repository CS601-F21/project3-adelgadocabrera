package com.cs601.project3.clientRequest;

public class ClientResponse {
    public final int statusCode;
    public final String body;
    public final String method;
    public final String protocol;

    public ClientResponse(int statusCode, String body, String method, String protocol) {
        this.statusCode = statusCode;
        this.body = body;
        this.method = method;
        this.protocol = protocol;
    }
}
