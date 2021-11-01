package com.cs601.project3.amazon.api.reviewsQA.views;

import com.cs601.project3.amazon.models.Review;
import com.cs601.project3.server.views.Html;

import java.util.ArrayList;
import java.util.List;

import static com.cs601.project3.amazon.api.reviewsQA.views.ReviewsQAsParser.getReviewsFromStrings;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Builds an HTML response for the ReviewSearch handler
 */
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

    /**
     * Creates a title, search bar and input, AKA HERO
     *
     * @return
     */
    public static String hero() {
        return Html.build(Styles.css, hero);
    }

    /**
     * Returns the hero with reviews search results
     *
     * @param stringifiedReviews
     * @return
     */
    public static String heroWithResults(List<String> stringifiedReviews) {
        ArrayList<Review> reviews = getReviewsFromStrings(stringifiedReviews);
        StringBuilder body = new StringBuilder();
        body.append(hero);
        body.append(Common.startContainer);
        for (Review review : reviews) {
            body.append(Common.reviewCard(review));
        }
        body.append(Common.endContainer);

        return Html.build(Styles.css, body.toString());
    }
}

