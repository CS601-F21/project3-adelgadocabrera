package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.Handler;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpLambdaHandler;

import java.util.HashMap;

public class Router {
    HashMap<String, Handler> routes = new HashMap<>();

    void createHandler(CRUD operation, String path, HttpHandler callback) {
        Handler handler = createPath(path);

        if (operation.equals(CRUD.GET)) {
            handler.setGETCallback(callback::handle);
        } else if (operation.equals(CRUD.POST)) {
            handler.setPOSTCallback(callback::handle);
        }
    }

    void createHandler(CRUD operation, String path, HttpLambdaHandler callback) {
        Handler handler = createPath(path);

        if (operation.equals(CRUD.GET)) {
            handler.setGETCallback(callback);
        } else if (operation.equals(CRUD.POST)) {
            handler.setPOSTCallback(callback);
        }
    }

    Handler createPath(String path) {
        if (!routes.containsKey(path)) {
            Handler handler = new Handler(path);
            routes.put(path, handler);
        }
        return routes.get(path);
    }

}
