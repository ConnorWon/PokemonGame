package ui.game;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.CpuTrainer;
import model.trainers.UserTrainer;

import java.util.Scanner;

// The starting menu
public class MainMenu {

    static Pokedex pokedex = new Pokedex();
    static CpuTrainer red;
    static UserTrainer trainer;

    private Scanner input;


    // EFFECTS: opens the starting menu
    public MainMenu() {
        runMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs
    private void runMainMenu() {
        boolean appRunning = true;

        initInput();

        System.out.println("Name your trainer: ");
        String name = input.next();

        initUserTrainer(name);
        initPokedex();

        while (appRunning) {
            displayMenu();

            String choice = input.next();
            choice = choice.toLowerCase();

            // TODO 3: clean up into a single method similar to that of parseInput from FitLifeGymChain
            if (choice.equals("b")) {
                new BattleMenu();
            } else if (choice.equals("c")) {
                new CreatePokemon();
            } else if (choice.equals("q")) {
                System.out.println("Quitting...");
                appRunning = false;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the user inputs
    private void initInput() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: initializes the user's trainer
    private void initUserTrainer(String name) {
        trainer = new UserTrainer(name);
    }

    // MODIFIES: this
    // EFFECTS: initializes the CPU trainer
    private void initCpuTrainer(Pokemon p1, Pokemon p2, Pokemon p3) {
        red = new CpuTrainer("Red");
        red.addTeamMember(p1);
        red.addTeamMember(p2);
        red.addTeamMember(p3);
    }

    // MODIFIES: this
    // EFFECTS: initializes starting Pokemon
    private void initPokedex() {
        Pokemon pikachu = initPikachu();
        Pokemon charmander = initCharmander();
        Pokemon squirtle = initSquirtle();
        Pokemon bulbasaur = initBulbasaur();

        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);
        pokedex.addPokemonToPokedex(squirtle);
        pokedex.addPokemonToPokedex(bulbasaur);

        initCpuTrainer(pikachu, charmander, squirtle);
    }

    // EFFECTS: displays the menu options
    private void displayMenu() {
        System.out.println("Select: 'b = Battle' | 'c = Create Pokemon' | 'q = Quit'");
    }

    // EFFECTS: initializes the starting Pokemon Pikachu
    private Pokemon initPikachu() {
        Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 30);
        pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);

        return pikachu;
    }

    // EFFECTS: initializes the starting Pokemon Charmander
    private Pokemon initCharmander() {
        Pokemon charmander = new Pokemon("Charmander", "Fire", 39, 52, 43);
        charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
        charmander.addMoveToMoveSet("Fire Spin", 35, 15, 85);
        charmander.addMoveToMoveSet("Dragon Breath", 60, 20, 100);
        charmander.addMoveToMoveSet("Slash", 70, 20, 100);

        return charmander;
    }

    // EFFECTS: initializes the starting Pokemon Squirtle
    private Pokemon initSquirtle() {
        Pokemon squirtle = new Pokemon("Squirtle", "Water", 44, 48, 65);
        squirtle.addMoveToMoveSet("Hydro Pump", 110, 5, 80);
        squirtle.addMoveToMoveSet("Water Pulse", 60, 20, 100);
        squirtle.addMoveToMoveSet("Bite", 60, 25, 100);
        squirtle.addMoveToMoveSet("Rapid Spin", 50, 40, 100);

        return squirtle;
    }

    // EFFECTS: initializes the starting Pokemon Bulbasaur
    private Pokemon initBulbasaur() {
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass", 45, 49, 49);
        bulbasaur.addMoveToMoveSet("Solar Beam", 120, 10, 100);
        bulbasaur.addMoveToMoveSet("Seed Bomb", 80, 15, 100);
        bulbasaur.addMoveToMoveSet("Razor Leaf", 55, 25, 95);
        bulbasaur.addMoveToMoveSet("Vine Whip", 45, 25, 100);

        return bulbasaur;
    }
}
