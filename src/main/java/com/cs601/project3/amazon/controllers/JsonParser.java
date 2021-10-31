package com.cs601.project3.amazon.controllers;

import com.cs601.project3.amazon.models.Doc;
import com.cs601.project3.amazon.models.JsonParserResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Author: Alberto Delgado
 * <p>
 * Parses JSON files and creates a T type object from them
 *
 * @param <T>
 */
public class JsonParser<T extends Doc> {
    /**
     * Reads line by line and parses the string into json
     *
     * @param fileName
     * @return
     */
    public static <T extends Doc> JsonParserResponse<T> parse(Class<T> classType, String fileName, int maxNumberOfItems) {
        Charset charset = StandardCharsets.ISO_8859_1;
        int counter = 0;
        Gson gson = new Gson();
        ArrayList<T> store = new ArrayList<>();

        try (BufferedReader b = Files.newBufferedReader(Paths.get(fileName), charset)) {
            System.out.println("Reading file: " + fileName + "... may take some time");

            String line = b.readLine();
            while (line != null && counter < maxNumberOfItems) {
                counter++;
                T element = gson.fromJson(line, classType);
                if (element != null) store.add(element);
                line = b.readLine();
            }

            return JsonParserResponse.successful(store);
        } catch (IOException e) {
            return JsonParserResponse.failed("\nSomething went wrong reading file " + fileName);
        }
    }
}
