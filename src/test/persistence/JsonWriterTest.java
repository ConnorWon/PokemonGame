package persistence;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Based on the supplied Workroom example for CPSC 210
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest{

    Pokemon pikachu;
    Pokemon charmander;
    Pokedex pokedex;
    Trainer user;

    @BeforeEach
    public void runBefore() {
        pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 30);
        charmander = new Pokemon("Charmander", "Fire", 39, 52, 43);
        pokedex = new Pokedex();
        user = new Trainer("testUser");
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName");
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    public void testWriterTrainerAndEmptyPokedex() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterTrainerAndEmptyPokedex.json");
            writer.open();
            writer.write(pokedex, user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTrainerAndEmptyPokedex.json");

            pokedex = reader.readForPokedex();
            assertEquals(0, pokedex.getUsablePokemon().size());

            user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
        } catch (IOException e) {
            fail("Not expected");
        }
    }

    @Test
    public void testWriterTrainerAndPokedexPokemonEmptyMoveSet() {
        try {
            pokedex.addPokemonToPokedex(pikachu);
            pokedex.addPokemonToPokedex(charmander);
            JsonWriter writer = new JsonWriter("./data/testWriterTrainerAndPokedexPokemonEmptyMoveSet.json");
            writer.open();
            writer.write(pokedex, user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTrainerAndPokedexPokemonEmptyMoveSet.json");

            pokedex = reader.readForPokedex();
            ArrayList<Pokemon> usablePokemon = pokedex.getUsablePokemon();
            assertEquals(2, usablePokemon.size());
            checkPokemon("Pikachu", "Electric", 35, 55, 30, usablePokemon.get(0));
            assertEquals(0, usablePokemon.get(0).getMoveSet().size());
            checkPokemon("Charmander", "Fire", 39, 52, 43, usablePokemon.get(1));
            assertEquals(0, usablePokemon.get(1).getMoveSet().size());

            user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
        } catch (IOException e) {
            fail("Not expected");
        }
    }

    @Test
    public void testWriterTrainerAndPokedexPokemonUnevenMoveSet() {
        try {
            pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
            pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
            pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
            pokedex.addPokemonToPokedex(pikachu);
            charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
            pokedex.addPokemonToPokedex(charmander);
            JsonWriter writer = new JsonWriter("./data/testWriterTrainerAndPokedexPokemonUnevenMoveSet.json");
            writer.open();
            writer.write(pokedex, user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTrainerAndPokedexPokemonUnevenMoveSet.json");

            pokedex = reader.readForPokedex();
            ArrayList<Pokemon> usablePokemon = pokedex.getUsablePokemon();
            assertEquals(2, usablePokemon.size());

            Pokemon checkerPikachu = pokedex.getUsablePokemon().get(0);
            checkPokemon("Pikachu", "Electric", 35, 55, 30, checkerPikachu);
            checkMove("Thunderbolt", 90, 15, 100, checkerPikachu.getMoveSet().get(0));
            checkMove("Iron Tail", 100, 15, 75, checkerPikachu.getMoveSet().get(1));
            checkMove("Volt Tackle", 120, 15, 100, checkerPikachu.getMoveSet().get(2));

            Pokemon checkerCharmander = pokedex.getUsablePokemon().get(1);
            checkPokemon("Charmander", "Fire", 39, 52, 43, checkerCharmander);
            checkMove("Flamethrower", 90, 15, 100, checkerCharmander.getMoveSet().get(0));

            user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
        } catch (IOException e) {
            fail("Not expected");
        }
    }

    @Test
    public void testWriterTrainerAndPokedexPokemonFullMoveSet() {
        try {
            pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
            pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
            pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
            pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
            pokedex.addPokemonToPokedex(pikachu);
            charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
            charmander.addMoveToMoveSet("Fire Spin", 35, 15, 85);
            charmander.addMoveToMoveSet("Dragon Breath", 60, 20, 100);
            charmander.addMoveToMoveSet("Slash", 70, 20, 100);
            pokedex.addPokemonToPokedex(charmander);
            JsonWriter writer = new JsonWriter("./data/testWriterTrainerAndPokedexPokemonFullMoveSet.json");
            writer.open();
            writer.write(pokedex, user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTrainerAndPokedexPokemonFullMoveSet.json");

            pokedex = reader.readForPokedex();
            ArrayList<Pokemon> usablePokemon = pokedex.getUsablePokemon();
            assertEquals(2, usablePokemon.size());

            Pokemon checkerPikachu = pokedex.getUsablePokemon().get(0);
            checkPokemon("Pikachu", "Electric", 35, 55, 30, checkerPikachu);
            checkMove("Thunderbolt", 90, 15, 100, checkerPikachu.getMoveSet().get(0));
            checkMove("Iron Tail", 100, 15, 75, checkerPikachu.getMoveSet().get(1));
            checkMove("Volt Tackle", 120, 15, 100, checkerPikachu.getMoveSet().get(2));
            checkMove("Quick Attack", 40, 30, 100, checkerPikachu.getMoveSet().get(3));

            Pokemon checkerCharmander = pokedex.getUsablePokemon().get(1);
            checkPokemon("Charmander", "Fire", 39, 52, 43, checkerCharmander);
            checkMove("Flamethrower", 90, 15, 100, checkerCharmander.getMoveSet().get(0));
            checkMove("Fire Spin", 35, 15, 85, checkerCharmander.getMoveSet().get(1));
            checkMove("Dragon Breath", 60, 20, 100, checkerCharmander.getMoveSet().get(2));
            checkMove("Slash", 70, 20, 100,checkerCharmander.getMoveSet().get(3));

            user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
        } catch (IOException e) {
            fail("Not expected");
        }
    }

}
