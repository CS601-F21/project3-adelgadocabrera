package com.cs601.project3.amazon.api.reviewsQA.views;

import com.cs601.project3.amazon.models.QA;
import com.cs601.project3.amazon.models.Review;
import com.cs601.project3.server.views.Html;

import java.util.ArrayList;
import java.util.List;

import static com.cs601.project3.amazon.api.reviewsQA.views.ReviewsQAsParser.getQAsFromStrings;
import static com.cs601.project3.amazon.api.reviewsQA.views.ReviewsQAsParser.getReviewsFromStrings;

/**
 * @author Alberto Delgado Cabrera
 * <p>
 * Html response for FindAsin handler
 */
public class FindAsinResponse {
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

    /**
     * Returns HTML containing A title, search bar and a button: aka HERO
     *
     * @return
     */
    public static String hero() {
        return Html.build(Styles.css, hero);
    }

    /**
     * Returns the HERO with the search results
     *
     * @param stringifiedReviews
     * @param stringifiedQAs
     * @return
     */
    public static String heroWithResults(List<String> stringifiedReviews, List<String> stringifiedQAs) {
        ArrayList<Review> reviews = getReviewsFromStrings(stringifiedReviews);
        ArrayList<QA> qas = getQAsFromStrings(stringifiedQAs);
        StringBuilder body = new StringBuilder();
        body.append(hero);
        body.append(Common.startContainer);

        for (Review review : reviews) {
            body.append(Common.reviewCard(review));
        }

        for (QA qa : qas) {
            body.append(Common.qaCard(qa));
        }

        body.append(Common.endContainer);

        return Html.build(Styles.css, body.toString());
    }

}
