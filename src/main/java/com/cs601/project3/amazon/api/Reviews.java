package com.cs601.project3.amazon.api;

import com.cs601.project3.amazon.api.reviews.Find;
import com.cs601.project3.amazon.api.reviews.SearchBarAsin;
import com.cs601.project3.amazon.api.reviews.SearchBarReviewTerm;
import com.cs601.project3.amazon.api.reviews.SearchReviews;
import com.cs601.project3.server.models.HttpHandler;

public class Reviews {
    public static HttpHandler searchBarReviewTerm = new SearchBarReviewTerm();
    public static HttpHandler searchBarAsin = new SearchBarAsin();
    public static HttpHandler searchReviews = new SearchReviews();
    public static HttpHandler find = new Find();
}
