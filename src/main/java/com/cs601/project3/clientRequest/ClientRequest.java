package com.cs601.project3.clientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Makes a HTTP request.
 * Supported methos are GET, POST and PUT
 */
public class ClientRequest {
    /**
     * Makes a GET request
     *
     * @param href
     * @return
     * @throws IOException
     */
    public static ClientResponse get(String href) throws IOException {
        HttpURLConnection con = connect("GET", href);
        con.connect();
        return response(con);
    }

    /**
     * Makes a POST request
     *
     * @param href
     * @param data
     * @return
     * @throws IOException
     */
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

    /**
     * Makes a post request with headers
     *
     * @param href
     * @param data
     * @param headers
     * @return
     * @throws IOException
     */
    public static ClientResponse post(String href, String data, List<String> headers) throws IOException {
        HttpURLConnection con = connect("POST", href);

        // expecting headers to come in format <header>:<value>
        for (String header : headers) {
            String[] headerParts = header.split(":");
            con.setRequestProperty(headerParts[0].trim(), headerParts[1].trim());
        }
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        con.connect();
        return response(con);
    }

    /**
     * Makes a PUT request
     *
     * @param href
     * @param data
     * @return
     * @throws IOException
     */
    public static ClientResponse put(String href, String data) throws IOException {
        HttpURLConnection con = connect("PUT", href);
        con.setDoOutput(true);
        con.connect();
        return response(con);
    }

    /**
     * Helper method to avoid repetition in HTTP requests.
     * Sets the URL and establishes connection
     *
     * @param operation
     * @param href
     * @return
     * @throws IOException
     */
    private static HttpURLConnection connect(String operation, String href) throws IOException {
        URL url = new URL(href); //create URL object
        HttpURLConnection con = (HttpURLConnection) url.openConnection(); //create secure connection
        con.setRequestProperty("Connection", "close"); // close connection upon finishing request
        con.setRequestMethod(operation); // set HTTP method

        return con;
    }

    /**
     * Generates HTTP response and returns it in the form of ClientResponse
     *
     * @param con
     * @return
     * @throws IOException
     */
    private static ClientResponse response(HttpURLConnection con) throws IOException {
        // prepare request response
        final String method = con.getRequestMethod();
        final int responseCode = con.getResponseCode();
        final List<String> requestLine = con.getHeaderFields().get(null);
        final String protocol = requestLine.get(0).split(" ")[0];

        final StringBuilder response = new StringBuilder();

        // create body for error scenario
        if ((con.getResponseCode() < 200) || (con.getResponseCode() >= 300)) {
            final BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            in.close();

            return new ClientResponse(responseCode, response.toString(), method, protocol);
        }

        // creates body of successful request
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
