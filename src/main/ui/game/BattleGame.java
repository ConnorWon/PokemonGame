package ui.game;

import model.battle.BattlingPokemon;
import model.pokedex.Move;
import model.trainers.Trainer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

// Represents a Pokemon battle
public class BattleGame {

    private Trainer user;
    private Trainer red;
    private String userName;
    private String cpuName;
    private ArrayList<BattlingPokemon> userTeam;
    private ArrayList<BattlingPokemon> cpuTeam;
    private BattlingPokemon userCurrent;
    private BattlingPokemon cpuCurrent;
    private Scanner input;
    private boolean battleContinue;
    private int index;

    // EFFECTS: starts the Pokemon battle
    public BattleGame(Trainer user, Trainer red) {
        index = 0;

        this.user = user;
        this.red = red;

        userName = user.getName();
        cpuName = red.getName();
        userTeam = user.getTeam();
        cpuTeam = red.getTeam();
        userCurrent = userTeam.get(0);
        cpuCurrent = cpuTeam.get(index);

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        runBattleGame();
    }

    // MODIFIES: this
    // EFFECTS: runs the Pokemon battle
    private void runBattleGame() {

        System.out.println(userName + " vs " + cpuName);
        System.out.println(userName + " sends out " + userCurrent.getName());
        System.out.println(cpuName + " sends out " + cpuCurrent.getName());

        battleContinue = true;
        int turn = 1;

        while (battleContinue) {
            pokemonFainted();

            displayPokemonBattling();
            mainBattleMenu(turn);

            if (allPokemonFainted(userTeam) || allPokemonFainted(cpuTeam)) {
                battleEnd();
                battleContinue = false;
            }
            turn++;
        }
        user.clearTeam();
        red.clearTeam();
    }

    // MODIFIES: this
    // EFFECTS: switch fainted Pokemon out of battle
    private void pokemonFainted() {
        if (userCurrent.getHP() == 0) {
            switchPokemon();
            System.out.println(userName + " sends out " + userCurrent.getName());
        }
        if (cpuCurrent.getHP() == 0) {
            index++;
            cpuCurrent = cpuTeam.get(index);
            System.out.println(cpuName + " sends out " + cpuCurrent.getName());
        }
    }

    // EFFECTS: displays the two Pokemon currently in battle with their current HP
    private void displayPokemonBattling() {
        System.out.println("\n" + userName + ":");
        System.out.println(userCurrent.getName() + " - HP: " + userCurrent.getHP());
        System.out.println(cpuName + ":");
        System.out.println(cpuCurrent.getName() + " - HP: " + cpuCurrent.getHP() + "\n");
    }

    // MODIFIES: this
    // EFFECTS: displays the main battle menu and processes the user's inputs
    private void mainBattleMenu(int turn) {
        System.out.println("Turn " + turn);
        System.out.println("m = MOVE \t s = SWITCH");

        String choice = input.next();
        choice = choice.toLowerCase();

        if (choice.equals("m")) {
            displayMove();
        } else if (choice.equals("s")) {
            switchPokemon();
        } else {
            System.out.println("Invalid input");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the moves of the user's Pokemon that is currently in battle
    private void displayMove() {
        int num = 1;
        boolean hasPP = false;
        System.out.println("\nChoose a Move: ");

        for (Move m : userCurrent.getMoveSet()) {
            System.out.println(num + " = " + m.getName() + ": " + m.getPP() + " PP");
            num++;
            if (m.getPP() != 0) {
                hasPP = true;
            }
        }

        if (hasPP) {
            userMoveChoice();
        } else {
            displayDamage(-1, userName, cpuName, userCurrent, cpuCurrent);
        }

        if (cpuCurrent.getHP() != 0) {
            cpuMoveChoice();
        }
    }

    // MODIFIES: this
    // EFFECTS: lets user select the move they want to use
    private void userMoveChoice() {
        boolean keepGoing = true;

        while (keepGoing) {
            String choice = input.next();

            // reference: https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
            if (choice != null && choice.matches("[0-9.]+")) {
                int num = parseInt(choice);

                if (num > userCurrent.getMoveSet().size() || num <= 0) {
                    System.out.println("Invalid input");
                } else if (userCurrent.getMoveSet().get(num - 1).getPP() != 0) {
                    displayDamage(num - 1, userName, cpuName, userCurrent, cpuCurrent);
                    keepGoing = false;
                } else {
                    System.out.println("Move is out of PP");
                }
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: chooses move for cpu trainer
    private void cpuMoveChoice() {
        boolean keepGoing = true;
        boolean hasPP = false;

        for (Move m : cpuCurrent.getMoveSet()) {
            if (m.getPP() != 0) {
                hasPP = true;
            }
        }

        if (!hasPP) {
            displayDamage(-1, cpuName, userName, cpuCurrent, userCurrent);
        } else {
            while (keepGoing) {
                // reference: https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
                Random rand = new Random();
                int choice = rand.nextInt(cpuCurrent.getMoveSet().size());

                if (cpuCurrent.getMoveSet().get(choice).getPP() != 0) {
                    displayDamage(choice, cpuName, userName, cpuCurrent, userCurrent);
                    keepGoing = false;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the damage output by the attacking Pokemon
    private void displayDamage(int move, String atkT, String defT, BattlingPokemon atkBP, BattlingPokemon defBP) {
        if (move < 0) {
            int damage = atkBP.struggle(defBP);
            int recoil = damage / 2;
            defBP.damageTaken(damage);

            System.out.println("\n" + atkT + "'s " + atkBP.getName() + " used struggle");
            System.out.println(defT + "'s " + defBP.getName() + " took " + damage + " damage");
            System.out.println(atkT + "'s " + atkBP.getName() + " took " + recoil + " as recoil damage");
            remainingHP(defBP, defT);
            remainingHP(atkBP, atkT);
        } else {
            int damage = atkBP.damageOutput(atkBP.getMoveSet().get(move), defBP);
            defBP.damageTaken(damage);

            System.out.println("\n" + atkT + "'s " + atkBP.getName() + " used "
                    + atkBP.getMoveSet().get(move).getName());

            if (damage == 0) {
                System.out.println(atkBP.getMoveSet().get(move).getName() + " missed");
            } else {
                System.out.println(defT + "'s " + defBP.getName() + " took " + damage + " damage");
                remainingHP(defBP, defT);
            }
        }
    }

    // EFFECTS: displays the remaining hp of a Pokemon after an attack. If the Pokemon has 0 health, it faints
    private void remainingHP(BattlingPokemon bp, String t) {
        if (bp.getHP() == 0) {
            System.out.println(t + "'s " + bp.getName() + " has fainted");
        } else {
            System.out.println(t + "'s " + bp.getName() + " has " + bp.getHP() + " HP");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the Pokemon that can be switched to and lets user choose which Pokemon they want to swap into
    //          battle
    private void switchPokemon() {
        int count = 1;

        System.out.println("\nSwitch to new Pokemon:");
        for (BattlingPokemon bp : userTeam) {
            System.out.println(count + " = " + bp.getName() + ": " + bp.getHP() + " HP");
            count++;
        }

        boolean keepGoing = true;
        while (keepGoing) {
            String choice = input.next();

            // reference: https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
            if (choice != null && choice.matches("[0-9.]+")) {
                keepGoing = determineSwitch(choice);
            } else {
                System.out.println("Invalid input");
            }
        }
        cpuMoveChoice();
    }

    // MODIFIES: this
    // EFFECTS: determines which Pokemon the trainer wants to switch too, checking to see if switch is valid
    private boolean determineSwitch(String c) {
        int choice = parseInt(c);

        if (choice > userTeam.size() || choice <= 0) {
            System.out.println("Invalid Choice. Choose again.");
            return true;
        } else if (userTeam.get(choice - 1).equals(userCurrent)) {
            System.out.println(userCurrent.getName() + " is already in battle. Choose again.");
            return true;
        } else if (userTeam.get(choice - 1).getHP() == 0) {
            System.out.println(userTeam.get(choice - 1).getName() + " has fainted. Choose again");
            return true;
        } else {
            userCurrent = userTeam.get(choice - 1);
            return false;
        }
    }

    // EFFECTS: determines if the battle is over. If over displays battle end text
    private void battleEnd() {
        if (allPokemonFainted(userTeam) && allPokemonFainted(cpuTeam)) {
            System.out.println("Battle Over!");
            System.out.println("Both Trainers Lost");
        } else if (allPokemonFainted(userTeam)) {
            System.out.println(cpuName + " Won!");
        } else if (allPokemonFainted(cpuTeam)) {
            System.out.println(userName + " Won!");
        }
    }

    // EFFECTS: determines if all the Pokemon of a trainer has fainted
    private boolean allPokemonFainted(ArrayList<BattlingPokemon> team) {
        return team.get(0).getHP() == 0 && team.get(1).getHP() == 0 && team.get(2).getHP() == 0;
    }

}
