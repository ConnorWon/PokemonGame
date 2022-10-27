package model.trainers;

import model.battle.BattlingPokemon;
import model.pokedex.Move;
import model.pokedex.Pokemon;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// TODO: add a win loss record for the user trainer, may have to then split off to user trainer class
// Represents a Pokemon Trainer with a name and a battle ready Pokemon team
public class Trainer implements Writable {

    private String name;
    private ArrayList<Pokemon> team;
    private ArrayList<BattlingPokemon> activeTeam;

    // EFFECTS: constructs a trainer with given name, and empty team
    public Trainer(String name) {
        this.name = name;
        team = new ArrayList<>();
        activeTeam = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getTeam() {
        return team;
    }

    public ArrayList<BattlingPokemon> getActiveTeam() {
        return activeTeam;
    }

    // MODIFIES: this
    // EFFECTS: adds a battle ready Pokemon to the team, with a maximum of 3 Pokemon
    public void addTeamMember(Pokemon p) {
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
    // EFFECTS: clears the trainers active team
    public void clearActiveTeam() {
        activeTeam.clear();
    }

    // MODIFIES: this
    // EFFECTS: turns all Pokemon on team into battle ready Battling Pokemon, and puts them on battle ready team
    public void prepareForBattle() {
        for (Pokemon p : team) {
            activeTeam.add(new BattlingPokemon(p));
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("team", teamToJson());
        return json;
    }

    private JSONArray teamToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : team) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}
