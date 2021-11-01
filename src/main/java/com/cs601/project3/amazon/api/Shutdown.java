package com.cs601.project3.amazon.api;

import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;

import java.io.IOException;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Handler to shutdown the server
 */
public class Shutdown implements HttpHandler {
    public void handle(Request req, Response res) {
        String body = Html.build("Server shutdown. Thank you commander.");
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AmazonSearch.shutdown();
    }
}
