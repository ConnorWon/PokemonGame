package model.trainers;

import model.battle.BattlingPokemon;
import model.pokedex.Move;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// TODO: add a win loss record for the user trainer, may have to then split off to user trainer class
// Represents a Pokemon Trainer with a name and a battle ready Pokemon team
public class Trainer implements Writable {

    private String name;
    private ArrayList<BattlingPokemon> team;

    // EFFECTS: constructs a trainer with given name, and empty team
    public Trainer(String name) {
        this.name = name;
        team = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<BattlingPokemon> getTeam() {
        return team;
    }

    // MODIFIES: this
    // EFFECTS: adds a battle ready Pokemon to the team, with a maximum of 3 Pokemon
    public void addTeamMember(BattlingPokemon p) {
        if (team.size() < 3) {
            team.add(p);
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the trainers team
    public void clearTeam() {
        team.clear();
    }

    // MODIFIES: this
    // EFFECTS: restores the HP and PP of Pokemon
    public void restorePokemonHPAndPP() {
        for (BattlingPokemon bp : team) {
            bp.restoreHP();
            for (Move m : bp.getMoveSet()) {
                m.restorePP();
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }

}
