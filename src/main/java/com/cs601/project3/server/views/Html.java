package com.cs601.project3.server.views;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Creates and html template
 */
public class Html {
    /**
     * HTML template with given body
     *
     * @param body
     * @return
     */
    public static String build(String body) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                        <title>adelgadocabrera</title>
                    </head>
                    <body>""" +
                body + """
                    </body>
                </html>
                """;
    }

    /**
     * Html template with given body and given css properties
     *
     * @param style
     * @param body
     * @return
     */
    public static String build(String style, String body) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                        <title>adelgadocabrera</title>
                           <style>
                        """
                + style +
                """
                            </style>
                        </head>
                                <body>""" +
                body + """
                </body>
                </html>
                """;

    }
}
