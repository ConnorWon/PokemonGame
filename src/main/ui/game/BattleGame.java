package ui.game;

import model.battle.BattlingPokemon;
import model.pokedex.Move;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static ui.game.MainMenu.red;
import static ui.game.MainMenu.user;

// Represents a Pokemon battle
public class BattleGame {

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
    public BattleGame() {
        index = 0;

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
    }

    // TODO: make it so you can go back after selecting switch or move
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

    // EFFECTS: displays the two Pokemon currently in battle with their current HP
    private void displayPokemonBattling() {
        System.out.println("\n" + userName + ":");
        System.out.println(userCurrent.getName() + " - HP: " + userCurrent.getHP());
        System.out.println(cpuName + ":");
        System.out.println(cpuCurrent.getName() + " - HP: " + cpuCurrent.getHP() + "\n");
    }

    // TODO: include struggling
    // MODIFIES: this
    // EFFECTS: displays the moves of the user's Pokemon that is currently in battle
    private void displayMove() {
        System.out.println("\nChoose a Move: ");

        int num = 1;
        for (Move m : userCurrent.getMoveSet()) {
            System.out.println(num + " = " + m.getName() + ": " + m.getPP() + " PP");
            num++;
        }

        userMoveChoice();

        if (cpuCurrent.getHP() != 0) {
            cpuMoveChoice();
        }
    }

    // TODO: error protect input
    // EFFECTS: lets user select the move they want to use
    private void userMoveChoice() {
        boolean keepGoing = true;

        while (keepGoing) {
            int choice = input.nextInt();

            if (userCurrent.getMoveSet().get(choice - 1).getPP() != 0) {
                displayDamage(choice - 1, userName, cpuName, userCurrent, cpuCurrent);
                keepGoing = false;
            } else {
                System.out.println("Move is out of PP");
            }
        }
    }

    // EFFECTS: chooses move for cpu trainer
    private void cpuMoveChoice() {
        boolean keepGoing = true;

        while (keepGoing) {
            Random rand = new Random();
            int choice = rand.nextInt(4);

            if (cpuCurrent.getMoveSet().get(choice).getPP() != 0) {
                displayDamage(choice, cpuName, userName, cpuCurrent, userCurrent);
                keepGoing = false;
            }
        }
    }

    // EFFECTS: displays the damage output by the Pokemon and the remaining health of the opposing Pokemon. If opposing
    //          Pokemon health is 0, it faints
    private void displayDamage(int move, String atkT, String defT, BattlingPokemon atkBP, BattlingPokemon defBP) {
        int damage = atkBP.damageOutput(atkBP.getMoveSet().get(move), defBP);
        defBP.damageTaken(damage);

        System.out.println("\n" + atkT + "'s " + atkBP.getName() + " used " + atkBP.getMoveSet().get(move).getName());
        if (damage == 0) {
            System.out.println(atkBP.getMoveSet().get(move).getName() + " missed");
        } else {
            System.out.println(defT + "'s " + defBP.getName() + " took " + damage + " damage");

            if (defBP.getHP() == 0) {
                System.out.println(defT + "'s " + defBP.getName() + " has fainted");
            } else {
                System.out.println(defT + "'s " + defBP.getName() + " has " + defBP.getHP() + " HP");
            }
        }
    }

//    private void displayTurnDamage(int userDamage, int cpuDamage) {
//        System.out.println(userName + "'s " + userCurrent.getName() + " did " + userDamage + " to " + cpuName + "'s "
//                + cpuCurrent.getName());
//        System.out.println(cpuName + "'s " + cpuCurrent.getName() + " has " + cpuCurrent.getHP() + " HP");
//        if (cpuCurrent.getHP() == 0) {
//            System.out.println(cpuName + "'s " + cpuCurrent.getName() + " has fainted");
//        }
//
//        System.out.println(cpuName + "'s " + cpuCurrent.getName() + " did " + cpuDamage + " to " + userName + "'s "
//                + userCurrent.getName());
//        System.out.println(userName + "'s " + userCurrent.getName() + " has " + userCurrent.getHP() + " HP");
//        if (userCurrent.getHP() == 0) {
//            System.out.println(userName + "'s " + userCurrent.getName() + " has fainted");
//        }
//    }

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

    // EFFECTS: switches the Pokemon that is currently in the battle for the user
    private void switchPokemon() {
        int count = 1;

        System.out.println("\nSwitch to new Pokemon:");
        for (BattlingPokemon bp : userTeam) {
            System.out.println(count + " = " + bp.getName() + ": " + bp.getHP() + " HP");
            count++;
        }

        boolean keepGoing = true;
        while (keepGoing) {
            int choice = input.nextInt();

            if (choice > userTeam.size() || choice <= 0) {
                System.out.println("Invalid Choice. Choose again.");
            } else if (userTeam.get(choice - 1).equals(userCurrent)) {
                System.out.println(userCurrent.getName() + " is already in battle. Choose again.");
            } else if (userTeam.get(choice - 1).getHP() == 0) {
                System.out.println(userTeam.get(choice - 1).getName() + " has fainted. Choose again");
            } else {
                userCurrent = userTeam.get(choice - 1);
                keepGoing = false;
            }
        }
        cpuMoveChoice();
    }

}
