package com.cs601.project3.server.controllers;


import com.cs601.project3.server.models.CRUD;

class Handler {
    private CRUD operation;
    private String path;
    private Runnable callback;

    public Handler(CRUD operation, String path, Runnable callback) {
        this.operation = operation;
        this.path = path;
        this.callback = callback;
    }

    public CRUD getOperation() {
        return operation;
    }

    public String getPath() {
        return path;
    }

    public Runnable getCallback() {
        return callback;
    }
}
