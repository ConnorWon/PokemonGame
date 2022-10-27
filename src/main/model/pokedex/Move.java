package model.pokedex;

import org.json.JSONObject;
import persistence.Writable;

// Represents a single move of a Pokemon. Has a name, power, pp (amount of times a move can be used), and move accuracy
public class Move implements Writable {

    private String name;
    private int power;
    private int pp;
    private int accuracy;

    // REQUIRES: power & accuracy & pp > 0, name != ""
    // EFFECTS: creates a move with the given name, power, pp, and accuracy; stores the original pp value
    public Move(String name, int power, int pp, int accuracy) {
        this.name = name;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getPP() {
        return pp;
    }

    public int getAccuracy() {
        return accuracy;
    }

    // REQUIRES: pp > 0
    // MODIFIES: this
    // EFFECTS: decreases move's pp by 1
    public void usedMove() {
        pp -= 1;
    }

    // Based on the supplied Workroom example for CPSC 210
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("power", power);
        json.put("pp", pp);
        json.put("accuracy", accuracy);
        return json;
    }
}
