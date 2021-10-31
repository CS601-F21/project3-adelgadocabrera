package com.cs601.project3.amazon.api;

import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;

import java.io.IOException;

public class Shutdown implements HttpHandler {
    public void handle(Request req, Response res) {
        String body = Html.build("Server shutdown. Thank you commander.");
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Closing server from a different thread so server
        // logic can continue and closes current request socket.
        AmazonSearch.shutdown();
    }
}
