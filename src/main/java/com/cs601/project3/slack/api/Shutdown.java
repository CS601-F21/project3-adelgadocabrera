package com.cs601.project3.slack.api;

import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;
import com.cs601.project3.slack.Slack;

import java.io.IOException;

public class Shutdown implements HttpHandler {
    public void handle(Request req, Response res) {
        String body = Html.build("Server shutdown. Thank you commander.");
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Slack.shutdown();
    }
}
