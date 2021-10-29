package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.EndpointHandlers;
import com.cs601.project3.server.models.HttpHandler;

import java.util.HashMap;

public class Router {
    HashMap<String, EndpointHandlers> routes = new HashMap<>();

    void createHandler(CRUD operation, String path, HttpHandler callback) {
        EndpointHandlers endpointHandlers = createPath(path);

        if (operation.equals(CRUD.GET)) {
            endpointHandlers.setGETCallback(callback::handle);
        } else if (operation.equals(CRUD.POST)) {
            endpointHandlers.setPOSTCallback(callback::handle);
        }
    }

    EndpointHandlers createPath(String path) {
        if (!routes.containsKey(path)) {
            EndpointHandlers endpointHandlers = new EndpointHandlers(path);
            routes.put(path, endpointHandlers);
        }
        return routes.get(path);
    }

}
