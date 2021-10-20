package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.Handler;

import java.util.HashMap;

public class Router {
    HashMap<String, Handler> routes = new HashMap<>();

    public void get(String path, Runnable callback) {
        createHandler(CRUD.GET, path, callback);
    }

    public void post(String path, Runnable callback, String query) {
        createHandler(CRUD.POST, path, callback);
    }

    private void createHandler(CRUD operation, String path, Runnable callback) {
        if (!routes.containsKey(path)) {
            Handler handler = new Handler(operation, path);
            routes.put(path, handler);
        }

        Handler handler = routes.get(path);
        if (operation.equals(CRUD.GET)) {
            handler.setGETCallback(callback);
        } else if (operation.equals(CRUD.POST)) {
            handler.setPOSTCallback(callback);
        }
    }

}
