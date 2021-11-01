package com.cs601.project3.server.models;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Contains the HTTP status codes and protocol.
 */
public class HttpStatus {
    // protocol version
    private static final String LINE_END = "\n\r\n";
    public static final String VERSION = "HTTP/1.1";

    // status codes
    public static final String OK = headerify("200 OK");
    public static final String BAD_REQUEST = headerify("400 Bad Request");
    public static final String NOT_FOUND = headerify("404 Not Found");
    public static final String NOT_ALLOWED = headerify("405 Method Not Allowed");

    /**
     * Helper method to put together protocols and status codes
     *
     * @param result
     * @return
     */
    private static String headerify(String result) {
        return VERSION + " " + result + LINE_END;
    }
}
