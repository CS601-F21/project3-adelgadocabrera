package com.cs601.project3.AmazonReviews.models;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Data Structure for JsonParser class.
 * Gives information on success/error of json parsing
 *
 * @param <T>
 */
public class JsonParserResponse<T extends Doc> {
    private final boolean hasError;
    private final String errorMsg;
    private final ArrayList<T> data;

    /**
     * Private to prevent instantiating directly from constructor
     *
     * @param hasError
     * @param errorMsg
     * @param data
     */
    private JsonParserResponse(boolean hasError, String errorMsg, ArrayList<T> data) {
        this.hasError = hasError;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    /**
     * Constructor for on fail
     *
     * @param errorMsg
     * @param <V>
     * @return
     */
    public static <V extends Doc> JsonParserResponse<V> failed(String errorMsg) {
        return new JsonParserResponse<>(true, errorMsg, null);
    }

    /**
     * Constructor for on success
     *
     * @param data
     * @param <V>
     * @return
     */
    public static <V extends Doc> JsonParserResponse<V> successful(ArrayList<V> data) {
        return new JsonParserResponse<>(false, null, data);
    }

    // hasError getter
    public boolean hasError() {
        return hasError;
    }

    // errorMsg getter
    public String getErrorMsg() {
        return errorMsg;
    }

    // data getter
    public ArrayList<T> getData() {
        return data;
    }
}
