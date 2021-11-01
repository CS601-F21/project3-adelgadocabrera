package com.cs601.project3.server.models;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Main interface for all handlers.
 * Additionally, it is implemented as FunctionalInterface,
 * so you don't have to pass an object, you can define
 * a handler using lambda functions (req, res) -> { }
 */
@FunctionalInterface
public interface HttpHandler {
    void handle(Request req, Response res);
}
