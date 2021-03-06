package com.cs601.project3.slack.api.bot;

import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;

import java.io.IOException;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Helper methods for handlers
 */
public class Helpers {

    /**
     * Checks whether request body is well-formed
     *
     * @param payload
     * @param queryFlag
     * @return
     */
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

    /**
     * Gets the body of the message. It handles if message
     * has more than one occurrence of the symbol =
     *
     * @param payload
     * @return
     */
    public static String getMessage(String payload) {
        StringBuilder message = new StringBuilder();
        boolean foundEqual = false;
        for (int i = 0; i < payload.length(); i++) {
            if (foundEqual)
                message.append(payload.charAt(i));
           
            if (payload.charAt(i) == '=')
                foundEqual = true;
        }
        return message.toString();
    }

    /**
     * Sends a HTML BAD REQUEST response
     *
     * @param res
     */
    public static void sendBadRequest(Response res) {
        String body = Html.build(HttpStatus.BAD_REQUEST);
        try {
            res.status(HttpStatus.BAD_REQUEST).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
