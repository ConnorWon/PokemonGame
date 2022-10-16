package model;

import model.pokedex.Move;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonTest {

    Pokemon pikachu;

    @BeforeEach
    public void runBefore() {
        pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 50);
    }

    @Test
    public void testConstructor() {
        assertEquals("Pikachu", pikachu.getName());
        assertEquals("Electric", pikachu.getType());
        assertEquals(35, pikachu.getHP());
        assertEquals(55, pikachu.getAtk());
        assertEquals(50, pikachu.getDef());
        assertTrue(pikachu.getMoveSet().isEmpty());
    }

    @Test
    public void testAddMoveToMoveSet() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        Move move = pikachu.getMoveSet().get(0);

        assertEquals("Thunderbolt", move.getName());
        assertEquals(95, move.getPower());
        assertEquals(15, move.getPP());
        assertEquals(100, move.getAccuracy());
        assertEquals(1, pikachu.getMoveSet().size());
    }

    @Test
    public void testAddMoveToMoveSetMultipleTimes() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        Move move = pikachu.getMoveSet().get(0);

        assertEquals("Thunderbolt", move.getName());
        assertEquals(95, move.getPower());
        assertEquals(15, move.getPP());
        assertEquals(100, move.getAccuracy());
        assertEquals(1, pikachu.getMoveSet().size());

        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        Move moveTwo = pikachu.getMoveSet().get(1);

        assertEquals("Quick Attack", moveTwo.getName());
        assertEquals(40, moveTwo.getPower());
        assertEquals(30, moveTwo.getPP());
        assertEquals(100, moveTwo.getAccuracy());
        assertEquals(2, pikachu.getMoveSet().size());
    }

    @Test
    public void testAddMoveToMoveSetMoreThanFourTimes() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        pikachu.addMoveToMoveSet("Tackle", 40, 35, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);

        Move move1 = pikachu.getMoveSet().get(0);
        Move move2 = pikachu.getMoveSet().get(1);
        Move move3 = pikachu.getMoveSet().get(2);
        Move move4 = pikachu.getMoveSet().get(3);

        assertEquals(4, pikachu.getMoveSet().size());

        assertEquals("Thunderbolt", move1.getName());
        assertEquals(95, move1.getPower());
        assertEquals(15, move1.getPP());
        assertEquals(100, move1.getAccuracy());

        assertEquals("Quick Attack", move2.getName());
        assertEquals(40, move2.getPower());
        assertEquals(30, move2.getPP());
        assertEquals(100, move2.getAccuracy());

        assertEquals("Tackle", move3.getName());
        assertEquals(40, move3.getPower());
        assertEquals(35, move3.getPP());
        assertEquals(100, move3.getAccuracy());

        assertEquals("Iron Tail", move4.getName());
        assertEquals(100, move4.getPower());
        assertEquals(15, move4.getPP());
        assertEquals(75, move4.getAccuracy());
    }
}
