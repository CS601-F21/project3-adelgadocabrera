package com.cs601.project3.AmazonReviews.console;

import com.cs601.project3.AmazonReviews.controllers.QAsFinder;
import com.cs601.project3.AmazonReviews.controllers.ReviewsFinder;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Executes the user queries such as finding docs with certain asin, a term or a partial term.
 */
public class UserQueries {
    ReviewsFinder reviews;
    QAsFinder qas;

    public UserQueries(ReviewsFinder reviews, QAsFinder qas) {
        this.reviews = reviews;
        this.qas = qas;
    }

    /**
     * Finds all docs with specified ASIN
     *
     * @param term
     */
    protected void find(String term) {
        ArrayList<String> reviewsAsin = reviews.findAsin(term);
        ArrayList<String> qasAsin = qas.findAsin(term);

        String reviewsHeader = "\nASIN results for reviews " + term;
        queryFormatter(reviewsAsin, reviewsHeader);

        String qasHeader = "\nASIN results for QAs " + term;
        queryFormatter(qasAsin, qasHeader);
    }

    /**
     * Searches all docs in Reviews containing user specified term
     *
     * @param term
     */
    protected void reviewSearch(String term) {
        ArrayList<String> results = reviews.search(term);
        String header = "\nSearch results in Reviews for " + term;
        queryFormatter(results, header);
    }

    /**
     * Searches all docs in QAs containing user specified term
     *
     * @param term
     */
    protected void qaSearch(String term) {
        ArrayList<String> results = qas.search(term);
        String header = "\nSearch results in QA for " + term;
        queryFormatter(results, header);
    }

    /**
     * Partially searches all docs in Reviews with user specified term
     *
     * @param term
     */
    protected void reviewPartialSearch(String term) {
        String header = "\nPartial search results in Reviews for " + term;
        ArrayList<String> results = reviews.partialSearch(term);
        queryFormatter(results, header);
    }

    /**
     * Partially searches all docs in QAs with user specified term
     *
     * @param term
     */
    protected void qaPartialSearch(String term) {
        String header = "\nPartial search results in QAs for " + term;
        ArrayList<String> results = qas.partialSearch(term);
        queryFormatter(results, header);
    }

    /**
     * Helper method so all queries are returned with the same format
     *
     * @param data
     * @param header
     */
    private void queryFormatter(ArrayList<String> data, String header) {
        System.out.println(header);
        for (String d : data) {
            System.out.println(Prompt.shortString(d));
        }
        System.out.println("\nTotal of " + data.size());
    }
}
