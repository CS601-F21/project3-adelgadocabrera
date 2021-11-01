package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.EndpointHandlers;
import com.cs601.project3.server.models.HttpHandler;

import java.util.HashMap;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Handles the creation of routes.
 * In a specified path it will save EnpointHandlers which is
 * and object containing all the handlers GET/POST/PUT/DELETE callbacks for
 * that path
 */
public class Router {
    // Contains all the paths and endpoint handlers
    HashMap<String, EndpointHandlers> routes = new HashMap<>();

    /**
     * Maps the given handler to the given path
     *
     * @param operation
     * @param path
     * @param callback
     */
    void createHandler(CRUD operation, String path, HttpHandler callback) {
        EndpointHandlers endpointHandlers = createPath(path);

        if (operation.equals(CRUD.GET)) {
            endpointHandlers.setGETCallback(callback::handle);
        } else if (operation.equals(CRUD.POST)) {
            endpointHandlers.setPOSTCallback(callback::handle);
        }
    }

    /**
     * Returns the handler for a given path, if that path does not exist
     * it creates it in the routes map.
     *
     * @param path
     * @return
     */
    EndpointHandlers createPath(String path) {
        if (!routes.containsKey(path)) {
            EndpointHandlers endpointHandlers = new EndpointHandlers(path);
            routes.put(path, endpointHandlers);
        }
        return routes.get(path);
    }

}
