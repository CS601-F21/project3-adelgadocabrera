package com.cs601.project3.slack.api;

import com.cs601.project3.slack.api.bot.MessageForm;
import com.cs601.project3.slack.api.bot.PublishMessage;

import java.util.ArrayList;

public class Bot {
    // messages sent from the server
    private static final ArrayList<String> listOfSentMessages = new ArrayList<>();

    public static MessageForm messageForm = new MessageForm(listOfSentMessages);
    public static PublishMessage publishMessage = new PublishMessage(listOfSentMessages);
}
