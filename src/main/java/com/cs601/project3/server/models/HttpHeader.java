package com.cs601.project3.server.models;

public class HttpHeader {
    private static final String LINE_END = "\n\r\n";
    public static final String VERSION = "HTTP/1.1";

    public static final String OK = headerify("200 OK");
    public static final String BAD_REQUEST = headerify("400 Bad Request");
    public static final String NOT_FOUND = headerify("404 Not Found");
    public static final String NOT_ALLOWED = headerify("405 Method Not Allowed");

    public static final String CONTENT_LENGTH = "Content-Length:";
    public static final String CONNECTION_CLOSE = "Connection: close";

    private static String headerify(String result) {
        return VERSION + " " + result + LINE_END;
    }
}
