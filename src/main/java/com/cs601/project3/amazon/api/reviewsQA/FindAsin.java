package com.cs601.project3.amazon.api.reviewsQA;

import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.api.reviewsQA.views.FindAsinResponse;
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
 * Handler to search for ASIN in QAs and Reviews
 * Returns the results of the search ASIN and addtionally
 * renders a search bar to search for another ASIN item
 */
public class FindAsin implements HttpHandler {
    private static final String ASIN_FLAG = "asin";

    public void handle(Request req, Response res) {
        String payload = req.getBody();
        String[] payloadParts = payload.trim().split("=");

        // checks whether query is well-formed
        if (!payloadHasQuery(payload, ASIN_FLAG)) {
            sendBadRequest(res);
            return;
        }

        // Searched for an empty query - gets the hero in return
        // Ideally this should be handled in the FE with js
        if (payloadParts.length <= 1) {
            try {
                res.status(HttpStatus.OK).send(FindAsinResponse.hero());
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String asin = payloadParts[1];
        List<String> reviews = AmazonSearch.reviews.findAsin(asin);
        List<String> qas = AmazonSearch.qas.findAsin(asin);
        String body = FindAsinResponse.heroWithResults(reviews, qas);
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
