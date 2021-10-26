package com.cs601.project3.AmazonReviews.models;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Data structured for scanned Reviews
 */
public class Review extends Doc {
    private final String reviewerID;
    private final String asin;
    private final String reviewerName;
    private final ArrayList<Integer> helpful;
    private final String reviewText;
    private final int overall;
    private final String summary;
    private final int unixReviewTime;
    private final String reviewTime;


    public Review(
            String reviewerID,
            String asin,
            String reviewerName,
            ArrayList<Integer> helpful,
            String reviewText,
            int overall,
            String summary,
            int unixReviewTime,
            String reviewTime) {
        this.reviewerID = reviewerID;
        this.asin = asin;
        this.reviewerName = reviewerName;
        this.helpful = helpful;
        this.reviewText = reviewText;
        this.overall = overall;
        this.summary = summary;
        this.unixReviewTime = unixReviewTime;
        this.reviewTime = reviewTime;
    }

    // asin getter
    public String getAsin() {
        return asin;
    }

    // reviewText getter
    public String getReviewText() {
        return reviewText;
    }

    // toString() method overriding
    @Override
    public String toString() {
        return "Review{" +
                "reviewerID='" + reviewerID + '\'' +
                ", asin='" + asin + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", helpful=" + helpful +
                ", reviewText='" + reviewText + '\'' +
                ", overall=" + overall +
                ", summary='" + summary + '\'' +
                ", unixReviewTime=" + unixReviewTime +
                ", reviewTime='" + reviewTime + '\'' +
                '}';
    }
}
