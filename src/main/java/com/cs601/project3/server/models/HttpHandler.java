package com.cs601.project3.server.models;

public abstract class HttpHandler {
    public abstract void handle(Request req, Response res);
}
