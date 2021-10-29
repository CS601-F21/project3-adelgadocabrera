package com.cs601.project3.slack.api.bot;

import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.slack.api.bot.views.Form;

import java.io.IOException;

public class MessageForm implements HttpHandler {
    public void handle(Request req, Response res) {
        String body = Form.hero();
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
