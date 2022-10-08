package model.pokedex;

import java.util.ArrayList;

// Represents all the Pokemon available to be used in battle
public class Pokedex {

    ArrayList<Pokemon> usablePokemon;

    // EFFECTS: constructs a Pokedex with 0 Pokemon within it
    public Pokedex() {
        usablePokemon = new ArrayList<>();
    }

    public ArrayList<Pokemon> getUsablePokemon() {
        return usablePokemon;
    }

    // MODIFIES: this
    // EFFECTS: adds a Pokemon to the Pokedex of usable Pokemon
    public void addPokemonToPokedex(Pokemon p) {
        usablePokemon.add(p);
    }

    // MODIFIES: this
    // EFFECTS: adds a set of internally built Pokemon to usablePokemon
    private void createPreBuiltPokemon(Pokedex p) {
//        Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 30);
//        pikachu.addMoveToMoveList("Thunderbolt", 90, 15);
//        pikachu.addMoveToMoveList("Iron Tail", 100, 15);
//        pikachu.addMoveToMoveList("Volt Tackle", 120, 15);
//        pikachu.addMoveToMoveList("Quick Attack", 40, 30);
//
//
//        Pokemon charmander = new Pokemon();
//        Pokemon squirtle = new Pokemon();
//        Pokemon bulbasaur = new Pokemon();
//        Pokemon eevee = new Pokemon();

    }
}
