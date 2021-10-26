package com.cs601.project3.AmazonReviews;

import com.cs601.project3.AmazonReviews.controllers.ArgsParser;
import com.cs601.project3.AmazonReviews.controllers.QAsFinder;
import com.cs601.project3.AmazonReviews.controllers.ReviewsFinder;
import com.cs601.project3.AmazonReviews.models.ArgsParserResponse;

/**
 * Author: Alberto Delgado
 * <p>
 * Builds an InvertedIndex data structure and creates two instances of it.
 * The first will hold the data from a review dataset and the second will
 * hold the data from the Q&A dataset - both dataset files have to be
 * specified in the args with the -reviews and -qa flags.
 * <p>
 * AmazonSearch provides a cli-type interface to search reviews/QAs from
 * the previously mentioned datasets.
 */
public class AmazonSearch {

    public static void main(String[] args) {
    }

    private void initInvertedIndex(String[] args) {
        ArgsParserResponse fileNames = ArgsParser.get(args);

        if (fileNames.hasErrors()) {
            System.out.println(fileNames.getErrorMsg());
            System.exit(1);
        }

        ReviewsFinder reviews;
        QAsFinder qas;
        String reviewsFile = fileNames.getReviews();
        String qasFile = fileNames.getQAs();

        try {
            reviews = ReviewsFinder.build(reviewsFile);
            qas = QAsFinder.build(qasFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

}
