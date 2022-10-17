package model.pokedex;

import java.util.ArrayList;

// Represents a list of all the Pokemon available to be used in battle
public class Pokedex {

    private ArrayList<Pokemon> usablePokemon;

    // EFFECTS: constructs a Pokedex with 0 Pokemon within it
    public Pokedex() {
        usablePokemon = new ArrayList<>();
    }

    public ArrayList<Pokemon> getUsablePokemon() {
        return usablePokemon;
    }

    // MODIFIES: this
    // EFFECTS: adds a Pokemon to the Pokedex
    public void addPokemonToPokedex(Pokemon p) {
        usablePokemon.add(p);
    }
}

