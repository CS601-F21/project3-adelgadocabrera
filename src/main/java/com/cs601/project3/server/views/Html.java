package com.cs601.project3.server.views;

public class Html {
    public static String build(String body){
       return """
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
        "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <title>Title of document</title>
            </head>
            <body>""" +
               body + """
            </body>
        </html>
        """;
    }
}
