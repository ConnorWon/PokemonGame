package model.pokedex;

import java.util.ArrayList;

// Represents a Pokemon with a name, type, list of moves, and attack, defense, and health points stat
public class Pokemon {

    private String name;
    private String type;
    private int hp;
    private int atk;
    private int def;
    private ArrayList<Move> moveSet;

    // REQUIRES: name & type != "", hp & atk & def > 0
    // EFFECTS: constructs a Pokemon with the given name, type, hp, atk, and def, and an empty move set
    public Pokemon(String name, String type, int hp, int atk, int def) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        moveSet = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHP() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public ArrayList<Move> getMoveSet() {
        return moveSet;
    }

    // REQUIRES: power & accuracy & pp > 0, name != ""
    // MODIFIES: this
    // EFFECTS: adds a move to the Pokemon's move set
    public void addMoveToMoveSet(String name, int power, int pp, int accuracy) {
        if (moveSet.size() < 4) {
            Move move = new Move(name, power, pp, accuracy);
            moveSet.add(move);
        }
    }
}
