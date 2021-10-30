package com.cs601.project3.slack.api.bot;

import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.slack.api.bot.views.Form;

import java.io.IOException;
import java.util.ArrayList;

public class MessageForm implements HttpHandler {
    ArrayList<String> listOfSentMessages;

    public MessageForm(ArrayList<String> listOfSentMessages) {
        this.listOfSentMessages = listOfSentMessages;
    }

    public void handle(Request req, Response res) {
        String body = Form.heroWithPastMessages(listOfSentMessages);
        try {
            res.status(HttpStatus.OK).send(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
