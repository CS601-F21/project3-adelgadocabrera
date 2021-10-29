package com.cs601.project3.amazon.controllers;

import com.cs601.project3.amazon.models.InvertedIndex;
import com.cs601.project3.amazon.models.JsonParserResponse;
import com.cs601.project3.amazon.models.QA;

import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Creates a Doc finder for QA
 * Creates inverted index data structure for querying
 */
public class QAsFinder extends DocsFinder<QA> {

    private QAsFinder(InvertedIndex invertedIndex, ArrayList<QA> store) {
        super(invertedIndex, store);
    }

    /**
     * Each Doc Finder has a different logic when creating the inverted
     * index. It is the build's method responsibility to select what
     * terms it wants to index
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static QAsFinder build(String fileName, int maxNumberOfItems) throws Exception {
        JsonParser<QA> parser = new JsonParser<>(QA.class);
        JsonParserResponse<QA> parserResponse = parser.parse(fileName, maxNumberOfItems);

        if (parserResponse.hasError()) {
            throw new Exception(parserResponse.getErrorMsg());
        }

        ArrayList<QA> qas = parserResponse.getData();
        InvertedIndex invertedIndex = new InvertedIndex();
        QAsFinder qasFinder = new QAsFinder(invertedIndex, qas);

        for (int i = 0; i < qas.size(); i++) {
            QA qa = qas.get(i);
            String question = qa.getQuestion();
            String answer = qa.getAnswer();

            for (String word : question.split(" ")) {
                invertedIndex.update(word, i);
            }

            for (String word : answer.split(" ")) {
                invertedIndex.update(word, i);
            }

        }
        return qasFinder;
    }

}
