package com.cs601.project3.slack.api.bot;

import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.models.HttpHandler;
import com.cs601.project3.server.models.HttpStatus;
import com.cs601.project3.server.models.Request;
import com.cs601.project3.server.models.Response;
import com.cs601.project3.slack.api.bot.views.Form;
import com.cs601.project3.slack.api.bot.views.PostResponse;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Publish messages to a Slack channel. Slack channel and token
 * are defined in a .env file
 * <p>
 * After sending message, it will return feedback of whether the
 * message was successful or not and a list of sent messages
 */
public class PublishMessage implements HttpHandler {
    ArrayList<String> listOfSentMessages;

    // slack api
    private static final String SLACK_DOMAIN = "https://slack.com";
    private static final String SLACK_API = "/api/chat.postMessage";
    private static final String ENDPOINT = SLACK_DOMAIN + SLACK_API;

    // env variables
    private static final Dotenv dotenv = Dotenv.load();
    private static final String CHANNEL = dotenv.get("channel");
    private static final String TOKEN = dotenv.get("token");

    public PublishMessage(ArrayList<String> listOfSentMessages) {
        this.listOfSentMessages = listOfSentMessages;
    }

    /**
     * Publish message to Slack channel
     *
     * @param req
     * @param res
     */
    public void handle(Request req, Response res) {
        String payload = req.getBody();
        String[] payloadParts = payload.trim().split("=");
        String MESSAGE_FLAG = "message";

        // if request is bad formed return BAD REQUEST
        if (!Helpers.payloadHasQuery(payload, MESSAGE_FLAG)) {
            Helpers.sendBadRequest(res);
            return;
        }

        // if request has no message returns the hero again.
        // this should be handled by FE in js
        if (payloadParts.length <= 1) {
            try {
                res.status(HttpStatus.OK).send(Form.heroWithPastMessages(listOfSentMessages));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // body
        String message = java.net.URLDecoder.decode(Helpers.getMessage(payload), StandardCharsets.UTF_8);
        SlackMessage slackMessage = new SlackMessage(CHANNEL, message);
        Gson gson = new Gson();
        String apiBody = gson.toJson(slackMessage);

        // create headers
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Authorization: Bearer " + TOKEN);
        headers.add("Content-type: application/json");

        try {
            ClientResponse response = ClientRequest.post(ENDPOINT, apiBody, headers);
            if (response.statusCode == 200) {
                listOfSentMessages.add(0, message); // message history is sorted from newest to oldest
                res.status(HttpStatus.OK).send(PostResponse.response("Message sent successfully", listOfSentMessages));
            } else {
                res.status(HttpStatus.OK).send(PostResponse.response("Oops! Something went wrong!", listOfSentMessages));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private record SlackMessage(String channel, String text) {
    }
}
