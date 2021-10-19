package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;

import java.util.HashMap;

public class Router {
    HashMap<String, Handler> routes = new HashMap<>();

    public void get(String path, Runnable callback) {
        try {
            createHandler(CRUD.GET, path, callback);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    ;

    public void post(String path, Runnable callback, String query) {
        try {
            createHandler(CRUD.POST, path, callback);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    ;

    public void createHandler(CRUD operation, String path, Runnable callback) throws IllegalAccessException {
        if (routes.containsKey(path)) throw new IllegalAccessException("Route " + path + " already exists");
        Handler handler = new Handler(operation, path, callback);
        routes.put(path, handler);
    }
}
