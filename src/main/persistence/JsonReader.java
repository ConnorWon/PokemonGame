package persistence;

import model.pokedex.Move;
import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Based on the supplied Workroom example for CPSC 210
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// TODO: implement reading user's team from file
// represents a reader that reads the Pokedex and user's Trainer from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Pokedex from file and returns it; throws IOException if an error occurs reading data from file
    public Pokedex readForPokedex() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePokedex(jsonObject);
    }

    // EFFECTS: reads user's trainer from file and returns it; throws IOException if an error occurs reading data from
    //          file
    public Trainer readForTrainer() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTrainer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Pokedex from JSON object and returns it
    private Pokedex parsePokedex(JSONObject jsonObject) {
        Pokedex pokedex = new Pokedex();
        JSONObject jsonPokedex = jsonObject.getJSONObject("pokedex");
        fillPokedex(pokedex, jsonPokedex);
        return pokedex;
    }

    // MODIFIES: p
    // EFFECTS: parses the list of Pokemon from JSON object and adds them to the Pokedex
    private void fillPokedex(Pokedex p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pokemon");
        for (Object json : jsonArray) {
            JSONObject pokemonJson = (JSONObject) json;
            Pokemon pokemon = addPokemon(pokemonJson);
            p.addPokemonToPokedex(pokemon);
        }
    }

    // EFFECTS: parses a Pokemon from JSON object and returns it
    private Pokemon addPokemon(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int hp = jsonObject.getInt("hp");
        int atk = jsonObject.getInt("atk");
        int def = jsonObject.getInt("def");
        Pokemon pokemon = new Pokemon(name, type, hp, atk, def);

        JSONArray jsonArray = jsonObject.getJSONArray("moves");
        for (Object json : jsonArray) {
            JSONObject move = (JSONObject) json;
            addMove(pokemon, move);
        }

        return pokemon;
    }

    // MODIFIES: p
    // EFFECTS: parses a Move from JSON object and adds it to Pokemon
    private void addMove(Pokemon p, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int power = jsonObject.getInt("power");
        int pp = jsonObject.getInt("pp");
        int accuracy = jsonObject.getInt("accuracy");
        p.addMoveToMoveSet(name, power, pp, accuracy);
    }

    // EFFECTS: parses user's trainer from JSON object and returns it
    private Trainer parseTrainer(JSONObject jsonObject) {
        JSONObject jsonUser = jsonObject.getJSONObject("user");
        String name = jsonUser.getString("name");
        Trainer user = new Trainer(name);
        fillTeam(user, jsonUser);
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses the saved Pokemon team from JSON object and adds them back to the user's team
    private void fillTeam(Trainer user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("team");
        for (Object json : jsonArray) {
            JSONObject bp = (JSONObject) json;
            Pokemon pokemon = addPokemon(bp);
            user.addTeamMember(pokemon);
        }
    }

}
