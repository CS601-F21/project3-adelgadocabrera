package com.cs601.project3.amazon.api.reviewsQA;


import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.api.reviewsQA.views.ReviewSearchResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;

import java.io.IOException;
import java.util.List;

import static com.cs601.project3.amazon.api.reviewsQA.Helpers.payloadHasQuery;
import static com.cs601.project3.amazon.api.reviewsQA.Helpers.sendBadRequest;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Handler to search for terms in Amazon Reviews.
 * Returns the searched item and additionally renders
 * a search bar to search for another term
 */
public class ReviewSearch implements HttpHandler {
    private static final String QUERY_FLAG = "query";

    public void handle(Request req, Response res) {
        String payload = req.getBody();
        String[] payloadParts = payload.trim().split("=");

        // send bad request if bad-formed request
        if (!payloadHasQuery(payload, QUERY_FLAG)) {
            sendBadRequest(res);
            return;
        }

        // Searched for an empty query - gets the hero in return
        // Ideally this should be handled in the FE with js
        if (payloadParts.length <= 1) {
            try {
                res.status(HttpStatus.OK).send(ReviewSearchResponse.hero());
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String term = payloadParts[1];
        List<String> reviews = AmazonSearch.reviews.search(term);
        String body = ReviewSearchResponse.heroWithResults(reviews);
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
