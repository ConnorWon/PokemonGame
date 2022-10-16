package model.trainers;

import model.battle.BattlingPokemon;

import java.util.ArrayList;

// Represents a Pokemon Trainer with a name and Pokemon team
public class Trainer {

    protected String name;
    protected ArrayList<BattlingPokemon> team;

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
    // EFFECTS: adds a Pokemon to the team, with a maximum of 3 Pokemon
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

}
