package com.cs601.project3.amazon.api;

import com.cs601.project3.amazon.api.reviews.FindAsin;
import com.cs601.project3.amazon.api.reviews.ReviewSearch;
import com.cs601.project3.amazon.api.reviews.SearchBarFindAsin;
import com.cs601.project3.amazon.api.reviews.SearchBarReviewSearch;
import com.cs601.project3.server.models.HttpHandler;

public class Reviews {
    public static HttpHandler searchBarReviewSearch = new SearchBarReviewSearch();
    public static HttpHandler searchBarFindAsin = new SearchBarFindAsin();
    public static HttpHandler reviewSearch = new ReviewSearch();
    public static HttpHandler findAsin = new FindAsin();
}
