package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.*;
import com.cs601.project3.server.views.Html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Alberto Delgado
 * <p>
 * Server is runnable but not necessarily has to run in separate thread.
 * It is made runnable, so it CAN be run in separate thread.
 */
public class Server implements Runnable {
    // server properties
    private volatile boolean running = true;
    private ServerSocket server;
    private Router router = new Router();

    // threads
    private final int THREAD_POOL_TIMEOUT = 30; // in seconds
    private final int THREAD_POOL_SIZE = 10;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It is possible to create a Router manually instead of using
     * the one created by default in the server.
     * <p>
     * By adding the external router it will completely replace
     * the default one. This is expected behavior.
     *
     * @param router
     */
    public void use(Router router) {
        this.router = router;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket sock = server.accept();
                threadPool.execute(() -> serverLogic(sock));
            } catch (IOException e) {
                // this will give an exception when
                // the server is shutdown because it will
                // interrupt sock.accept()
            }
        }
    }

    public void get(String path, HttpHandler callback) {
        router.createHandler(CRUD.GET, path, callback);
    }

    public void post(String path, HttpHandler callback) {
        router.createHandler(CRUD.POST, path, callback);
    }


    public void shutdown() {
        running = false;
        new Thread(() -> {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(THREAD_POOL_TIMEOUT, TimeUnit.SECONDS)) {
                    System.err.println("Thread didn't finish in " + THREAD_POOL_TIMEOUT + " seconds");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void serverLogic(Socket sock) {
        try (
                BufferedReader inStream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                OutputStream outStream = sock.getOutputStream()
        ) {
            // Create Request && Response objects
            Response response = new Response(outStream);
            Request request;
            try {
                request = getRequest(inStream);
            } catch (IllegalAccessException e) {
                String errorResponse = Html.build(e.getMessage());
                response.status(e.getMessage()).send(errorResponse);
                return;
            }

            // Only accept GET/POST
            if (!isValidCRUDRequest(request)) {
                String notAllowedResponse = Html.build(HttpStatus.NOT_ALLOWED);
                response.status(HttpStatus.NOT_ALLOWED).send(notAllowedResponse);
                return;
            }

            // Get Handler for specified path
            EndpointHandlers endpointHandlers = getHandler(request.getPath());

            // No path exists
            if (endpointHandlers == null) {
                String pathNotFoundResponse = Html.build(HttpStatus.NOT_FOUND);
                response.status(HttpStatus.NOT_FOUND).send(pathNotFoundResponse);
                return;
            }

            // Execute handler callback in thread
            HttpHandler callback = getCallback(request.getOperation(), endpointHandlers);
            if (callback != null) {
                callback.handle(request, response);
            } else {
                // The path exists but there is no callback defined for that endpoint
                String pathNotFoundResponse = Html.build(HttpStatus.NOT_FOUND);
                response.status(HttpStatus.NOT_FOUND).send(pathNotFoundResponse);
            }

            // close socket
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Request getRequest(BufferedReader inStream) throws IOException, IllegalAccessException {
        // Get request line
        StringBuilder headers = new StringBuilder();
        String requestLine = inStream.readLine();

        // Get headers
        String line = inStream.readLine();
        while (line != null && !line.trim().isEmpty()) {
            headers.append(line).append("\n");
            line = inStream.readLine();
        }

        // Get body
        StringBuilder body = new StringBuilder();
        while (inStream.ready()) {
            body.append((char) inStream.read());
        }

        // Parse Request
        return RequestParser.get(requestLine, headers.toString(), body.toString());
    }

    private HttpHandler getCallback(CRUD operation, EndpointHandlers endpointHandlers) {
        if (operation.equals(CRUD.GET)) return endpointHandlers.getGETCallback();
        if (operation.equals(CRUD.POST)) return endpointHandlers.getPOSTCallback();
        return null;
    }

    private EndpointHandlers getHandler(String path) {
        return router.routes.get(path);
    }

    private boolean isValidCRUDRequest(Request request) {
        CRUD operation = request.getOperation();
        return operation.equals(CRUD.GET) || operation.equals(CRUD.POST);
    }
}