package com.cs601.project3.slack.api.bot.views;

import com.cs601.project3.server.views.Html;

public class PostResponse {
    static final String start = """
            <div class="container">
                <div class="response">
                    """;
    static final String end = """
                    </div>
                </div>
            """;

    public static String response(String msg) {
        StringBuilder body = new StringBuilder();
        body.append(Form.hero());
        body.append(start);
        body.append(msg);
        body.append(end);
        return Html.build(Styles.css, Html.build(body.toString()));
    }
}
