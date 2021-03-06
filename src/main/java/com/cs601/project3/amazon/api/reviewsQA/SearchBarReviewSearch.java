package com.cs601.project3.amazon.api.reviewsQA;


import com.cs601.project3.amazon.api.reviewsQA.views.ReviewSearchResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;

import java.io.IOException;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Handler to search for terms in Amazon Reviews
 */
public class SearchBarReviewSearch implements HttpHandler {
    public void handle(Request req, Response res) {
        String body = ReviewSearchResponse.hero();
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
