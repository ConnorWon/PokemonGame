package model.trainers;

import model.pokedex.Pokemon;

import java.util.ArrayList;

public class Trainer {

    private String name;
    private ArrayList<Pokemon> team;

    // EFFECTS: constructs a trainer with given name, and empty team
    public Trainer(String name) {
        this.name = name;
        team = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getTeam() {
        return team;
    }

    // MODIFIES: this
    // EFFECTS: adds a Pokemon to the team, with a maximum of 3 Pokemon
    public void addTeamMember(Pokemon p) {
        if (team.size() < 3) {
            team.add(p);
        }
    }

}
