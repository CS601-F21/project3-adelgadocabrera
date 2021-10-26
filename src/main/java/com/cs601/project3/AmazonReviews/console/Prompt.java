package com.cs601.project3.AmazonReviews.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Handles the command line prompts to keep the prompts
 * consistent
 */
public class Prompt {
    private static final int maxNumberOfCharsToRender = 300;

    /**
     * Simple prompt to read one line
     *
     * @param out
     * @return
     * @throws IOException
     */
    public static String print(String out) throws IOException {
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println(out);
        System.out.print("> ");
        return reader.readLine().trim();
    }

    /**
     * Renders multiple lines and then prompts for user input
     *
     * @param out
     * @return
     * @throws IOException
     */
    public static String print(ArrayList<String> out) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        for (String o : out) System.out.println(o);
        System.out.print("> ");
        return reader.readLine().trim();
    }

    /**
     * Formats output string to a shorter version so it doesn't
     * bloat the terminal
     *
     * @param str
     * @return
     */
    public static String shortString(String str) {
        StringBuilder string = new StringBuilder();
        string.append("\n * ");
        int counter = 0;

        for (int i = 0; i < str.length(); i++) {
            if (counter > maxNumberOfCharsToRender) break;
            counter++;
            string.append(str.charAt(i));
        }

        if (str.length() > maxNumberOfCharsToRender) {
            string.append("...");
        }

        return String.valueOf(string);
    }
}
