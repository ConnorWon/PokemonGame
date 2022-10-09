package ui.game;

import model.pokedex.Pokemon;

import java.util.Scanner;

import static ui.game.MainMenu.pokedex;
import static ui.game.MainMenu.trainer;

// The menu that allows users to select their Pokemon team
public class BattleMenu {

    private Scanner input;

    // EFFECTS: runs the battle menu
    public BattleMenu() {
        runBattleMenu();
    }

    // EFFECTS: creates the users Pokemon team
    private void runBattleMenu() {
        initInput();

        while (trainer.getTeam().size() < 3) {
            int count = 1;

            displayBattleMenu(trainer.getTeam().size());

            for (Pokemon p : pokedex.getUsablePokemon()) {
                System.out.println(count + " -> " + p.getName());
                count++;
            }

            //TODO 4: protect against improper inputs
            int choice = input.nextInt();

            if (choice < pokedex.getUsablePokemon().size()) {
                trainer.addTeamMember(pokedex.getUsablePokemon().get(choice - 1));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the user inputs
    private void initInput() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays the Pokemon select screen
    private void displayBattleMenu(int num)  {
        if (num == 0) {
            System.out.println("Select your first team member");
        } else if (num == 1) {
            System.out.println("Select your second team member");
        } else if (num == 2) {
            System.out.println("Select your last team member");
        }
    }
}

