package model.battle;

import model.pokedex.Move;
import model.pokedex.Pokemon;

import java.util.ArrayList;
import java.util.Random;

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
    ArrayList<Move> moveSet;
    int hitChance;
    int damageRoll;

    // EFFECTS: constructs a battling Pokemon using the info from the given Pokemon
    public BattlingPokemon(Pokemon p) {
        this.name = p.getName();
        this.type = p.getType();
        this.hp = 110 + 2 * p.getHP();
        this.atk = 5 + 2 * p.getAtk();
        this.def = 5 + 2 * p.getDef();
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

    // TODO: Q: how to create a test for when i am using random numbers
    // MODIFIES: this
    // EFFECTS: sets hitChance to a random number in [1, 100]
    public void setHitChance() {
        Random rand = new Random();
        this.hitChance = rand.nextInt(100) + 1;
    }

    // TODO: Q: how to create a test for when i am using random numbers
    // MODIFIES: this
    // EFFECTS: sets damageRoll to a random number in [85, 100]
    public void setDamageRoll() {
        Random rand = new Random();
        this.damageRoll = 85 + rand.nextInt(16);
    }

    // EFFECTS: returns the damage output by the Pokemon
    public int damageOutput(Move m, BattlingPokemon target) {
        setDamageRoll();
        setHitChance();

        if (getHitChance() <= m.getAccuracy()) {
            return (int) Math.round(((42.0 * m.getPower() * getAtk() / target.getDef()) / 50.0 + 2)
                    * getDamageRoll() / 100.0);
        } else {
            return 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: calculates the damage taken based on the number given and changes the Pokemon's HP accordingly
    public void damageTaken(int damage) {
        if (damage > this.hp) {
            this.hp = 0;
        } else {
            this.hp -= damage;
        }
    }

    // TODO: Q: this modifies m, but m is apart of the moveSet field of this class, so what do I say it modifies?
    // MODIFIES: m, this
    // EFFECTS: decreases the pp of the move by 1
    public void usedMove(Move m) {
        m.setPP(m.getPP() - 1);
    }

    // MODIFIES: this
    // EFFECTS: construct move set for battling Pokemon
    private void constructMoveSet(ArrayList<Move> moveSet) {
        for (Move m : moveSet) {
            Move move = new Move(m.getName(), m.getPower(), m.getPP(),m.getAccuracy());
            this.moveSet.add(move);
        }
    }

}
