package com.cs601.project3.slack.api.bot.models;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Data structure to get 'ok' property from the Slack API response.
 */
public class SlackResponse {
    public final boolean ok;

    public SlackResponse(boolean ok) {
        this.ok = ok;
    }
}
