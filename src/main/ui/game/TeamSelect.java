package ui.game;

import model.battle.BattlingPokemon;
import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.CpuTrainer;
import model.trainers.UserTrainer;

import java.util.Scanner;

// The menu that allows users to select their Pokemon team
public class TeamSelect {

    private Pokedex pokedex;
    private UserTrainer user;
    private CpuTrainer red;
    private Scanner input;

    // EFFECTS: runs the battle menu
    public TeamSelect(Pokedex p, UserTrainer user, CpuTrainer red) {
        pokedex = p;
        this.user = user;
        this.red = red;
        runBattleMenu();
    }

    // EFFECTS: creates the users Pokemon team
    private void runBattleMenu() {
        initInput();

        while (user.getTeam().size() < 3) {
            int count = 1;

            displayBattleMenu(user.getTeam().size());

            for (Pokemon p : pokedex.getUsablePokemon()) {
                System.out.println(count + " -> " + p.getName());
                count++;
            }

            //TODO 4: protect against improper inputs
            int choice = input.nextInt();

            if (choice <= pokedex.getUsablePokemon().size()) {
                BattlingPokemon bp = new BattlingPokemon(pokedex.getUsablePokemon().get(choice - 1));
                user.addTeamMember(bp);
            }
        }
        System.out.println("Battle Beginning...");
        new BattleGame(user, red);
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

