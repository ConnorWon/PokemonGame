package model.trainers;

import model.battle.BattlingPokemon;
import model.event.Event;
import model.event.EventLog;
import model.pokedex.Pokemon;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a Pokemon Trainer with a name and a Pokemon team, which can be prepared and used for battle
public class Trainer implements Writable {

    private String name;
    private ArrayList<Pokemon> team;
    private ArrayList<BattlingPokemon> battleTeam;

    // EFFECTS: constructs a trainer with given name, empty team, and empty battle team
    public Trainer(String name) {
        this.name = name;
        team = new ArrayList<>();
        battleTeam = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getTeam() {
        return team;
    }

    public ArrayList<BattlingPokemon> getBattleTeam() {
        return battleTeam;
    }

    // MODIFIES: this
    // EFFECTS: adds a Pokemon to the team, with a maximum of 3 Pokemon
    public void addTeamMember(Pokemon p) {
        if (team.size() < 3) {
            team.add(p);
            EventLog.getInstance().logEvent(new Event("Pokemon " + p.getName() + " added to " + name + "'s team"));
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the trainer's team
    public void clearTeam() {
        team.clear();
        EventLog.getInstance().logEvent(new Event(name + "'s team was cleared"));
    }

    // MODIFIES: this
    // EFFECTS: removes Pokemon at index i of the trainer's team
    public void removeTeamMember(int i) {
        if (i <= team.size() - 1) {
            EventLog.getInstance().logEvent(new Event("Pokemon " + team.get(i).getName() + " removed from "
                    + name + "'s team"));
            team.remove(i);
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the trainer's battle team
    public void clearBattleTeam() {
        battleTeam.clear();
    }

    // MODIFIES: this
    // EFFECTS: turns all Pokemon on team into battle ready Battling Pokemon, and puts them on battle ready team
    public void prepareForBattle() {
        for (Pokemon p : team) {
            battleTeam.add(new BattlingPokemon(p));
        }
    }

    // Based on the supplied Workroom example for CPSC 210
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns the trainer's data as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("team", teamToJson());
        return json;
    }

    // Based on the supplied Workroom example for CPSC 210
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns a trainer's Pokemon team as a JSON array
    private JSONArray teamToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : team) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}
