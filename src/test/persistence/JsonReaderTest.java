package persistence;

import model.battle.BattlingPokemon;
import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Based on the supplied Workroom example for CPSC 210
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// tests methods in the JsonReader class
public class JsonReaderTest extends JsonTest{

    @Test
    public void testTrainerReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonexistent.json");
        try {
            Trainer user = reader.readForTrainer();
            fail("Supposed to throw IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testTrainerReaderEmptyTeam() {
        JsonReader reader = new JsonReader("./data/testTrainerReaderEmptyTeam.json");
        try {
            Trainer user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    public void testTrainerReaderTeamPresent() {
        JsonReader reader = new JsonReader("./data/testTrainerReaderTeamPresent.json");
        try {
            Trainer user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
            Pokemon pikachu = user.getTeam().get(0);
            checkPokemon("Pikachu", "Electric", 35, 55, 30, pikachu);
            checkMove("Thunderbolt", 90, 15, 100, pikachu.getMoveSet().get(0));
            checkMove("Iron Tail", 100, 15, 75, pikachu.getMoveSet().get(1));
            checkMove("Volt Tackle", 120, 15, 100, pikachu.getMoveSet().get(2));

            Pokemon charmander = user.getTeam().get(1);
            checkPokemon("Charmander", "Fire", 39, 52, 43, charmander);
            checkMove("Flamethrower", 90, 15, 100, charmander.getMoveSet().get(0));

            Pokemon squirtle = user.getTeam().get(2);
            checkPokemon("Squirtle", "Water", 50, 51, 52, squirtle);
            checkMove("Water Gun", 35, 30, 100, squirtle.getMoveSet().get(0));
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    public void testTrainerReaderPokedexPresent() {
        JsonReader reader = new JsonReader("./data/testReaderPokedexAndTrainerPresent.json");
        try {
            Trainer user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    public void testPokedexReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonexistent.json");
        try {
            Pokedex pokedex = reader.readForPokedex();
            fail("Supposed to throw IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testPokedexReaderEmptyPokedex() {
        JsonReader reader = new JsonReader("./data/testPokedexReaderEmptyPokedex.json");
        try {
            Pokedex pokedex = reader.readForPokedex();
            assertEquals(0, pokedex.getUsablePokemon().size());
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    public void testPokedexReaderPokemonPresentEmptyMoveSet() {
        JsonReader reader = new JsonReader("./data/testPokedexReaderPokemonPresentEmptyMoveSet.json");
        try {
            Pokedex pokedex = reader.readForPokedex();
            ArrayList<Pokemon> usablePokemon = pokedex.getUsablePokemon();
            assertEquals(2, usablePokemon.size());
            checkPokemon("Pikachu", "Electric", 35, 55, 30, usablePokemon.get(0));
            assertEquals(0, usablePokemon.get(0).getMoveSet().size());
            checkPokemon("Charmander", "Fire", 39, 52, 43, usablePokemon.get(1));
            assertEquals(0, usablePokemon.get(1).getMoveSet().size());
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    public void testPokedexReaderPokemonPresentUnevenMoveSet() {
        JsonReader reader = new JsonReader("./data/testPokedexReaderPokemonPresentUnevenMoveSet.json");
        try {
            Pokedex pokedex = reader.readForPokedex();
            ArrayList<Pokemon> usablePokemon = pokedex.getUsablePokemon();
            assertEquals(2, usablePokemon.size());

            Pokemon pikachu = pokedex.getUsablePokemon().get(0);
            checkPokemon("Pikachu", "Electric", 35, 55, 30, pikachu);
            checkMove("Thunderbolt", 90, 15, 100, pikachu.getMoveSet().get(0));
            checkMove("Iron Tail", 100, 15, 75, pikachu.getMoveSet().get(1));
            checkMove("Volt Tackle", 120, 15, 100, pikachu.getMoveSet().get(2));

            Pokemon charmander = pokedex.getUsablePokemon().get(1);
            checkPokemon("Charmander", "Fire", 39, 52, 43, charmander);
            checkMove("Flamethrower", 90, 15, 100, charmander.getMoveSet().get(0));
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    public void testPokedexReaderPokemonPresentFullMoveSet() {
        JsonReader reader = new JsonReader("./data/testPokedexReaderPokemonPresentFullMoveSet.json");
        try {
            Pokedex pokedex = reader.readForPokedex();
            ArrayList<Pokemon> usablePokemon = pokedex.getUsablePokemon();
            assertEquals(2, usablePokemon.size());

            Pokemon pikachu = pokedex.getUsablePokemon().get(0);
            checkPokemon("Pikachu", "Electric", 35, 55, 30, pikachu);
            checkMove("Thunderbolt", 90, 15, 100, pikachu.getMoveSet().get(0));
            checkMove("Iron Tail", 100, 15, 75, pikachu.getMoveSet().get(1));
            checkMove("Volt Tackle", 120, 15, 100, pikachu.getMoveSet().get(2));
            checkMove("Quick Attack", 40, 30, 100, pikachu.getMoveSet().get(3));

            Pokemon charmander = pokedex.getUsablePokemon().get(1);
            checkPokemon("Charmander", "Fire", 39, 52, 43, charmander);
            checkMove("Flamethrower", 90, 15, 100, charmander.getMoveSet().get(0));
            checkMove("Fire Spin", 35, 15, 85, charmander.getMoveSet().get(1));
            checkMove("Dragon Breath", 60, 20, 100, charmander.getMoveSet().get(2));
            checkMove("Slash", 70, 20, 100,charmander.getMoveSet().get(3));
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    public void testPokedexReaderTrainerPresent() {
        JsonReader reader = new JsonReader("./data/testReaderPokedexAndTrainerPresent.json");
        try {
            Pokedex pokedex = reader.readForPokedex();
            ArrayList<Pokemon> usablePokemon = pokedex.getUsablePokemon();
            assertEquals(2, usablePokemon.size());

            Pokemon pikachu = pokedex.getUsablePokemon().get(0);
            checkPokemon("Pikachu", "Electric", 35, 55, 30, pikachu);
            checkMove("Thunderbolt", 90, 15, 100, pikachu.getMoveSet().get(0));
            checkMove("Iron Tail", 100, 15, 75, pikachu.getMoveSet().get(1));
            checkMove("Volt Tackle", 120, 15, 100, pikachu.getMoveSet().get(2));

            Pokemon charmander = pokedex.getUsablePokemon().get(1);
            checkPokemon("Charmander", "Fire", 39, 52, 43, charmander);
            checkMove("Flamethrower", 90, 15, 100, charmander.getMoveSet().get(0));
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

}