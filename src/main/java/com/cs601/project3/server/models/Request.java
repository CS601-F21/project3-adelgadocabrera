package com.cs601.project3.server.models;

public class Request {
    CRUD operation;
    String path;
    String protocol;
    String headers;
    String body;

    public Request(CRUD operation, String path, String protocol, String headers, String body) {
        this.operation = operation;
        this.path = path;
        this.protocol = protocol;
        this.headers = headers;
        this.body = body;
    }

    public CRUD getOperation() {
        return operation;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "\nRequest {" +
                "\noperation: " + operation +
                "\npath: " + path +
                "\nprotocol: " + protocol +
                "\n\nHeaders:\n" + headers.trim() +
                "\n}";
    }
}
