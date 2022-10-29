package model.pokedex;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of all the Pokemon available to be used in battle
public class Pokedex implements Writable {

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

    // Based on the supplied Workroom example for CPSC 210
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns the Pokedex as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pokemon", pokemonToJson());
        return json;
    }

    // Based on the supplied Workroom example for CPSC 210
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns Pokemon in Pokedex as a JSON array
    private JSONArray pokemonToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : usablePokemon) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}

