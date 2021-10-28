package com.cs601.project3.amazon.api.reviews.views;

import com.cs601.project3.amazon.models.QA;
import com.cs601.project3.amazon.models.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsQAsParser {
    public static ArrayList<QA> getQAsFromStrings(List<String> stringifiedQAs) {
        ArrayList<QA> reviews = new ArrayList<>();
        for (String qa : stringifiedQAs) {
            String EOP = Review.EOP;
            String[] lines = qa.split(EOP);

            String question = "";
            String answer = "";
            String answerTime = "";

            for (String line : lines) {
                String[] property = line.split("=");
                String label = property[0];
                String value = property[1];
                if (label.equals("question")) question = value;
                if (label.equals("answer")) answer = value;
                if (label.equals("answerTime")) answerTime = value;
            }

            reviews.add(new QA(question, answer, answerTime));
        }
        return reviews;
    }

    public static ArrayList<Review> getReviewsFromStrings(List<String> stringifiedReviews) {
        ArrayList<Review> reviews = new ArrayList<>();
        for (String review : stringifiedReviews) {
            String EOP = Review.EOP;
            String[] lines = review.split(EOP);

            String reviewerName = "";
            int overall = 0;
            String summary = "";
            String reviewText = "";
            String reviewTime = "";

            for (String line : lines) {
                String[] property = line.split("=");
                String label = property[0];
                String value = property[1];
                if (label.equals("reviewerName")) reviewerName = value;
                if (label.equals("overall")) overall = Integer.parseInt(value);
                if (label.equals("summary")) summary = value;
                if (label.equals("reviewText")) reviewText = value;
                if (label.equals("reviewTime")) reviewTime = value;
            }

            reviews.add(new Review(reviewerName, overall, summary, reviewText, reviewTime));
        }
        return reviews;
    }
}
