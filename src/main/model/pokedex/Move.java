package model.pokedex;

// Represents a single move of a Pokemon, has a name, power, pp (amount of times a move can be used), and move accuracy
public class Move {

    private String name;
    private int power;
    private int pp;
    private int accuracy;

    // REQUIRES: power, accuracy, pp > 0, name != ""
    // EFFECTS: creates a move with the given name, power, powerpoints, and accuracy
    public Move(String name, int power, int pp, int accuracy) {
        this.name = name;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getPP() {
        return pp;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setPP(int pp) {
        this.pp = pp;
    }
}
