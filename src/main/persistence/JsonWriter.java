package persistence;

import model.pokedex.Pokedex;
import model.trainers.Trainer;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Based on the supplied Workroom example for CPSC 210
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes JSON representation of Pokedex and user's trainer to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Pokedex and the user's trainer to file
    public void write(Pokedex p, Trainer user) {
        JSONObject json = combine(p, user);
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // EFFECTS: combines the JSON representation of Pokedex and the user's trainer into one JSON representation and
    //          returns that
    private JSONObject combine(Pokedex p, Trainer user) {
        JSONObject json = new JSONObject();
        json.put("pokedex", p.toJson());
        json.put("user", user.toJson());
        return json;
    }

}
