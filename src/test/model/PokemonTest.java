package model;

import model.pokedex.Move;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonTest {

    Pokemon pikachu;
    ArrayList<Move> moves;

    @BeforeEach
    public void runBefore() {
        pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 50);
        moves = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        assertEquals("Pikachu", pikachu.getName());
        assertEquals("Electric", pikachu.getType());
        assertEquals(35, pikachu.getHp());
        assertEquals(55, pikachu.getAtk());
        assertEquals(50, pikachu.getDef());
        assertEquals(moves, pikachu.getMoveSet());
    }

    @Test
    public void testAddMoveToMoveSet() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        Move move = pikachu.getMoveSet().get(0);
        assertEquals("Thunderbolt", move.getName());
        assertEquals(95, move.getPower());
        assertEquals(15, move.getPp());
        assertEquals(100, move.getAccuracy());
        assertEquals(1, pikachu.getMoveSet().size());
    }

    @Test
    public void testAddMoveToMoveSetMultipleTimes() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        Move move = pikachu.getMoveSet().get(0);
        assertEquals("Thunderbolt", move.getName());
        assertEquals(95, move.getPower());
        assertEquals(15, move.getPp());
        assertEquals(100, move.getAccuracy());
        assertEquals(1, pikachu.getMoveSet().size());
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        Move moveTwo = pikachu.getMoveSet().get(1);
        assertEquals("Quick Attack", moveTwo.getName());
        assertEquals(40, moveTwo.getPower());
        assertEquals(30, moveTwo.getPp());
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
        assertEquals(4, pikachu.getMoveSet().size());
    }
}
