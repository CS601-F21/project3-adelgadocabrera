package com.cs601.project3.server.models;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Stores the information of an incoming client request
 */
public class Request {
    CRUD operation;
    String path;
    String protocol;
    String headers;
    String body;

    /**
     * Data to be saved
     *
     * @param operation GET/POST ...
     * @param path      request path
     * @param protocol  request protocol
     * @param headers   request headers
     * @param body      request body
     */
    public Request(CRUD operation, String path, String protocol, String headers, String body) {
        this.operation = operation;
        this.path = path;
        this.protocol = protocol;
        this.headers = headers;
        this.body = body;
    }

    /**
     * operation getter
     *
     * @return
     */
    public CRUD getOperation() {
        return operation;
    }

    /**
     * path getter
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * protocol getter
     *
     * @return
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * headers getter
     *
     * @return
     */
    public String getHeaders() {
        return headers;
    }

    /**
     * body getter
     *
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * toString override to make it prettier
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nRequest {" +
                "\noperation: " + operation +
                "\npath: " + path +
                "\nprotocol: " + protocol +
                "\n\nHeaders:\n" + headers.trim() +
                "\n}";
    }
}
