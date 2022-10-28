package model;

import model.pokedex.Move;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests methods in the Pokemon class
public class PokemonTest {

    private Pokemon pikachu;

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

        checkMove("Thunderbolt", 95, 15, 100, move);
    }

    @Test
    public void testAddMoveToMoveSetMultipleTimes() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        Move move1 = pikachu.getMoveSet().get(0);
        checkMove("Thunderbolt", 95, 15, 100, move1);

        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        Move move2 = pikachu.getMoveSet().get(1);
        checkMove("Quick Attack", 40, 30, 100, move2);
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

        checkMove("Thunderbolt", 95, 15, 100, move1);
        checkMove("Quick Attack", 40, 30, 100, move2);
        checkMove("Tackle", 40, 35, 100, move3);
        checkMove("Iron Tail", 100, 15, 75, move4);
        assertEquals(4, pikachu.getMoveSet().size());
    }

    // EFFECTS: checks to see if Move m has the correct info
    private void checkMove(String name, int power, int pp, int accuracy, Move m) {
        assertEquals(name, m.getName());
        assertEquals(power, m.getPower());
        assertEquals(pp, m.getPP());
        assertEquals(accuracy, m.getAccuracy());
    }

}
