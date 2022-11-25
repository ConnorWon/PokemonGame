package model;

import model.event.Event;
import model.event.EventLog;
import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

// tests methods in the Pokedex class
class PokedexTest {

    private Pokedex pokedex;
    private Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 50, 40);
    private Pokemon charmander = new Pokemon("Charmander", "Fire", 25, 45, 35);

    @BeforeEach
    public void runBefore() {
        pokedex = new Pokedex();
        EventLog el = EventLog.getInstance();
        el.clear();
    }

    @Test
    public void testConstructor() {
        assertTrue(pokedex.getUsablePokemon().isEmpty());
    }

    @Test
    public void testAddPokemonToPokedex() {
        pokedex.addPokemonToPokedex(pikachu);
        Pokemon actualPikachu = pokedex.getUsablePokemon().get(0);
        assertEquals("Pikachu", actualPikachu.getName());
        assertEquals("Electric", actualPikachu.getType());
        assertEquals(35, actualPikachu.getHP());
        assertEquals(50,actualPikachu.getAtk());
        assertEquals(40, actualPikachu.getDef());
        assertTrue(actualPikachu.getMoveSet().isEmpty());
    }

    @Test
    public void testAddPokemonToPokedexMultipleTimes() {
        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);

        assertEquals(2, pokedex.getUsablePokemon().size());

        Pokemon actualPikachu = pokedex.getUsablePokemon().get(0);
        assertEquals("Pikachu", actualPikachu.getName());
        assertEquals("Electric", actualPikachu.getType());
        assertEquals(35, actualPikachu.getHP());
        assertEquals(50,actualPikachu.getAtk());
        assertEquals(40, actualPikachu.getDef());
        assertTrue(actualPikachu.getMoveSet().isEmpty());


        Pokemon actualCharmander = pokedex.getUsablePokemon().get(1);
        assertEquals("Charmander", actualCharmander.getName());
        assertEquals("Fire", actualCharmander.getType());
        assertEquals(25, actualCharmander.getHP());
        assertEquals(45,actualCharmander.getAtk());
        assertEquals(35, actualCharmander.getDef());
        assertTrue(actualCharmander.getMoveSet().isEmpty());
    }

    @Test
    public void testAddPokemonEventLogging() {
        pokedex.addPokemonToPokedex(pikachu);
        EventLog el = EventLog.getInstance();
        Iterator<Event> itr = el.iterator();
        itr.next();

        assertTrue(itr.hasNext());
        assertEquals("Pokemon Pikachu added to the Pokedex", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testFilterPokedexTypeNone() {
        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);

        assertEquals(pokedex.getUsablePokemon(), pokedex.filterPokedex("None"));
    }

    @Test
    public void testFilterPokedexActualType() {
        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);

        ArrayList<Pokemon> testList = new ArrayList<>();
        testList.add(charmander);

        assertEquals(testList, pokedex.filterPokedex("Fire"));
    }

    @Test
    public void testFilterPokedexNoPokemonHasType() {
        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);

        assertTrue(pokedex.filterPokedex("Water").isEmpty());
    }

    @Test
    public void testFilterPokedexMultipleTimes() {
        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);

        ArrayList<Pokemon> testList1 = new ArrayList<>();
        testList1.add(charmander);

        ArrayList<Pokemon> testList2 = new ArrayList<>();
        testList2.add(pikachu);

        assertEquals(testList1, pokedex.filterPokedex("Fire"));
        assertEquals(testList2, pokedex.filterPokedex("Electric"));
        assertEquals(pokedex.getUsablePokemon(), pokedex.filterPokedex("None"));
        assertTrue(pokedex.filterPokedex("Water").isEmpty());
    }

    @Test
    public void testFilterPokedexEventLogging() {
        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);
        pokedex.filterPokedex("Fire");
        pokedex.filterPokedex("Water");

        EventLog el = EventLog.getInstance();
        Iterator<Event> itr = el.iterator();
        itr.next();

        assertTrue(itr.hasNext());
        assertEquals("Pokemon Pikachu added to the Pokedex", itr.next().getDescription());
        assertEquals("Pokemon Charmander added to the Pokedex", itr.next().getDescription());
        assertEquals("Pokedex filtered for Fire type Pokemon", itr.next().getDescription());
        assertEquals("Pokedex filtered for Water type Pokemon", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

}