package com.cs601.project3.amazon.api.reviewsQA.views;

import com.cs601.project3.amazon.models.QA;
import com.cs601.project3.amazon.models.Review;
import org.apache.commons.text.StringEscapeUtils;

/**
 * Common html items
 */
public class Common {
    static final String startContainer = """
            <div class="container">
            """;
    static final String endContainer = """
            </div>
            """;

    /**
     * Creates a HTML card for a given QA
     *
     * @param qa
     * @return
     */
    static String qaCard(QA qa) {
        return """
                <div class="card">
                    <label class="label">
                    """
                + StringEscapeUtils.escapeHtml3(qa.getQuestion()) +
                """
                        </label>
                            <p>
                            """
                + StringEscapeUtils.escapeHtml3(qa.getAnswer()) +
                """
                        </p>
                        <p class="date">
                        """ + qa.getAnswerTime() + """
                    </p>
                </div>
                   """;
    }

    /**
     * Creates a HTML card for a given review
     *
     * @param review
     * @return
     */
    static String reviewCard(Review review) {
        return """
                <div class="card">
                    <label class="label">
                    """
                + StringEscapeUtils.escapeHtml3(review.getReviewerName()) +
                """
                        </label>
                        <p><span class="stars">
                        """
                + review.getOverall() +
                """
                        /5 </span><span class="summary">
                        """ + StringEscapeUtils.escapeHtml3(review.getSummary()) + """
                </span></p>
                <p>
                """
                + StringEscapeUtils.escapeHtml3(review.getReviewText()) +
                """
                        </p>
                        <p class="date">
                        """ + review.getReviewTime() + """
                    </p>
                </div>
                   """;
    }
}
