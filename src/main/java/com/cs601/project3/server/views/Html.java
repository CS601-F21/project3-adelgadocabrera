package com.cs601.project3.server.views;

public class Html {
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
