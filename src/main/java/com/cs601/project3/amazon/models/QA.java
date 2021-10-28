package com.cs601.project3.amazon.models;

/**
 * Author: Alberto Delgado
 * <p>
 * Data structure for scanned QAs
 */
public class QA extends Doc {
    public static final String EOP = "END_OF_PROPERTY";
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

    /**
     * Created to send back on client request
     *
     * @param answerTime
     * @param question
     * @param answer
     */
    public QA(
            String question,
            String answer,
            String answerTime
    ) {
        this.answerTime = answerTime;
        this.answer = answer;
        this.question = question;
        this.answerType = null;
        this.questionType = null;
        this.asin = null;
        this.unixTime = -1;
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

    // get answer time
    public String getAnswerTime() {
        return answerTime;
    }

    // toString() method overriding
    @Override
    public String toString() {
        return "question=" + question + EOP +
                "answer=" + answer + EOP +
                "answerTime=" + answerTime + EOP;
    }
}
