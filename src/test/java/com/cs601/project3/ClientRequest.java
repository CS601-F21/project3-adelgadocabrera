package com.cs601.project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ClientRequest {
    public static ClientResponse get(String href) throws IOException {
        HttpURLConnection con = connect("GET", href);
        con.connect();
        return response(con);
    }

    public static ClientResponse post(String href, String data) throws IOException {
        HttpURLConnection con = connect("POST", href);
        con.setRequestProperty("Content-Type", "text/plain");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        con.connect();
        return response(con);
    }

    public static ClientResponse put(String href, String data) throws IOException {
        HttpURLConnection con = connect("PUT", href);
        con.setDoOutput(true);
        con.connect();
        return response(con);
    }

    private static HttpURLConnection connect(String operation, String href) throws IOException {
        //create URL object
        URL url = new URL(href);

        //create secure connection
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // close connection upon finishing request
        con.setRequestProperty("Connection", "close");

        // set HTTP method
        con.setRequestMethod(operation);

        return con;
    }

    private static ClientResponse response(HttpURLConnection con) throws IOException {
        // prepare request response
        final String method = con.getRequestMethod();
        final int responseCode = con.getResponseCode();
        final List<String> requestLine = con.getHeaderFields().get(null);
        final String protocol = requestLine.get(0).split(" ")[0];

        final StringBuilder response = new StringBuilder();

        if ((con.getResponseCode() < 200) || (con.getResponseCode() >= 300)) {
            final BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            in.close();

            return new ClientResponse(responseCode, response.toString(), method, protocol);
        }

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            in.close();

            return new ClientResponse(responseCode, response.toString(), method, protocol);
        }

        String body = "Client request didn't get a response";
        return new ClientResponse(responseCode, body, method, protocol);
    }
}
