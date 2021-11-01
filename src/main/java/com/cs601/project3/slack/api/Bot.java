package com.cs601.project3.slack.api;

import com.cs601.project3.slack.api.bot.MessageForm;
import com.cs601.project3.slack.api.bot.PublishMessage;

import java.util.ArrayList;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Group handler.
 * Handlers for posting message to slack channel
 */
public class Bot {
    // messages history
    private static final ArrayList<String> listOfSentMessages = new ArrayList<>();

    public static MessageForm messageForm = new MessageForm(listOfSentMessages);
    public static PublishMessage publishMessage = new PublishMessage(listOfSentMessages);
}
