package com.cs601.project3.AmazonReviews.models;

import java.util.*;

/**
 * Author: Alberto Delgado
 * <p>
 * Data structure that stores terms and stores the ID of the documents
 * that contains that word, as well as a counter to know how many times
 * that word is contained.
 */
public class InvertedIndex {
    private final HashMap<String, HashMap<Integer, Integer>> invertedIndex = new HashMap<>();

    public InvertedIndex() {
    }

    /**
     * Updates the inverted index
     *
     * @param word
     * @param id
     */
    public void update(String word, int id) {
        word = wordFormatter(word);
        if (invertedIndex.containsKey(word)) {
            HashMap<Integer, Integer> wordIndex = invertedIndex.get(word);
            if (wordIndex.containsKey(id)) {
                wordIndex.put(id, wordIndex.get(id) + 1);
            } else {
                wordIndex.put(id, 1);
            }
        } else {
            HashMap<Integer, Integer> wordIndex = new HashMap<>();
            wordIndex.put(id, 1);
            invertedIndex.put(word, wordIndex);
        }
    }

    /**
     * Helper to make string lower case and remove non alphanumerical chars
     *
     * @param word
     * @return
     */
    private String wordFormatter(String word) {
        return filterNonAlphanumericalChars(word.toLowerCase());
    }

    /**
     * Filter non alphanumerical chars
     *
     * @param str
     * @return
     */
    private String filterNonAlphanumericalChars(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Searches for a term and returns the list of IDs with that term
     * sorted from MOST to LEAST amount of matches
     *
     * @param term
     * @return
     */
    public ArrayList<Integer> searchTerm(String term) {
        term = term.toLowerCase();
        ArrayList<Integer> docIds = new ArrayList<>();

        if (invertedIndex.containsKey(term)) {
            HashMap<Integer, Integer> wordIndex = invertedIndex.get(term);
            List<Map.Entry<Integer, Integer>> docsContainingWord = new ArrayList<>(wordIndex.entrySet());
            docsContainingWord.sort(Map.Entry.comparingByValue());
            Collections.reverse(docsContainingWord);

            for (Map.Entry<Integer, Integer> item : docsContainingWord) {
                docIds.add(item.getKey());
            }
        }

        return docIds;
    }

    /**
     * Partially searches a term and returns the list of IDs with
     * that partial term
     *
     * @param term
     * @return
     */
    public ArrayList<Integer> partialSearch(String term) {
        term = term.toLowerCase();
        HashSet<Integer> results = new HashSet<>();
        Set<String> invertedIndexWords = invertedIndex.keySet();
        ArrayList<String> wordsContainingTerm = new ArrayList<>();

        for (String word : invertedIndexWords) {
            if (word.contains(term)) wordsContainingTerm.add(word);
        }

        for (String word : wordsContainingTerm) {
            HashMap<Integer, Integer> wordIndex = invertedIndex.get(word);
            for (Map.Entry<Integer, Integer> docEntry : wordIndex.entrySet()) {
                int docId = docEntry.getKey();
                results.add(docId);
            }
        }

        return new ArrayList<>(results);
    }

}
