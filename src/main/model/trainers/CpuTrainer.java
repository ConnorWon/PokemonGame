package model.trainers;

import model.battle.BattlingPokemon;

import java.util.ArrayList;

// Represents a computer controlled Pokemon Trainer that will serve as the opponent for users
public class CpuTrainer extends Trainer {

    // EFFECTS: constructs a computer controlled trainer with given name
    public CpuTrainer(String name) {
        super(name);
    }

//    // MODIFIES: this
//    // EFFECTS: stores what the full HP of each Pokemon is
//    public void determineFullHPs() {
//        for (BattlingPokemon bp : team) {
//            fullHPs.add(bp.getHP());
//        }
//    }

//    // MODIFIES: this
//    // EFFECTS: restores the HP of cpu's Pokemon after a battle
//    public void restorePokemonHP() {
//        for (int i = 0; i < team.size(); i++) {
//            team.get(i).setHP(fullHPs.get(i));
//        }
//    }
//
//    public ArrayList<Integer> getFullHPs() {
//        return fullHPs;
//    }
}
