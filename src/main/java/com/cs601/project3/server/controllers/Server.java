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
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    // error messages
    final String OPERATION_NOT_ALLOWED_ERROR = "Only GET and POST operations are allowed";
    final String PATH_NOT_FOUND_ERROR = "<h3>Path does not exist.</h3>";

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
                System.err.println("Server closed!");
            }
        }
    }

    public void get(String path, HttpLambdaHandler callback) {
        router.createHandler(CRUD.GET, path, callback);
    }

    public void get(String path, HttpHandler callback) {
        router.createHandler(CRUD.GET, path, callback);
    }

    public void post(String path, HttpLambdaHandler callback) {
        router.createHandler(CRUD.POST, path, callback);
    }

    public void post(String path, HttpHandler callback) {
        router.createHandler(CRUD.POST, path, callback);
    }


    public void shutdown() throws InterruptedException, IOException {
        running = false;
        threadPool.shutdown();
        if (!threadPool.awaitTermination(THREAD_POOL_TIMEOUT, TimeUnit.SECONDS)) {
            System.err.println("Thread didn't finish in " + THREAD_POOL_TIMEOUT + " seconds");
        }
        server.close();
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
                System.out.println(e.getMessage());
                String body = Html.build(e.getMessage());
                response.status(e.getMessage()).send(body);
                return;
            }

            // Only accept GET/POST
            if (!isValidCRUDRequest(request)) {
                String notAllowedResponse = Html.build(OPERATION_NOT_ALLOWED_ERROR);
                response.status(HttpHeader.NOT_ALLOWED).send(notAllowedResponse);
                return;
            }

            // Get Handler for specified path
            Handler handler = getHandler(request.getPath());

            // Handle PATH NOT FOUND
            if (handler == null) {
                String pathNotFoundResponse = Html.build(PATH_NOT_FOUND_ERROR);
                response.status(HttpHeader.NOT_FOUND).send(pathNotFoundResponse);
                return;
            }

            // Execute handler callback in thread
            HttpLambdaHandler callback = getCallback(request.getOperation(), handler);
            if (callback != null) {
                callback.handle(request, response);
            } else {
                String pathNotFoundResponse = Html.build(PATH_NOT_FOUND_ERROR);
                response.status(HttpHeader.NOT_FOUND).send(pathNotFoundResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Request getRequest(BufferedReader instream) throws IOException, IllegalAccessException {
        // Get request line
        StringBuilder headers = new StringBuilder();
        String requestLine = instream.readLine();

        // Get headers
        String line = instream.readLine();
        while (line != null && !line.trim().isEmpty()) {
            headers.append(line).append("\n");
            line = instream.readLine();
        }

        // Get body
        StringBuilder body = new StringBuilder();
        while (instream.ready()) {
            body.append((char) instream.read());
        }

        // Parse Request
        return RequestParser.get(requestLine, headers.toString(), body.toString());
    }

    private HttpLambdaHandler getCallback(CRUD operation, Handler handler) {
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
}