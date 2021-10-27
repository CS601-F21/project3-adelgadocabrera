package com.cs601.project3.amazon.controllers;

import com.cs601.project3.amazon.models.Doc;
import com.cs601.project3.amazon.models.InvertedIndex;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Abstract class for all current and future JSON files to be able to search
 * terms in a inverted index as well as get doc ASIN.
 * <p>
 * It requires a list of <T> type items - compliant to Doc - and an inverted index.
 *
 * @param <T>
 */
public abstract class DocsFinder<T extends Doc> {
    private final InvertedIndex invertedIndex;
    private final ArrayList<T> store;

    /**
     * Constructor is protected. It is weird at first but the intention
     * is for DocsFinder subclasses not to be created with "new" but
     * use a static method instead, so is the static method who will
     * access this constructor.
     *
     * @param invertedIndex
     * @param store
     */
    protected DocsFinder(InvertedIndex invertedIndex, ArrayList<T> store) {
        this.invertedIndex = invertedIndex;
        this.store = store;
    }

    /**
     * Searches for a term in the inverted index
     *
     * @param term
     * @return
     */
    public ArrayList<String> search(String term) {
        ArrayList<String> results = new ArrayList<>();
        ArrayList<Integer> docIds = invertedIndex.searchTerm(term);

        for (int id : docIds) {
            Doc doc = store.get(id);
            results.add(doc.toString());
        }
        return results;
    }

    /**
     * Partially searches for a term in the inverted index
     *
     * @param term
     * @return
     */
    public ArrayList<String> partialSearch(String term) {
        ArrayList<String> results = new ArrayList<>();
        ArrayList<Integer> docIds = invertedIndex.partialSearch(term);
        for (int id : docIds) {
            T doc = store.get(id);
            results.add(doc.toString());
        }
        return results;
    }

    /**
     * Searches for all docs matching specified ASIN
     *
     * @param asin
     * @return
     */
    public ArrayList<String> findAsin(String asin) {
        asin = asin.toLowerCase();
        ArrayList<String> results = new ArrayList<>();
        for (T doc : store) {
            if (doc.getAsin().equalsIgnoreCase(asin)) {
                results.add(doc.toString());
            }
        }
        return results;
    }
}
