package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Parses a client request and returns a Request object
 */
public class RequestParser {
    private static final int REQUEST_LINE_LENGTH = 3; // CRUD operation / path / protocol

    /**
     * To parse a client request it requires the 3 parts:
     *
     * @param requestLine which should contain method, path and protocol
     * @param headers     possible headers
     * @param body        possible body
     * @return
     * @throws IllegalAccessException
     */
    public static Request get(String requestLine, String headers, String body) throws IllegalAccessException {
        //parse request line
        String[] requestLineParts = requestLine.split("\\s+");

        if (requestLineParts.length != REQUEST_LINE_LENGTH) {
            throw new IllegalAccessException(HttpStatus.BAD_REQUEST);
        }

        // request headline consists of 3 parts: method, path and protocol
        CRUD operation = CRUD.valueOf(requestLineParts[0]);
        String path = requestLineParts[1];
        String protocol = requestLineParts[2];

        boolean operationExists = isInEnum(requestLineParts[0], CRUD.class);
        if (!operationExists) {
            throw new IllegalAccessException(HttpStatus.NOT_ALLOWED);
        }

        if (!protocol.equals(HttpStatus.VERSION)) {
            throw new IllegalAccessException(HttpStatus.BAD_REQUEST);
        }

        return new Request(operation, path, protocol, headers, body);
    }

    /**
     * Checks if string belongs to enum.
     *
     * @param value
     * @param enumClass
     * @param <E>
     * @return
     */
    private static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
