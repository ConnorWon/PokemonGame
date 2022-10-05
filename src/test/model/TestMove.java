package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMove {

    Move move;

    @BeforeEach
    public void runBefore() {
        move = new Move("Tackle", 40, 35, 100);
    }

    @Test
    public void testConstructor() {
        assertEquals("Tackle", move.getName());
        assertEquals(40, move.getPower());
        assertEquals(35, move.getPp());
        assertEquals(100, move.getAccuracy());
    }

}
