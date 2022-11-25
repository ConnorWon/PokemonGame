package model.pokedex;

import model.event.Event;
import model.event.EventLog;
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
        EventLog.getInstance().logEvent(new Event("Pokemon " + p.getName() + " added to the Pokedex"));
        usablePokemon.add(p);
    }

    // MODIFIES: this
    // EFFECTS: returns a list of Pokemon from the Pokedex filtered for a specific typing
    public ArrayList<Pokemon> filterPokedex(String type) {
        ArrayList<Pokemon> availablePokemon = new ArrayList<>();
        if (type.equals("None")) {
            EventLog.getInstance().logEvent(new Event("Pokedex filtered for no specific Pokemon type"));
            for (Pokemon p : usablePokemon) {
                availablePokemon.add(p);
            }
        } else {
            EventLog.getInstance().logEvent(new Event("Pokedex filtered for " + type + " type Pokemon"));
            for (Pokemon p : usablePokemon) {
                if (p.getType().equals(type)) {
                    availablePokemon.add(p);
                }
            }
        }
        return availablePokemon;
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

