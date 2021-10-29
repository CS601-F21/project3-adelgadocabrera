package com.cs601.project3.amazon.api.reviews;

import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.api.reviews.views.FindResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;

import java.io.IOException;
import java.util.List;

import static com.cs601.project3.amazon.api.reviews.Helpers.payloadHasQuery;
import static com.cs601.project3.amazon.api.reviews.Helpers.sendBadRequest;

public class Find implements HttpHandler {
    public void handle(Request req, Response res) {
        String payload = req.getBody();
        String[] payloadParts = payload.trim().split("=");
        String ASIN_FLAG = "asin";

        if (!payloadHasQuery(payload, ASIN_FLAG)) {
            sendBadRequest(res);
            return;
        }

        if (payloadParts.length <= 1) {
            try {
                res.status(HttpStatus.OK).send(FindResponse.hero());
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String asin = payloadParts[1];
        List<String> reviews = AmazonSearch.reviews.findAsin(asin);
        List<String> qas = AmazonSearch.qas.findAsin(asin);
        String body = FindResponse.heroWithResults(reviews, qas);
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
