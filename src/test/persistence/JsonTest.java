package persistence;

import model.pokedex.Move;
import model.pokedex.Pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Based on the supplied Workroom example for CPSC 210
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {

    protected void checkPokemon(String name, String type, int hp, int atk, int def, Pokemon p) {
        assertEquals(name, p.getName());
        assertEquals(type, p.getType());
        assertEquals(hp, p.getHP());
        assertEquals(atk, p.getAtk());
        assertEquals(def, p.getDef());
    }

    protected void checkMove(String name, int power, int pp, int accuracy, Move m) {
        assertEquals(name, m.getName());
        assertEquals(power, m.getPower());
        assertEquals(pp, m.getPP());
        assertEquals(accuracy, m.getAccuracy());
    }

}
