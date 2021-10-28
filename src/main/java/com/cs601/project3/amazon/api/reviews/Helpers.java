package com.cs601.project3.amazon.api.reviews;

import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;

import java.io.IOException;

public class Helpers {

    public static boolean payloadHasQuery(String payload, String queryFlag) {
        if (payload == null) return false;
        if (payload.equals("")) return false;
        if (!payload.contains("=")) return false;
        String[] payloadParts = payload.trim().split("=");
        if (payloadParts.length < 1 || !payloadParts[0].equals(queryFlag)) {
            return false;
        }
        return true;
    }

    public static void sendBadRequest(Response res) {
        String body = Html.build(HttpStatus.BAD_REQUEST);
        try {
            res.status(HttpStatus.BAD_REQUEST).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
