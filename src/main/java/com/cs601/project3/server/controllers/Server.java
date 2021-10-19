package com.cs601.project3.server.controllers;

import com.cs601.project3.server.models.Router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket server;
    Router router;
    final static String EOT = "EOT";
    static volatile boolean running = true;

    public Server(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void use(Router router) throws IllegalAccessException {
        if (router != null) throw new IllegalAccessException("Your server already has a router");
        this.router = router;
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

    public void run() {
        while (running) {
            //try with resources ensures socket will be closed
            try (
                    //block on accept until a new client connects
                    Socket sock = server.accept();
                    //wrap the socket input stream in a BufferedReader
                    BufferedReader instream = new BufferedReader(new InputStreamReader(sock.getInputStream()))
            ) {

                //initialize the message
                String message = "";
                //read the first line
                String line = instream.readLine();

                //keep reading until end of transmission
                while (line != null && !line.trim().equals(EOT)) {
                    //append to message
                    message += line + "\n";
                    //read next line
                    line = instream.readLine();
                }

                //display client message
                System.out.println("Client says: " + message);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }
}