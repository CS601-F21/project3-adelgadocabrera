package com.cs601.project3.amazon;

import com.cs601.project3.amazon.api.Reviews;
import com.cs601.project3.amazon.controllers.ArgsParser;
import com.cs601.project3.amazon.controllers.QAsFinder;
import com.cs601.project3.amazon.controllers.ReviewsFinder;
import com.cs601.project3.amazon.models.ArgsParserResponse;
import com.cs601.project3.server.controllers.Server;

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
    public static ReviewsFinder reviews;
    public static QAsFinder qas;
    private static Server app;
    private static final int PORT = 8080;

    public static void main(String[] args) {
        initInvertedIndex(args);
        initServer();
        app.run();
    }

    private static void initServer() {
        app = new Server(PORT);

        // reviewsearch endpoints
        app.get("/reviewsearch", Reviews.searchBarReviewTerm);
        app.post("/reviewsearch", Reviews.reviewSearch);

        // find endpoints
        app.get("/find", Reviews.searchBarAsin);
        app.post("/find", Reviews.find);
    }

    private static void initInvertedIndex(String[] args) {
        ArgsParserResponse fileNames = ArgsParser.get(args);

        if (fileNames.hasErrors()) {
            System.out.println(fileNames.getErrorMsg());
            System.exit(1);
        }

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
