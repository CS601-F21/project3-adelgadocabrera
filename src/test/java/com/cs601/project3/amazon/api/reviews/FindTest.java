package com.cs601.project3.amazon.api.reviews;

import com.cs601.project3.amazon.api.Reviews;
import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.server.views.Html;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FindTest {
    // app 
    Server app;
    private static final int PORT = 5000;
    final String PATH = "/path";
    final String URL = "http://localhost:" + PORT + PATH;

    // testing outputs
    static String HANDLER_OUTPUT = "OUTPUT";
    static String handlerOutput = null;
    static String RESPONSE = Html.build("RESPONSE");

    // threads
    Thread serverThread;
    Thread clientThread;

    @BeforeEach
    void setUp() {
        app = new Server(PORT);
        app.post("/find", Reviews.find);
        serverThread = new Thread(app);
    }

    @AfterEach
    void clean() throws IOException, InterruptedException {
        handlerOutput = null;
        if (app != null) app.shutdown();
        if (serverThread != null) serverThread.stop();
        if (clientThread != null) clientThread.stop();
    }

    @Test
    void handle() {
        
    }
}