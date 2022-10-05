package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

// Represents a Pokemon with a name, list of moves, and attack, defense, and health points stats
public class Pokemon {

    private String name;
    private String type;
    private int hp;
    private int atk;
    private int def;
    private ArrayList<Move> moveSet = new ArrayList<>();

    // REQUIRES: name != "", hp & atk & def > 0
    // EFFECTS: constructs a Pokemon with the given name, type, hp, atk, and def, and an empty move set
    public Pokemon(String name, String type, int hp, int atk, int def) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public void setHp(int hp) {
//        this.hp = hp;
//    }
//
//    public void setAtk(int atk) {
//        this.atk = atk;
//    }
//
//    public void setDef(int def) {
//        this.def = def;
//    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHp() {
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

    // REQUIRES: power > 0, name cannot equal ""
    // MODIFIES: this
    // EFFECTS: adds a move to the Pokemon's move set
    public void addMoveToMoveSet(String name, int power, int pp, int accuracy) {
        if (moveSet.size() < 4) {
            Move move = new Move(name, power, pp, accuracy);
            moveSet.add(move);
        }
    }
}
