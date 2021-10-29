package com.cs601.project3.server.models;


public class EndpointHandlers {
    private final String path;
    private HttpHandler GETCallback;
    private HttpHandler POSTCallback;

    public EndpointHandlers(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setGETCallback(HttpHandler callback) {
        GETCallback = callback;
    }

    public void setPOSTCallback(HttpHandler callback) {
        POSTCallback = callback;
    }

    public HttpHandler getGETCallback() {
        return GETCallback;
    }

    public HttpHandler getPOSTCallback() {
        return POSTCallback;
    }
}
