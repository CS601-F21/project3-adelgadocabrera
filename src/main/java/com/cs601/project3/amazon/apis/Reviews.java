package com.cs601.project3.amazon.apis;

import com.cs601.project3.amazon.AmazonSearch;
import com.cs601.project3.amazon.apis.reviews.ReviewResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.server.views.Html;

import java.io.IOException;
import java.util.List;

public class Reviews {
    public static HttpHandler getForm = new GetForm();
    public static HttpHandler search = new Search();

    private static class GetForm extends HttpHandler {
        public void handle(Request req, Response res) {
            String body = ReviewResponse.hero();
            try {
                res.status(HttpStatus.OK).send(body);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Search extends HttpHandler {
        public void handle(Request req, Response res) {
            String payload = req.getBody();
            String[] payloadParts = payload.trim().split(("="));
            String QUERY_FLAG = "query";

            if (payloadParts.length < 1 || !payloadParts[0].equals(QUERY_FLAG)) {
                String body = Html.build(HttpStatus.BAD_REQUEST);
                try {
                    res.status(HttpStatus.BAD_REQUEST).send(body);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (payloadParts.length <= 1) {
                try {
                    res.status(HttpStatus.OK).send(ReviewResponse.hero());
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String term = payloadParts[1];
            List<String> reviews = AmazonSearch.reviews.search(term);
            String body = ReviewResponse.heroWithResults(reviews);
            try {
                res.status(HttpStatus.OK).send(body);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
