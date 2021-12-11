package ru.mipt.bit.platformer.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressCalculatorTest {

    @Test
    void continueProgress() {
        var progressCalculator = new ProgressCalculator();
        assertEquals(0.5, progressCalculator.continueProgress(0, 0.3f, 0.6f));
    }
}
