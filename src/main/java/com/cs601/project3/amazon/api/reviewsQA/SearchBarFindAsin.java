package com.cs601.project3.amazon.api.reviewsQA;

import com.cs601.project3.amazon.api.reviewsQA.views.FindAsinResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;

import java.io.IOException;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Handler to search for ASIN items
 * Renders a search bar.
 */
public class SearchBarFindAsin implements HttpHandler {
    public void handle(Request req, Response res) {
        String body = FindAsinResponse.hero();
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
