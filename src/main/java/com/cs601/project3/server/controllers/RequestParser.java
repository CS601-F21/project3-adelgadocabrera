package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.HttpHeader;
import com.cs601.project3.server.models.Request;

public class RequestParser {
    private static final int REQUEST_LINE_LENGTH = 3; // CRUD operation / path / protocol

    public static Request get(String request, String headers, String body) throws IllegalAccessException {
        //parse request line
        String[] requestLineParts = request.split("\\s+");

        if (requestLineParts.length != REQUEST_LINE_LENGTH) {
            throw new IllegalAccessException(HttpHeader.BAD_REQUEST);
        }

        // request headline consists of 3 parts: operation, path and protocol
        CRUD operation = CRUD.valueOf(requestLineParts[0]);
        String path = requestLineParts[1];
        String protocol = requestLineParts[2];

        boolean operationExists = isInEnum(requestLineParts[0], CRUD.class);
        if (!operationExists) {
            throw new IllegalAccessException(HttpHeader.NOT_ALLOWED);
        }

        if (!protocol.equals(HttpHeader.VERSION)) {
            throw new IllegalAccessException(HttpHeader.BAD_REQUEST);
        }

        return new Request(operation, path, protocol, headers, body);
    }

    private static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
