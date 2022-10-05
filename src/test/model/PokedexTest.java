package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokedexTest {

    private Pokedex pokedex;

    @BeforeEach
    public void runBefore() {
        Pokedex pokedex = new Pokedex();
    }

    @Test
    public void testConstructor() {
       // assertArrayEquals(<call to data w/ pre-existing pokemon>, pokedex.getUsablePokemonSize());
    }

    @Test
    public void testAddPokemonToPokedex() {
        //
    }

    @Test
    public void testAddPokemonToPokedexMultipleTimes() {
        //
    }

}