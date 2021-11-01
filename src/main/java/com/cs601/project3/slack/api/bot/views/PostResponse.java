package com.cs601.project3.slack.api.bot.views;

import com.cs601.project3.server.views.Html;

import java.util.ArrayList;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Builds the HTML response for the BOT response upon new message submission
 */
public class PostResponse {
    public static String response(String msg, ArrayList<String> listOfSentMessages) {
        StringBuilder body = new StringBuilder();
        body.append(Form.hero);
        body.append(Common.containerStart);
        body.append(msg);
        body.append(Common.containerEnd);
        body.append(Common.sentMessages(listOfSentMessages));
        return Html.build(Styles.css, body.toString());
    }
}
