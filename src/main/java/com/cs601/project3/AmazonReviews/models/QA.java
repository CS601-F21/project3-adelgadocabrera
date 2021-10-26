package com.cs601.project3.AmazonReviews.models;

/**
 * Author: Alberto Delgado
 * <p>
 * Data structure for scanned QAs
 */
public class QA extends Doc {
    private final String questionType;
    private final String asin;
    private final String answerTime;
    private final int unixTime;
    private final String question;
    private final String answerType;
    private final String answer;

    public QA(
            String questionType,
            String asin,
            String answerTime,
            int unixTime,
            String question,
            String answerType,
            String answer) {
        this.questionType = questionType;
        this.asin = asin;
        this.answerTime = answerTime;
        this.unixTime = unixTime;
        this.question = question;
        this.answerType = answerType;
        this.answer = answer;
    }

    // asin getter
    public String getAsin() {
        return asin;
    }

    // question getter
    public String getQuestion() {
        return question;
    }

    // answer getter
    public String getAnswer() {
        return answer;
    }

    // toString() method overriding
    @Override
    public String toString() {
        return "QA{" +
                "questionType='" + questionType + '\'' +
                ", asin='" + asin + '\'' +
                ", answerTime='" + answerTime + '\'' +
                ", unixTime=" + unixTime +
                ", question='" + question + '\'' +
                ", answerType='" + answerType + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
