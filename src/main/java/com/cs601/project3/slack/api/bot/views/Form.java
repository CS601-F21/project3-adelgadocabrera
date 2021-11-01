package com.cs601.project3.slack.api.bot.views;

import com.cs601.project3.server.views.Html;

import java.util.ArrayList;

/**
 * @author Alberto Delgado
 * <p>
 * HTML elements of BOT response
 */
public class Form {
    static final String form = """
                        <form class="input-wrapper" action="/slackbot" method="post">
                            <label for="term" class="label">Message</label>
                            <input type="text" name="message" class="input" />
                            <button type="submit" class="search-button">Publish!</button>
                        </form>
            """;

    static final String hero = """
            <div class="container">
                    <h1>Post message on Slack</h1>
                    """ + form + """
            </div>
            """;

    /**
     * Returns the HTML response with a title, input and button aka HERO
     *
     * @param listOfSentMessages
     * @return
     */
    public static String heroWithPastMessages(ArrayList<String> listOfSentMessages) {
        return Html.build(Styles.css, hero + Common.sentMessages(listOfSentMessages));
    }
}
