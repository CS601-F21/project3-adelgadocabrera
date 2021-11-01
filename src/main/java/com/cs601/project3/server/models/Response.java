package com.cs601.project3.server.models;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Has the methods necessary to build a server response.
 */
public class Response {
    OutputStream outputStream;
    String statusCode;
    HashMap<String, String> headers = new HashMap<>();

    /**
     * Requires the socket outputStream
     *
     * @param outputStream
     */
    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Sends a status code
     *
     * @param code
     * @return
     */
    public Response status(String code) {
        this.statusCode = code;
        return this;
    }

    /**
     * Sends the entire response. That is the status code,
     * with the headers and lastly the body.
     *
     * @param body
     * @throws IOException
     */
    public void send(String body) throws IOException {
        StringBuilder header = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String headerKey = entry.getKey();
            String headerValue = entry.getValue();
            header.append(headerKey).append(":").append(headerValue).append("\n");
        }

        String output = statusCode + header + "\n" + body;
        outputStream.write(output.getBytes());
        outputStream.close();
    }

    /**
     * Headers setter
     *
     * @param headerName
     * @param headerBody
     * @return
     */
    public Response setheaders(String headerName, String headerBody) {
        headers.put(headerName, headerBody);
        return this;
    }
}
