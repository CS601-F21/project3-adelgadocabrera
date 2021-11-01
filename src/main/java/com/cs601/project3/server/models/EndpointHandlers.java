package com.cs601.project3.server.models;


/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Stores the callbacks for a given path.
 * Saves the path and the different callbacks for that path
 */
public class EndpointHandlers {
    private final String path;
    private HttpHandler GETCallback;
    private HttpHandler POSTCallback;

    /**
     * Expects the path of the handlers
     *
     * @param path
     */
    public EndpointHandlers(String path) {
        this.path = path;
    }

    /**
     * Path getter
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * GET callback setter
     *
     * @param callback
     */
    public void setGETCallback(HttpHandler callback) {
        GETCallback = callback;
    }

    /**
     * POST callback setter
     *
     * @param callback
     */
    public void setPOSTCallback(HttpHandler callback) {
        POSTCallback = callback;
    }

    /**
     * GET callback getter
     *
     * @return
     */
    public HttpHandler getGETCallback() {
        return GETCallback;
    }

    /**
     * POST callback getter
     *
     * @return
     */
    public HttpHandler getPOSTCallback() {
        return POSTCallback;
    }
}
