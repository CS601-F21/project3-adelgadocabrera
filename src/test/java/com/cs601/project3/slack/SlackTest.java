package com.cs601.project3.slack;

import com.cs601.project3.HtmlValidator;
import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.models.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

class SlackTest {
    // constants
    int PORT = 9090;
    String DOMAIN = "http://localhost:" + PORT;
    String SHUTDOWN_PATH = "/shutdown";
    String URL = DOMAIN + SHUTDOWN_PATH;
    public static String[] args;

    @BeforeAll
    public static void setUp() {
        args = new String[0];
    }

    @Test
    @DisplayName("should shutdown server on Get request to /shutdown endpoint")
    void throwsOnServerShutdown() throws IOException, InterruptedException {
        Thread serverThread = new Thread(() -> Slack.main(args));
        serverThread.start();

        TimeUnit.SECONDS.sleep(1);
        ClientRequest.get(URL);
        serverThread.join();

        Assertions.assertThrows(ConnectException.class, () -> ClientRequest.get(URL));
    }

    @Test
    @DisplayName("should return correct headers/body on shutdown call")
    void headers() throws IOException, InterruptedException {
        Thread serverThread = new Thread(() -> Slack.main(args));
        serverThread.start();

        TimeUnit.SECONDS.sleep(1);
        ClientResponse res = ClientRequest.get(URL);
        serverThread.join();

        Assertions.assertEquals(200, res.statusCode);
        Assertions.assertTrue(HtmlValidator.isValid(res.body));
        Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
    }

}