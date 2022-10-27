package model;

import model.pokedex.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests methods in the Move class
public class MoveTest {

    private Move move;

    @BeforeEach
    public void runBefore() {
        move = new Move("Tackle", 40, 35, 100);
    }

    @Test
    public void testConstructor() {
        assertEquals("Tackle", move.getName());
        assertEquals(40, move.getPower());
        assertEquals(35, move.getPP());
        assertEquals(100, move.getAccuracy());
    }

    @Test
    public void testUsedMove() {
        move.usedMove();
        assertEquals(34, move.getPP());
    }

    @Test
    public void testUsedMoveMultipleTimes() {
        move.usedMove();
        assertEquals(34, move.getPP());
        move.usedMove();
        assertEquals(33, move.getPP());
    }

}
