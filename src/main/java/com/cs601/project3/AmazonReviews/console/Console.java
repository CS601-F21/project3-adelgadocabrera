package com.cs601.project3.AmazonReviews.console;

import com.cs601.project3.AmazonReviews.controllers.ArgsParser;
import com.cs601.project3.AmazonReviews.controllers.QAsFinder;
import com.cs601.project3.AmazonReviews.controllers.ReviewsFinder;
import com.cs601.project3.AmazonReviews.models.ArgsParserResponse;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Handles the cli type application. It presents the user with several operations,
 * it can perform - selecting from 0 to 5 in terminal, performs those actions
 * and prints them to the user.
 * Last option, [5] or exit will terminate the app.
 */
public class Console {
    private final UserQueries Query;
    private final String FIND_SELECT = "find";
    private final String REVIEW_SEARCH_SELECT = "reviewsearch";
    private final String QA_SEARCH_SELECT = "qasearch";
    private final String REVIEW_PARTIAL_SEARCH_SELECT = "reviewpartialsearch";
    private final String QA_PARTIAL_SEARCH_SELECT = "qapartialsearch";
    private final String EXIT_INT = "5";
    private final String EXIT_STRING = "exit";

    private Console(ReviewsFinder reviews, QAsFinder qas) {
        this.Query = new UserQueries(reviews, qas);
    }

    /**
     * Runs the entire application.
     * - Parses json files
     * - Creates inverted index
     * - And displays actions that can be performed
     *
     * @param args
     */
    public static void run(String[] args) {
        Console c = init(args);
        if (c == null) return;
        runUI(c);
    }

    /**
     * Initializes the console class:
     * - gets a ReviewsFinder and QAsFinder
     *
     * @param args
     * @return
     */
    private static Console init(String[] args) {
        ArgsParserResponse fileNames = ArgsParser.get(args);

        if (fileNames.hasErrors()) {
            System.out.println(fileNames.getErrorMsg());
            return null;
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
            return null;
        }
        return new Console(reviews, qas);
    }

    /**
     * Handles the cli (ui) logic:
     * - Actions user can perform and handles when to exit.
     *
     * @param c
     */
    private static void runUI(Console c) {
        final boolean running = true;
        while (running) {
            try {
                String userInput = c.showOptions().trim();

                if (userInput.equals(c.EXIT_INT) || userInput.equals(c.EXIT_STRING)) {
                    System.out.println("\nThank you! Have a great day! :)");
                    return;
                }

                if (userInput.split(" ").length != 2) {
                    System.out.println("\nPlease make sure query has format <query> <term>");
                    continue;
                }

                String query = c.getQuery(userInput);
                String term = c.getTerm(userInput);
                c.makeUserQuery(query, term);

            } catch (Exception e) {
                System.out.println("\nOops! Something went wrong!");
            }
        }
    }

    /**
     * Get query to perform from the user input
     *
     * @param userInput
     * @return
     */
    private String getQuery(String userInput) {
        return userInput.split(" ")[0];
    }

    /**
     * Get term to search for from user input
     *
     * @return
     */
    private String getTerm(String userInput) {
        return userInput.split(" ")[1];
    }

    /**
     * Presents to the user with the actions available
     *
     * @return
     * @throws Exception
     */
    private String showOptions() throws Exception {
        ArrayList<String> options = new ArrayList<>();
        options.add("\nUse appropriate format '<query> <term>' to make a query");
        options.add("[0] " + FIND_SELECT + " <asin>");
        options.add("[1] " + REVIEW_SEARCH_SELECT + " <term>");
        options.add("[2] " + QA_SEARCH_SELECT + " <term>");
        options.add("[3] " + REVIEW_PARTIAL_SEARCH_SELECT + " <term>");
        options.add("[4] " + QA_PARTIAL_SEARCH_SELECT + " <term>");
        options.add("[5] " + EXIT_STRING);
        return Prompt.print(options);
    }

    /**
     * Performs the action requested by the user
     *
     * @param input
     * @param term
     */
    private void makeUserQuery(String input, String term) {
        input = input.trim();
        if (input.equals(FIND_SELECT)) Query.find(term);
        if (input.equals(REVIEW_SEARCH_SELECT)) Query.reviewSearch(term);
        if (input.equals(QA_SEARCH_SELECT)) Query.qaSearch(term);
        if (input.equals(REVIEW_PARTIAL_SEARCH_SELECT)) Query.reviewPartialSearch(term);
        if (input.equals(QA_PARTIAL_SEARCH_SELECT)) Query.qaPartialSearch(term);
    }

}
