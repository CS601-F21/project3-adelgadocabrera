package com.cs601.project3.amazon.api.reviewsQA;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelpersTest {

    @Test
    @DisplayName("should return false because payload is empty")
    void emptyBody() {
        Assertions.assertFalse(Helpers.payloadHasQuery("", "something"));
    }

    @Test
    @DisplayName("should return bad request because missing flag")
    void missingFlag() {
        Assertions.assertFalse(Helpers.payloadHasQuery("=", "something"));
    }

    @Test
    @DisplayName("should return false because missing equal symbol")
    void missingEquals() {
        Assertions.assertFalse(Helpers.payloadHasQuery("query: test", "query"));
    }

    @Test
    @DisplayName("should return true for good formation")
    void assertsTrue() {
        Assertions.assertTrue(Helpers.payloadHasQuery("query=test", "query"));
    }
}