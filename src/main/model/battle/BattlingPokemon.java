package model.battle;

import model.pokedex.Move;
import model.pokedex.Pokemon;

import java.util.ArrayList;

// TODO 7: create a class for pokemon that are used in battle
// have to look up info like how stats and damage are calculated for battles
// Represents a Pokemon being used to battle
// Calculates the damage being produced during battle
// * may have to expand more on the above description
public class BattlingPokemon {

    String name;
    String type;
    int hp;
    int atk;
    int def;
    ArrayList<Move> moveSet = new ArrayList<>();

    // EFFECTS: constructs a battling Pokemon using the info from the given Pokemon
    public BattlingPokemon(Pokemon p) {
    }

    // EFFECTS: returns the damage output by the Pokemon
    public int damageOutput() {
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: calculates the damage taken based on the number given and changes the Pokemon's HP accordingly
    public void damageTaken() {
    }

    // TODO 8: include a method for damage output and damage incoming

}
