package com.cs601.project3.server.models;

@FunctionalInterface
public interface HttpLambdaHandler {
    void handle(Request req, Response res);
}
