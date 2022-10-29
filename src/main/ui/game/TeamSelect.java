package ui.game;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

// The menu that allows users to select their Pokemon team
public class TeamSelect {

    private Pokedex pokedex;
    private Trainer user;
    private Trainer red;
    private Scanner input;

    // EFFECTS: runs the team select menu
    public TeamSelect(Pokedex p, Trainer user, Trainer red) {
        pokedex = p;
        this.user = user;
        this.red = red;
        runTeamSelect();
    }

    // MODIFIES: this
    // EFFECTS: creates the Pokemon team the user will use for battle
    private void runTeamSelect() {
        initInput();

        if (user.getTeam().size() == 3) {
            keepTeam();
        }

        while (user.getTeam().size() < 3) {
            displayTeamSelectMenu(user.getTeam().size());
            displayPokemonInPokedex();
            String choice = input.next();

            // reference: https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
            if (choice != null && choice.matches("[0-9.]+")) {
                addToTeam(choice);
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println("Battle Beginning...");
        user.prepareForBattle();
        red.prepareForBattle();
        new BattleGame(user, red);
    }

    // MODIFIES: this
    // EFFECTS: initializes the user inputs
    private void initInput() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // REQUIRES: user.getTeam().size() == 3
    // MODIFIES: this
    // EFFECTS: determines if the user wants to keep the team they previously were using
    private void keepTeam() {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("Do you want to keep your previous team (y/n)?");
            String choice = input.next();
            choice = choice.toLowerCase();

            if (choice.equals("y")) {
                keepGoing = false;
            } else if (choice.equals("n")) {
                user.clearTeam();
                keepGoing = false;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // EFFECTS: displays the Pokemon select screen
    private void displayTeamSelectMenu(int num)  {
        if (num == 0) {
            System.out.println("Select your first team member");
        } else if (num == 1) {
            System.out.println("Select your second team member");
        } else if (num == 2) {
            System.out.println("Select your last team member");
        }
    }

    // EFFECTS: displays the Pokemon the user can choose for their team
    private void displayPokemonInPokedex() {
        int count = 1;
        for (Pokemon p : pokedex.getUsablePokemon()) {
            System.out.println(count + " -> " + p.getName());
            count++;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the chosen Pokemon to the user's team
    private void addToTeam(String choice) {
        // reference: https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
        int num = parseInt(choice);

        if (num <= pokedex.getUsablePokemon().size()) {
            user.addTeamMember(pokedex.getUsablePokemon().get(num - 1));
        } else {
            System.out.println("Invalid input");
        }
    }

}

