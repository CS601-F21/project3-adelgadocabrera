package com.cs601.project3.server.models;

public class Response {
    String header;
    String body;

    public Response(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public byte[] getBytes() {
        return (header + "\n" + body).getBytes();
    }
}
