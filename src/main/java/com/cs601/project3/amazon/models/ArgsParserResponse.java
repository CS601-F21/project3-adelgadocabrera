package com.cs601.project3.amazon.models;

/**
 * Author: Alberto Delgado
 * <p>
 * Data Structure for ArgsParser class.
 * It is a way of informing whether there is an error without throw errors
 * and passing the data
 */
public class ArgsParserResponse {
    private final boolean hasErrors;
    private final String errorMsg;
    private final String qas;
    private final String reviews;

    /**
     * Private constructor.
     * Doesn't make sense to pass 4 variables each time
     * you want to create an instance
     *
     * @param hasErrors
     * @param errorMsg
     * @param reviews
     * @param qas
     */
    private ArgsParserResponse(boolean hasErrors, String errorMsg, String reviews, String qas) {
        this.hasErrors = hasErrors;
        this.errorMsg = errorMsg;
        this.qas = qas;
        this.reviews = reviews;
    }

    /**
     * Constructor when failed
     *
     * @param errorMsg
     * @return
     */
    public static ArgsParserResponse failed(String errorMsg) {
        return new ArgsParserResponse(true, errorMsg, null, null);
    }

    /**
     * Constructor on success
     *
     * @param reviews
     * @param qas
     * @return
     */
    public static ArgsParserResponse successful(String reviews, String qas) {
        return new ArgsParserResponse(false, null, reviews, qas);
    }

    // hasErrors getter
    public boolean hasErrors() {
        return hasErrors;
    }

    // errorMsg getter
    public String getErrorMsg() {
        return errorMsg;
    }

    // QAs getter
    public String getQAs() {
        return qas;
    }

    // reviews getter
    public String getReviews() {
        return reviews;
    }
}
