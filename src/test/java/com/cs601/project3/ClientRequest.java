package com.cs601.project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientRequest {
    public static String fetch(String target) throws IOException {
        //create URL object
        URL url = new URL(target);

        //create secure connection
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //set HTTP method
        con.setRequestMethod("GET");

        con.connect();
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return (response.toString());
        } else {
            return ("GET request not worked");
        }
    }
}
