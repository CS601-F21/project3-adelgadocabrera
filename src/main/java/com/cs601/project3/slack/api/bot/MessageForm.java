package com.cs601.project3.slack.api.bot;

import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.slack.api.bot.views.Form;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Returns an HTML form to post a message to Slack.
 * Additionally, it appends history of sent messages
 */
public class MessageForm implements HttpHandler {
    ArrayList<String> listOfSentMessages;

    /**
     * Messages history
     *
     * @param listOfSentMessages
     */
    public MessageForm(ArrayList<String> listOfSentMessages) {
        this.listOfSentMessages = listOfSentMessages;
    }

    /**
     * Returns HERO + messages history
     *
     * @param req
     * @param res
     */
    public void handle(Request req, Response res) {
        String body = Form.heroWithPastMessages(listOfSentMessages);
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
