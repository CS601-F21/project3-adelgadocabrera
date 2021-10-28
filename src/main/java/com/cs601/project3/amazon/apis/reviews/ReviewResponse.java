package com.cs601.project3.amazon.apis.reviews;

import com.cs601.project3.amazon.models.Review;
import com.cs601.project3.server.views.Html;

import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {
    private static final String form = """
                        <form class="input-wrapper" action="/reviewsearch" method="post">
                            <label for="term" class="label">Search</label>
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

    private static String startContainer = """
            <div class="container">
            """;
    private static String endContainer = """
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

    private static String reviewCard(Review review) {
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
                        </span>
                        """ + review.getSummary() + """
                </p>
                <p>
                """
                + review.getReviewText() +
                """
                            They look good and stick good! I just don't like the rounded shape because I was always bumping it and
                            Siri kept popping up and it was irritating. I just won't buy a product like this agai
                        </p>
                        <p class="date">
                        """ + review.getReviewTime() + """
                    </p>
                </div>
                   """;
    }


    private static ArrayList<Review> getReviewsFromStrings(List<String> stringifiedReviews) {
        ArrayList<Review> reviews = new ArrayList<>();
        for (String review : stringifiedReviews) {
            String EOP = Review.EOP;
            String[] lines = review.split(EOP);

            String reviewerName = "";
            int overall = 0;
            String summary = "";
            String reviewText = "";
            String reviewTime = "";

            for (String line : lines) {
                String[] property = line.split("=");
                String label = property[0];
                String value = property[1];
                if (label.equals("reviewerName")) reviewerName = value;
                if (label.equals("overall")) overall = Integer.parseInt(value);
                if (label.equals("summary")) summary = value;
                if (label.equals("reviewText")) reviewText = value;
                if (label.equals("reviewTime")) reviewTime = value;
            }

            reviews.add(new Review(reviewerName, overall, summary, reviewText, reviewTime));
        }
        return reviews;
    }

}

