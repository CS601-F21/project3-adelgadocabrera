package com.cs601.project3.amazon.controllers;

import com.cs601.project3.amazon.models.InvertedIndex;
import com.cs601.project3.amazon.models.JsonParserResponse;
import com.cs601.project3.amazon.models.Review;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Creates a Doc finder for Reviews
 * Creates inverted index data structure for querying
 */
public class ReviewsFinder extends DocsFinder<Review> {

    private ReviewsFinder(InvertedIndex invertedIndex, ArrayList<Review> store) {
        super(invertedIndex, store);
    }

    /**
     * Each Doc Finder has a different logic when creating the inverted
     * index. It is the build's method responsibility to select what
     * terms it wants to index
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static ReviewsFinder build(String fileName, int maxNumberOfItems) throws Exception {
        JsonParser<Review> parser = new JsonParser<>(Review.class);
        JsonParserResponse<Review> parserResponse = parser.parse(fileName, maxNumberOfItems);

        if (parserResponse.hasError()) {
            throw new Exception(parserResponse.getErrorMsg());
        }

        ArrayList<Review> reviews = parserResponse.getData();
        InvertedIndex invertedIndex = new InvertedIndex();
        ReviewsFinder reviewsFinder = new ReviewsFinder(invertedIndex, reviews);

        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            String reviewText = review.getReviewText();

            for (String word : reviewText.split(" ")) {
                invertedIndex.update(word, i);
            }
        }

        return reviewsFinder;
    }
}
