package model.battle;

import model.pokedex.Move;
import model.pokedex.Pokemon;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Random;

// Represents a Pokemon ready to be used in battle
public class BattlingPokemon {

    private String name;
    private String type;
    private int hp;
    private int atk;
    private int def;
    private ArrayList<Move> moveSet;
    private int hitChance;
    private int damageRoll;

    // EFFECTS: constructs a battle ready Pokemon using the info from the given Pokemon
    public BattlingPokemon(Pokemon p) {
        name = p.getName();
        type = p.getType();
        hp = 110 + 2 * p.getHP();
        atk = 5 + 2 * p.getAtk();
        def = 5 + 2 * p.getDef();
        moveSet = new ArrayList<>();
        constructMoveSet(p.getMoveSet());
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

    public int getHitChance() {
        return hitChance;
    }

    public int getDamageRoll() {
        return damageRoll;
    }

    // MODIFIES: this
    // EFFECTS: sets hitChance to a random number in [1, 100]
    public void setHitChance() {
        // reference: https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
        Random rand = new Random();
        this.hitChance = 1 + rand.nextInt(100);
    }

    // MODIFIES: this
    // EFFECTS: sets damageRoll to a random number in [85, 100]
    public void setDamageRoll() {
        // reference: https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
        Random rand = new Random();
        this.damageRoll = 85 + rand.nextInt(16);
    }

    // EFFECTS: returns the damage output by the Pokemon based on its move m and the Pokemon its targeting
    public int damageOutput(Move m, BattlingPokemon target) {
        setDamageRoll();
        setHitChance();
        m.usedMove();

        if (getHitChance() <= m.getAccuracy()) {
            // reference: https://stackoverflow.com/questions/6468730/converting-double-to-integer-in-java
            return (int) Math.round(((42.0 * m.getPower() * getAtk() / target.getDef()) / 50.0 + 2)
                    * getDamageRoll() / 100.0);
        } else {
            return 0;
        }
    }

    // REQUIRES: damage >= 0
    // MODIFIES: this
    // EFFECTS: processes the damage a Pokemon has taken
    public void damageTaken(int damage) {
        if (damage > this.hp) {
            this.hp = 0;
        } else {
            this.hp -= damage;
        }
    }

    // REQUIRES: moveSet to not be empty
    // MODIFIES: this
    // EFFECTS: constructs the move set for the battling Pokemon
    private void constructMoveSet(ArrayList<Move> moveSet) {
        for (Move m : moveSet) {
            Move move = new Move(m.getName(), m.getPower(), m.getPP(),m.getAccuracy());
            this.moveSet.add(move);
        }
    }

    // REQUIRES: every move in moveSet has PP 0
    // MODIFIES: this
    // EFFECTS: calculates the damage output by the Pokemon using the move struggle based on target Pokemon. Also
    //          calculates the recoil damage the Pokemon using struggle takes
    public int struggle(BattlingPokemon target) {
        setDamageRoll();
        // reference: https://stackoverflow.com/questions/6468730/converting-double-to-integer-in-java
        int damage = (int) Math.round(((42.0 * 50 * getAtk() / target.getDef()) / 50.0 + 2) * getDamageRoll() / 100.0);
        int recoil = damage / 2;
        damageTaken(recoil);

        return damage;
    }

}
