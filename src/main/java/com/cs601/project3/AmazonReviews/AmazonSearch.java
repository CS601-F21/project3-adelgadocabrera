package com.cs601.project3.AmazonReviews;

import com.cs601.project3.AmazonReviews.console.Console;

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

    // Runs the CLI type app
    public static void main(String[] args) {
        Console.run(args);
    }

}
