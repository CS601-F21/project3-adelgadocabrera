package com.cs601.project3.slack.api.bot;

import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.slack.api.bot.views.Form;

import java.io.IOException;

public class PublishMessage implements HttpHandler {
    public void handle(Request req, Response res) {
        String payload = req.getBody();
        String[] payloadParts = payload.trim().split("=");
        String MESSAGE_FLAG = "message";

        if (!Helpers.payloadHasQuery(payload, MESSAGE_FLAG)) {
            Helpers.sendBadRequest(res);
            return;
        }

        if (payloadParts.length <= 1) {
            try {
                res.status(HttpStatus.OK).send(Form.hero());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        String message = payloadParts[1];


    }
}
