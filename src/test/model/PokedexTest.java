package model;

import model.pokedex.Move;
import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PokedexTest {

    Pokedex pokedex;
    ArrayList<Pokemon> pokeList = new ArrayList<>();
    Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 50, 40);
    Pokemon charmander = new Pokemon("Charmander", "Fire", 25, 45, 35);
    ArrayList<Move> moves = new ArrayList<>();


    @BeforeEach
    public void runBefore() {
        pokedex = new Pokedex();
    }

    @Test
    public void testConstructor() {
        assertEquals(pokeList, pokedex.getUsablePokemon());
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
        assertEquals(moves, actualPikachu.getMoveSet());
    }

    @Test
    public void testAddPokemonToPokedexMultipleTimes() {
        pokedex.addPokemonToPokedex(pikachu);
        Pokemon actualPikachu = pokedex.getUsablePokemon().get(0);
        assertEquals("Pikachu", actualPikachu.getName());
        assertEquals("Electric", actualPikachu.getType());
        assertEquals(35, actualPikachu.getHP());
        assertEquals(50,actualPikachu.getAtk());
        assertEquals(40, actualPikachu.getDef());
        assertEquals(moves, actualPikachu.getMoveSet());
        pokedex.addPokemonToPokedex(charmander);
        Pokemon actualCharmander = pokedex.getUsablePokemon().get(1);
        assertEquals("Charmander", actualCharmander.getName());
        assertEquals("Fire", actualCharmander.getType());
        assertEquals(25, actualCharmander.getHP());
        assertEquals(45,actualCharmander.getAtk());
        assertEquals(35, actualCharmander.getDef());
        assertEquals(moves, actualCharmander.getMoveSet());
    }

}