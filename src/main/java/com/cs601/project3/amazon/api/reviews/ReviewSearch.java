package com.cs601.project3.amazon.api.reviews;


import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.api.reviews.views.ReviewSearchResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;

import java.io.IOException;
import java.util.List;

import static com.cs601.project3.amazon.api.reviews.Helpers.payloadHasQuery;
import static com.cs601.project3.amazon.api.reviews.Helpers.sendBadRequest;

public class ReviewSearch implements HttpHandler {
    public void handle(Request req, Response res) {
        String payload = req.getBody();
        String[] payloadParts = payload.trim().split("=");
        String QUERY_FLAG = "query";

        if (!payloadHasQuery(payload, QUERY_FLAG)) {
            sendBadRequest(res);
            return;
        }

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
