package model.trainers;

// Represents the Pokemon trainer of the user
public class UserTrainer extends Trainer {

    // TODO: Q: would i need to test this constructor
    // EFFECTS: constructs a trainer with given name to represent the user
    public UserTrainer(String name) {
        super(name);
    }

    // MODIFIES: Trainer
    // EFFECTS: clears the trainers team
    public void clearTeam() {
        team.clear();
    }
}
