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

// tests methods in the JsonWriter class
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
            addNotFullMovesPikachu(pikachu);
            charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
            pokedex.addPokemonToPokedex(charmander);

            JsonWriter writer = new JsonWriter("./data/testWriterTrainerAndPokedexPokemonUnevenMoveSet.json");
            writer.open();
            writer.write(pokedex, user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterTrainerAndPokedexPokemonUnevenMoveSet.json");

            pokedex = reader.readForPokedex();
            assertEquals(2, pokedex.getUsablePokemon().size());
            checkNotFullMovesPikachu(pokedex.getUsablePokemon().get(0));
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
            addFullMovesPikachu(pikachu);
            addFullMovesCharmander(charmander);

            JsonWriter writer = new JsonWriter("./data/testWriterTrainerAndPokedexPokemonFullMoveSet.json");
            writer.open();
            writer.write(pokedex, user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterTrainerAndPokedexPokemonFullMoveSet.json");

            pokedex = reader.readForPokedex();
            assertEquals(2, pokedex.getUsablePokemon().size());
            checkFullMovesPikachu(pokedex.getUsablePokemon().get(0));
            checkFullMovesCharmander(pokedex.getUsablePokemon().get(1));

            user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
        } catch (IOException e) {
            fail("Not expected");
        }
    }

    @Test
    public void testWriterTrainerTeamPresentAndPokedex() {
        try {
            addFullMovesPikachu(pikachu);
            addFullMovesCharmander(charmander);
            Pokemon squirtle = new Pokemon("Squirtle", "Water", 50, 51, 52);
            addFullMovesSquirtle(squirtle);

            user.addTeamMember(pikachu);
            user.addTeamMember(charmander);
            user.addTeamMember(squirtle);

            JsonWriter writer = new JsonWriter("./data/testWriterTrainerTeamPresentAndPokedex.json");
            writer.open();
            writer.write(pokedex, user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterTrainerTeamPresentAndPokedex.json");

            pokedex = reader.readForPokedex();
            assertEquals(3, pokedex.getUsablePokemon().size());
            checkFullMovesPikachu(pokedex.getUsablePokemon().get(0));
            checkFullMovesCharmander(pokedex.getUsablePokemon().get(1));
            checkFullMovesSquirtle(pokedex.getUsablePokemon().get(2));

            user = reader.readForTrainer();
            assertEquals("testUser", user.getName());
            assertEquals(3, user.getTeam().size());
            checkFullMovesPikachu(user.getTeam().get(0));
            checkFullMovesCharmander(user.getTeam().get(1));
            checkFullMovesSquirtle(user.getTeam().get(2));
        } catch (IOException e) {
            fail("Not expected");
        }
    }

    // REQUIRES: only checks the test Squirtle with a full move set
    // EFFECTS: checks to see if the Squirtle's info is all correct
    private void checkFullMovesSquirtle(Pokemon squirtle) {
        checkPokemon("Squirtle", "Water", 50, 51, 52, squirtle);
        checkMove("Surf", 90, 15, 100, squirtle.getMoveSet().get(0));
        checkMove("Water Gun", 35, 30, 100, squirtle.getMoveSet().get(1));
        checkMove("Rapid Spin", 30, 40, 100, squirtle.getMoveSet().get(2));
        checkMove("Water Pulse", 60, 20, 100, squirtle.getMoveSet().get(3));
    }

    // MODIFIES: this
    // EFFECTS: adds a full move set to Pikachu and then adds Pikachu to Pokedex
    private void addFullMovesPikachu(Pokemon pikachu) {
        pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        pokedex.addPokemonToPokedex(pikachu);
    }

    // MODIFIES: this
    // EFFECTS: adds a full move set to Charmander and then adds Charmander to Pokedex
    private void addFullMovesCharmander(Pokemon charmander) {
        charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
        charmander.addMoveToMoveSet("Fire Spin", 35, 15, 85);
        charmander.addMoveToMoveSet("Dragon Breath", 60, 20, 100);
        charmander.addMoveToMoveSet("Slash", 70, 20, 100);
        pokedex.addPokemonToPokedex(charmander);
    }

    // MODIFIES: squirtle, this
    // EFFECTS: adds a full move set to Squirtle and then adds Squirtle to Pokedex
    private void addFullMovesSquirtle(Pokemon squirtle) {
        squirtle.addMoveToMoveSet("Surf", 90, 15, 100);
        squirtle.addMoveToMoveSet("Water Gun", 35, 30, 100);
        squirtle.addMoveToMoveSet("Rapid Spin", 30, 40, 100);
        squirtle.addMoveToMoveSet("Water Pulse", 60, 20, 100);
        pokedex.addPokemonToPokedex(squirtle);
    }

    // MODIFIES: this
    // EFFECTS: adds a not completely full move set to Pikachu and then adds Pikachu to Pokedex
    private void addNotFullMovesPikachu(Pokemon pikachu) {
        pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
        pokedex.addPokemonToPokedex(pikachu);
    }

}
