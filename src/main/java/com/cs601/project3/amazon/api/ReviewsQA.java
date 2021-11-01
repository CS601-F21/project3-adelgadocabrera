package com.cs601.project3.amazon.api;

import com.cs601.project3.amazon.api.reviewsQA.FindAsin;
import com.cs601.project3.amazon.api.reviewsQA.ReviewSearch;
import com.cs601.project3.amazon.api.reviewsQA.SearchBarFindAsin;
import com.cs601.project3.amazon.api.reviewsQA.SearchBarReviewSearch;
import com.cs601.project3.server.models.HttpHandler;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Handler group.
 * <p>
 * Contains the handlers for Reviews and QA
 */
public class ReviewsQA {
    public static HttpHandler searchBarReviewSearch = new SearchBarReviewSearch();
    public static HttpHandler searchBarFindAsin = new SearchBarFindAsin();
    public static HttpHandler reviewSearch = new ReviewSearch();
    public static HttpHandler findAsin = new FindAsin();
}
