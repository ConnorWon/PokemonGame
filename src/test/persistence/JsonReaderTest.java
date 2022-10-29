package persistence;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
            assertTrue(user.getTeam().isEmpty());
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
            assertEquals(3, user.getTeam().size());
            Pokemon pikachu = user.getTeam().get(0);
            checkNotFullMovesPikachu(pikachu);

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
            assertEquals(3, user.getTeam().size());
            Pokemon pikachu = user.getTeam().get(0);
            checkNotFullMovesPikachu(pikachu);

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
            checkNotFullMovesPikachu(pikachu);

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
            checkFullMovesPikachu(pikachu);

            Pokemon charmander = pokedex.getUsablePokemon().get(1);
            checkFullMovesCharmander(charmander);
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
            assertEquals(3, usablePokemon.size());

            Pokemon pikachu = pokedex.getUsablePokemon().get(0);
            checkNotFullMovesPikachu(pikachu);

            Pokemon charmander = pokedex.getUsablePokemon().get(1);
            checkPokemon("Charmander", "Fire", 39, 52, 43, charmander);
            checkMove("Flamethrower", 90, 15, 100, charmander.getMoveSet().get(0));

            Pokemon squirtle = pokedex.getUsablePokemon().get(2);
            checkPokemon("Squirtle", "Water", 50, 51, 52, squirtle);
            checkMove("Water Gun", 35, 30, 100, squirtle.getMoveSet().get(0));
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

}
