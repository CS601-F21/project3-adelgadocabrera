package com.cs601.project3.clientRequest;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Client's response data
 */
public class ClientResponse {
    public final int statusCode;
    public final String body;
    public final String method;
    public final String protocol;

    /**
     * Stores status code, body, CRUD method and HTTP or other protocol
     */
    public ClientResponse(int statusCode, String body, String method, String protocol) {
        this.statusCode = statusCode;
        this.body = body;
        this.method = method;
        this.protocol = protocol;
    }
}
