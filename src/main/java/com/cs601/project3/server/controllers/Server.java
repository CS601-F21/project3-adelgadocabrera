package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.*;
import com.cs601.project3.server.views.Html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket server;
    Router router = null;
    static volatile boolean running = true;
    private final String PROHIBITED_CRUD_ERROR = "Only GET and POST operations are allowed";
    private final String ADD_ROUTER_ERROR = "Your server already has a router";

    public Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void use(Router router) throws IllegalAccessException {
        if (this.router != null) throw new IllegalAccessException(ADD_ROUTER_ERROR);
        this.router = router;
    }

    public void run() throws IllegalAccessException {
        while (running) {
            try (
                    Socket sock = server.accept();
                    BufferedReader instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    OutputStream outputStream = sock.getOutputStream()
            ) {
                // Read request
                StringBuilder headers = new StringBuilder();
                String requestLine = instream.readLine();

                String line = instream.readLine();
                while (line != null && !line.trim().isEmpty()) {
                    headers.append(line).append("\n");
                    line = instream.readLine();
                }

                // Parse Request
                Request request = RequestParser.get(requestLine, headers.toString());
                if (!isValidCRUDRequest(request)) {
                    throw new IllegalAccessException(PROHIBITED_CRUD_ERROR);
                }

                // Get Handler
                Handler handler = getHandler(request.getPath());

                // Handle PATH NOT FOUND
                if (handler == null) {
                    String response = Html.build("<h1>Oops! Something went wrong!</h1>");
                    outputStream.write(new Response(HTTPHeader.HTTP_NOT_FOUND, response).getBytes());
                    continue;
                }

                // Execute handler callback in thread
                Thread t = new Thread(getCallback(request.getOperation(), handler));
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String body = "<h1>Alberto is on fire!<h2>";
                Response response = new Response(HTTPHeader.HTTP_OK, Html.build(body));
                outputStream.write(response.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Runnable getCallback(CRUD operation, Handler handler) {
        if (operation.equals(CRUD.GET)) return handler.getGETCallback();
        if (operation.equals(CRUD.POST)) return handler.getPOSTCallback();
        return null;
    }

    private Handler getHandler(String path) {
        return router.routes.get(path);
    }

    private boolean isValidCRUDRequest(Request request) {
        CRUD operation = request.getOperation();
        return operation.equals(CRUD.GET) || operation.equals(CRUD.POST);
    }

    // idea to implement middleware
    // currently not required, but could be interesting
    //    public void use(Runnable middleware) {
    //
    //    }

    // to add routes directly to server
    // would have to handle conflicts with router
    // currently not required
    //    public void use(CRUD operation, String path, Runnable callback){ try {
    //            router.createHandler(operation, path, callback);
    //        } catch(IllegalAccessException e){
    //            e.printStackTrace();
    //        }
    //    }

}