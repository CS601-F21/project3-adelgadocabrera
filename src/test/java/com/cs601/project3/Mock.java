package com.cs601.project3;

import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.controllers.Server;
import com.cs601.project3.server.models.CRUD;
import com.cs601.project3.server.models.HttpStatus;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class Mock {

    public static void request(Server app, CRUD operation, String URL) {
        final String GET = "GET";
        final String POST = "POST";

        Thread serverThread = new Thread(app);
        Thread clientThread = new Thread(() -> {
            ClientResponse res;
            try {
                if (operation.equals(CRUD.GET)) {
                    res = ClientRequest.get(URL);
                    // make sure protocols and methods are correct
                    Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
                    Assertions.assertEquals(GET, res.method);
                }
                if (operation.equals(CRUD.POST)) {
                    res = ClientRequest.post(URL, "");
                    // make sure protocols and methods are correct
                    Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
                    Assertions.assertEquals(POST, res.method);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // run server && client request
        try {
            // start server
            serverThread.start();

            // make client query
            clientThread.start();

            // stop threads
            clientThread.join();
            app.shutdown(); // shutdown server before stopping thread
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
