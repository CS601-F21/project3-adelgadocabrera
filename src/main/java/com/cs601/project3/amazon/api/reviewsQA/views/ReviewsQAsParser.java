package com.cs601.project3.amazon.api.reviewsQA.views;

import com.cs601.project3.amazon.models.QA;
import com.cs601.project3.amazon.models.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Parses the Reviews and QAs from strings into objects
 */
public class ReviewsQAsParser {
    /**
     * Builds a list of QAs from a given list of stringified QAs
     *
     * @param stringifiedQAs
     * @return
     */
    public static ArrayList<QA> getQAsFromStrings(List<String> stringifiedQAs) {
        ArrayList<QA> reviews = new ArrayList<>();
        for (String qa : stringifiedQAs) {
            String EOP = Review.EOP;
            String[] lines = qa.split(EOP);

            String question = "";
            String answer = "";
            String answerTime = "";

            for (String line : lines) {
                try {
                    String[] property = line.split("=");
                    String label = property[0];
                    String value = property[1];
                    if (label.equals("question")) question = value;
                    if (label.equals("answer")) answer = value;
                    if (label.equals("answerTime")) answerTime = value;
                } catch (Exception e) {
                    // label or value doesn't exits due to incomplete json file
                }
            }

            if (!question.isEmpty() &&
                    !answer.isEmpty() &&
                    !answerTime.isEmpty()) {
                reviews.add(new QA(question, answer, answerTime));
            }
        }
        return reviews;
    }

    /**
     * Builds a list of Reviews from a given list of stringified Reviews
     *
     * @param stringifiedReviews
     * @return
     */
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
                try {
                    String[] property = line.split("=");
                    String label = property[0];
                    String value = property[1];
                    if (label.equals("reviewerName")) reviewerName = value;
                    if (label.equals("overall")) overall = Integer.parseInt(value);
                    if (label.equals("summary")) summary = value;
                    if (label.equals("reviewText")) reviewText = value;
                    if (label.equals("reviewTime")) reviewTime = value;
                } catch (Exception e) {
                    // catch if it does not exist label or value due to incomplete json file
                }
            }

            if (!reviewerName.isEmpty() &&
                    overall != 0 &&
                    !summary.isEmpty() &&
                    !reviewText.isEmpty() &&
                    !reviewTime.isEmpty()) {
                reviews.add(new Review(reviewerName, overall, summary, reviewText, reviewTime));
            }
        }
        return reviews;
    }
}
