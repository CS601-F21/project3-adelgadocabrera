package com.cs601.project3.server.models;


public class Handler {
    private final String path;
    private HttpLambdaHandler GETCallback;
    private HttpLambdaHandler POSTCallback;

    public Handler(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setGETCallback(HttpLambdaHandler callback) {
        GETCallback = callback;
    }

    public void setPOSTCallback(HttpLambdaHandler callback) {
        POSTCallback = callback;
    }

    public HttpLambdaHandler getGETCallback() {
        return GETCallback;
    }

    public HttpLambdaHandler getPOSTCallback() {
        return POSTCallback;
    }
}
