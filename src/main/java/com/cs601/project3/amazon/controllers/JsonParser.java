package com.cs601.project3.amazon.controllers;

import com.cs601.project3.amazon.models.Doc;
import com.cs601.project3.amazon.models.JsonParserResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
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
    private final Class<T> typeParameterClass;
    private final ArrayList<T> store = new ArrayList<>();


    public JsonParser(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    /**
     * Reads line by line and parses the string into json
     *
     * @param fileName
     * @return
     */
    public JsonParserResponse<T> parse(String fileName, int maxNumberOfItems) {
        Charset charset = StandardCharsets.ISO_8859_1;
        int counter = 0;
        try (BufferedReader b = Files.newBufferedReader(Paths.get(fileName), charset)) {
            System.out.println("Reading file: " + fileName + "... may take some time");
            Gson gson = new Gson();
            String line = "";
            boolean isRunning = true;

            while (isRunning && counter < maxNumberOfItems) {
                counter++;
                line = b.readLine();
                if (line == null) {
                    isRunning = false;
                    continue;
                }
                T element = parseJsonToString(gson, line, counter, fileName);
                if (element != null) store.add(element);
            }

            return JsonParserResponse.successful(store);
        } catch (Exception e) {
            return JsonParserResponse.failed("\nSomething went wrong reading file " + fileName);
        }
    }

    /**
     * Parses JSON object
     *
     * @param line
     * @param counter
     * @param fileName
     * @return
     */
    private T parseJsonToString(Gson gson, String line, int counter, String fileName) {
        try {
            return gson.fromJson(line, typeParameterClass);
        } catch (Exception e) {
            System.out.println(fileName + ": JSON object skipped at line " + counter + ". Reason: JSON object is mal-formed");
            return null;
        }
    }
}
