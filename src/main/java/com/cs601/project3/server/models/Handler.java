package com.cs601.project3.server.models;


public class Handler {
    private CRUD operation;
    private String path;
    private Runnable GETCallback;
    private Runnable POSTCallback;

    public Handler(CRUD operation, String path) {
        this.operation = operation;
        this.path = path;
    }

    public CRUD getOperation() {
        return operation;
    }

    public String getPath() {
        return path;
    }

    public void setGETCallback(Runnable callback) {
        GETCallback = callback;
    }

    public void setPOSTCallback(Runnable callback) {
        POSTCallback = callback;
    }

    public Runnable getGETCallback() {
        return GETCallback;
    }

    public Runnable getPOSTCallback() {
        return POSTCallback;
    }
}
