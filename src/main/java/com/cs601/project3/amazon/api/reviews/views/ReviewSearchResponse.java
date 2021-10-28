package com.cs601.project3.amazon.api.reviews.views;

import com.cs601.project3.amazon.models.Review;
import com.cs601.project3.server.views.Html;

import java.util.ArrayList;
import java.util.List;

import static com.cs601.project3.amazon.api.reviews.views.ReviewsQAsParser.getReviewsFromStrings;

public class ReviewSearchResponse {
    private static final String form = """
                        <form class="input-wrapper" action="/reviewsearch" method="post">
                            <label for="term" class="label">Search Reviews</label>
                            <input type="text" name="query" class="input" />
                            <button type="submit" class="search-button">Search</button>
                        </form>
            """;
    private static final String hero = """
            <div class="container">
                    <h1>Amazon Reviews</h1>
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

    public static String heroWithResults(List<String> stringifiedReviews) {
        ArrayList<Review> reviews = getReviewsFromStrings(stringifiedReviews);
        StringBuilder body = new StringBuilder();
        body.append(hero);
        body.append(startContainer);
        for (Review review : reviews) {
            body.append(reviewCard(review));
        }
        body.append(endContainer);

        return Html.build(Styles.css, body.toString());
    }

    public static String reviewCard(Review review) {
        return """
                <div class="card">
                    <label class="label">
                    """
                + review.getReviewerName() +
                """
                        </label>
                        <p><span class="stars">
                        """
                + review.getOverall() +
                """
                        /5 </span>
                        """ + review.getSummary() + """
                </p>
                <p>
                """
                + review.getReviewText() +
                """
                        </p>
                        <p class="date">
                        """ + review.getReviewTime() + """
                    </p>
                </div>
                   """;
    }
}

