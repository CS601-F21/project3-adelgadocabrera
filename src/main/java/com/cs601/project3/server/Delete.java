package com.cs601.project3.server;

class Delete {
    // create server example
    public static void main(String[] args) {
        Server server = new Server();

        // logic in handler creation
        server.get("/", (req, res) -> {
            System.out.println("helloWorld!\n");
        });

        // logic extracted to method
        server.get("/user/create", (req, res) -> CreateUser.handle(req, res));
        server.get("/user/create/alternative", CreateUser::handle);

        // logic extracted to class
        server.get("/user/email", UserApi.addEmail);
        server.get("/user/username", UserApi.updateUsername);
    }

    // server
    public static class Server {
        // create "GET" endpoint
        public void get(String path, HttpLambdaHandler handler) {
            System.out.println("\nServer new path: " + path);
            System.out.println("Executing handler: ");

            String req = "This is your request!";
            String res = "This is your response!";

            handler.handle(req, res);
        }

        public void get(String path, HttpHandler handler) {
            System.out.println("\nServer new path: " + path);
            System.out.println("Executing handler: ");

            String req = "This is your request!";
            String res = "This is your response!";

            handler.handle(req, res);
        }
    }

    // group handler
    public static class UserApi {

        public static AddEmail addEmail = new AddEmail();
        public static UpdateUsername updateUsername = new UpdateUsername();

        public static class AddEmail extends HttpHandler {
            public void handle(String req, String res) {
                System.out.println("Creating email");
                System.out.println("Req " + req);
                System.out.println("Res " + res);
            }
        }

        public static class UpdateUsername extends HttpHandler {
            public void handle(String req, String res) {
                System.out.println("Changing username");
                System.out.println("Req " + req);
                System.out.println("Res " + res);
            }
        }
    }

    // handler
    public static class CreateUser {
        public static void handle(String req, String res) {
            // Now I can do whatever I want with the
            // req and res objects

            System.out.println("Running handler");
            System.out.println("Request: " + req);
            System.out.println("Response: " + res);
        }

    }

    public static abstract class HttpHandler {
        abstract void handle(String req, String res);
    }

    // handles http request
    @FunctionalInterface
    public interface HttpLambdaHandler {
        void handle(String req, String res);
    }
}

