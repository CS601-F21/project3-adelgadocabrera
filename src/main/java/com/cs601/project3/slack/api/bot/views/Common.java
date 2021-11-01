package com.cs601.project3.slack.api.bot.views;

import java.util.ArrayList;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * HTML helpers for BOT's response
 */
public class Common {
    static final String containerStart = """
            <div class="container">
                <div class="response">
                    """;
    static final String containerEnd = """
                    </div>
                </div>
            """;
    static final String cardStart = """
                <div class="card">
            """;

    static final String cardEnd = """
                </div>
            """;

    /**
     * Creates a list of cards with a sent message in it
     *
     * @param listOfSentMessages
     * @return
     */
    static String sentMessages(ArrayList<String> listOfSentMessages) {
        StringBuilder body = new StringBuilder();
        body.append(Common.containerStart);
        for (String m : listOfSentMessages) {
            body.append(Common.cardStart);
            body.append(m);
            body.append(Common.cardEnd);
        }
        body.append(Common.containerEnd);
        return body.toString();
    }
}
