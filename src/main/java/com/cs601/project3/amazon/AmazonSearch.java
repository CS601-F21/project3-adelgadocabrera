package com.cs601.project3.amazon;

import com.cs601.project3.amazon.api.ReviewsQA;
import com.cs601.project3.amazon.api.Shutdown;
import com.cs601.project3.amazon.controllers.ArgsParser;
import com.cs601.project3.amazon.controllers.QAsFinder;
import com.cs601.project3.amazon.controllers.ReviewsFinder;
import com.cs601.project3.amazon.models.ArgsParserResponse;
import com.cs601.project3.server.controllers.Server;

/**
 * @author Alberto Delgado
 * <p>
 * Builds an InvertedIndex data structure and creates two instances of it.
 * The first will hold the data from a review dataset and the second will
 * hold the data from the Q&A dataset - both dataset files have to be
 * specified in the args with the -reviews and -qa flags.
 * <p>
 * Runs a server at lets clients search for reviews by term
 * and search for reviews/qas by ASIN
 */
public class AmazonSearch {
    public static ReviewsFinder reviews;
    public static QAsFinder qas;
    private static Server app;
    private static final int PORT = 8080;
    private static final int maxNumberOfItems = 10000;

    /**
     * Initiates inverted index, initiates server and runs till shutdown.
     *
     * @param args
     */
    public static void main(String[] args) {
        initInvertedIndex(args);
        initServer();

        System.out.println("App listening in port " + PORT);
        app.run();
        System.out.println("Server shutdown.");
    }

    /**
     * Creates a server and defines endpoints
     */
    private static void initServer() {
        app = new Server(PORT);

        // reviewsearch endpoints
        app.get("/reviewsearch", ReviewsQA.searchBarReviewSearch);
        app.post("/reviewsearch", ReviewsQA.reviewSearch);

        // find endpoints
        app.get("/find", ReviewsQA.searchBarFindAsin);
        app.post("/find", ReviewsQA.findAsin);

        // shutdown
        app.get("/shutdown", new Shutdown());
    }

    /**
     * Shutdowns the server
     */
    public static void shutdown() {
        app.shutdown();
    }

    /**
     * Builds the inverted index
     *
     * @param args
     */
    public static void initInvertedIndex(String[] args) {
        ArgsParserResponse fileNames = ArgsParser.get(args);

        if (fileNames.hasErrors()) {
            System.out.println(fileNames.getErrorMsg());
            System.exit(1);
        }

        String reviewsFile = fileNames.getReviews();
        String qasFile = fileNames.getQAs();

        try {
            reviews = ReviewsFinder.build(reviewsFile, maxNumberOfItems);
            qas = QAsFinder.build(qasFile, maxNumberOfItems);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

}
