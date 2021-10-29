package com.cs601.project3.amazon.api.reviews.views;

import com.cs601.project3.amazon.models.QA;
import com.cs601.project3.amazon.models.Review;
import com.cs601.project3.server.views.Html;

import java.util.ArrayList;
import java.util.List;

import static com.cs601.project3.amazon.api.reviews.views.ReviewSearchResponse.reviewCard;
import static com.cs601.project3.amazon.api.reviews.views.ReviewsQAsParser.getQAsFromStrings;
import static com.cs601.project3.amazon.api.reviews.views.ReviewsQAsParser.getReviewsFromStrings;

public class FindResponse {
    private static final String form = """
                        <form class="input-wrapper" action="/find" method="post">
                            <label for="term" class="label">Search ASIN</label>
                            <input type="text" name="asin" class="input" />
                            <button type="submit" class="search-button">Search</button>
                        </form>
            """;
    private static final String hero = """
            <div class="container">
                    <h1>Amazon Reviews and QA</h1>
                    """ + form + """
            </div>
            """;

    private static final String startContainer = """
            <div class="container">
            """;
    private static final String endContainer = """
            </div>
            """;

    public static String hero() {
        return Html.build(Styles.css, hero);
    }

    public static String heroWithResults(List<String> stringifiedReviews, List<String> stringifiedQAs) {
        ArrayList<Review> reviews = getReviewsFromStrings(stringifiedReviews);
        ArrayList<QA> qas = getQAsFromStrings(stringifiedQAs);
        StringBuilder body = new StringBuilder();
        body.append(hero);
        body.append(startContainer);
        for (Review review : reviews) {
            body.append(reviewCard(review));
        }
        for (QA qa : qas) {
            body.append(qaCard(qa));
        }
        body.append(endContainer);

        return Html.build(Styles.css, body.toString());
    }

    private static String qaCard(QA qa) {
        return """
                <div class="card">
                    <label class="label">
                    """
                + qa.getQuestion() +
                """
                        </label>
                            <p>
                            """
                + qa.getAnswer() +
                """
                        </p>
                        <p class="date">
                        """ + qa.getAnswerTime() + """
                    </p>
                </div>
                   """;
    }
}
