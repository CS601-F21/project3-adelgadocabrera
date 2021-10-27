package com.cs601.project3.amazon.controllers;

import com.cs601.project3.amazon.models.ArgsParserResponse;

/**
 * Author: Alberto Delgado
 * <p>
 * Parses the user input arguments to retrieve the -reviews and -qa flags
 */
public class ArgsParser {
    private static final String reviewsFlag = "-reviews";
    private static final String qaFlag = "-qa";

    /**
     * Returns the response in ArgsParserResponse data structure that contains whether
     * it has errors and if successful, the user inputs.
     *
     * @param args
     * @return
     */
    public static ArgsParserResponse get(String[] args) {
        String reviews = "";
        String qa = "";
        if (args.length != 4)
            return ArgsParserResponse.failed("Incorrect number of arguments. Please specify a file for -reviews and for -qa");
        if (args[0].equals(reviewsFlag)) reviews = args[1];
        if (args[2].equals(qaFlag)) qa = args[3];

        if (reviews.isEmpty()) return ArgsParserResponse.failed("Missing file " + reviewsFlag);
        if (qa.isEmpty()) return ArgsParserResponse.failed("Missing file " + qaFlag);

        return ArgsParserResponse.successful(reviews, qa);
    }
}
