package com.cs601.project3.slack.api.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelpersTest {

    @Test
    @DisplayName("should return message")
    void getMessage() {
        String flag = "message=";
        String payload = "something this cool = yes, should care about = aa";
        String message = Helpers.getMessage(flag + payload);
        Assertions.assertEquals(payload, message);
    }

    @Test
    @DisplayName("should not return anything")
    void empty() {
        String payload = "";
        Assertions.assertEquals(payload, Helpers.getMessage(payload));
    }

    @Test
    @DisplayName("should return correct value")
    void correct() {
        String flag = "message=";
        String payload = "yes";
        Assertions.assertEquals(payload, Helpers.getMessage(flag + payload));
    }
}