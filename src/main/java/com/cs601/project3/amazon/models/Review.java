package com.cs601.project3.amazon.models;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Data structured for scanned Reviews
 */
public class Review extends Doc {
    public static final String EOP = "END_OF_PROPERTY";
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

    public Review(
            String reviewerName,
            int overall,
            String summary,
            String reviewText,
            String reviewTime
    ) {
        this.reviewerName = reviewerName;
        this.overall = overall;
        this.summary = summary;
        this.reviewText = reviewText;
        this.reviewTime = reviewTime;
        this.reviewerID = null;
        this.asin = null;
        this.helpful = null;
        this.unixReviewTime = -1;
    }

    // get reviewer name
    public String getReviewerName() {
        return reviewerName;
    }

    // get overall
    public int getOverall() {
        return overall;
    }

    // get summary
    public String getSummary() {
        return summary;
    }

    // get date
    public String getReviewTime() {
        return reviewTime;
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
        return "reviewerName=" + reviewerName + EOP +
                "overall=" + overall + EOP +
                "summary=" + summary + EOP +
                "reviewText=" + reviewText + EOP +
                "reviewTime=" + reviewTime + EOP
                ;
    }
}
