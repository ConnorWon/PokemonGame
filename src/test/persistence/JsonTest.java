package persistence;

import model.pokedex.Move;
import model.pokedex.Pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Based on the supplied Workroom example for CPSC 210
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// provides useful methods for the Json test classes
public class JsonTest {

    // EFFECTS: checks to see if Pokemon p has the correct info
    protected void checkPokemon(String name, String type, int hp, int atk, int def, Pokemon p) {
        assertEquals(name, p.getName());
        assertEquals(type, p.getType());
        assertEquals(hp, p.getHP());
        assertEquals(atk, p.getAtk());
        assertEquals(def, p.getDef());
    }

    // EFFECTS: checks to see if Move m has the correct info
    protected void checkMove(String name, int power, int pp, int accuracy, Move m) {
        assertEquals(name, m.getName());
        assertEquals(power, m.getPower());
        assertEquals(pp, m.getPP());
        assertEquals(accuracy, m.getAccuracy());
    }

    // REQUIRES: only checks the test Pikachu with not a full move set
    // EFFECTS: checks to see if the Pikachu's info is all correct
    protected void checkNotFullMovesPikachu(Pokemon pikachu) {
        checkPokemon("Pikachu", "Electric", 35, 55, 30, pikachu);
        checkMove("Thunderbolt", 90, 15, 100, pikachu.getMoveSet().get(0));
        checkMove("Iron Tail", 100, 15, 75, pikachu.getMoveSet().get(1));
        checkMove("Volt Tackle", 120, 15, 100, pikachu.getMoveSet().get(2));
    }

    // REQUIRES: only checks the test Pikachu with a full move set
    // EFFECTS: checks to see if the Pikachu's info is all correct
    protected void checkFullMovesPikachu(Pokemon pikachu) {
        checkPokemon("Pikachu", "Electric", 35, 55, 30, pikachu);
        checkMove("Thunderbolt", 90, 15, 100, pikachu.getMoveSet().get(0));
        checkMove("Iron Tail", 100, 15, 75, pikachu.getMoveSet().get(1));
        checkMove("Volt Tackle", 120, 15, 100, pikachu.getMoveSet().get(2));
        checkMove("Quick Attack", 40, 30, 100, pikachu.getMoveSet().get(3));
    }

    // REQUIRES: only checks the test Charmander with a full move set
    // EFFECTS: checks to see if the Charmander's info is all correct
    protected void checkFullMovesCharmander(Pokemon charmander) {
        checkPokemon("Charmander", "Fire", 39, 52, 43, charmander);
        checkMove("Flamethrower", 90, 15, 100, charmander.getMoveSet().get(0));
        checkMove("Fire Spin", 35, 15, 85, charmander.getMoveSet().get(1));
        checkMove("Dragon Breath", 60, 20, 100, charmander.getMoveSet().get(2));
        checkMove("Slash", 70, 20, 100,charmander.getMoveSet().get(3));
    }

}
