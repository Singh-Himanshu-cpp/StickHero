package com.example.stickhero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {
    @Test
    public void testResetScore() {
        Score.resetScore();
        assertEquals(Score.getScore(), 0);
    }

}