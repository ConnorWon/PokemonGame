package ui.game;

import model.battle.BattlingPokemon;
import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

// The starting menu
public class MainMenu {

    private static final String DATA_STORE = "./data/savedPokedexAndUser.json";
    private Pokedex pokedex;
    private Trainer red;
    private Trainer user;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: opens the starting menu
    public MainMenu() {
        jsonWriter = new JsonWriter(DATA_STORE);
        jsonReader = new JsonReader(DATA_STORE);
        initInput();
        runMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs for the main menu
    private void runMainMenu() {
        boolean appRunning = true;
        initData();
        initCpuTrainer();

        while (appRunning) {
            displayMenu();
            String choice = input.next();
            choice = choice.toLowerCase();

            if (choice.equals("b")) {
                new TeamSelect(pokedex, user, red);
                initCpuTrainer();
            } else if (choice.equals("c")) {
                new CreatePokemon(pokedex);
                initCpuTrainer();
            } else if (choice.equals("q")) {
                determineSaveData();
                System.out.println("Quitting...");
                appRunning = false;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the data to be used for the app
    private void initData() {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("Do you want to load the data from your previous save (y/n)?");
            String choice = input.next();
            choice = choice.toLowerCase();

            if (choice.equals("y")) {
                try {
                    loadData();
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + DATA_STORE);
                    initUserTrainer();
                    initPokedex();
                }
                keepGoing = false;
            } else if (choice.equals("n")) {
                initUserTrainer();
                initPokedex();
                keepGoing = false;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Pokedex and user's trainer info from file
    private void loadData() throws IOException {
        System.out.println("Loading data...");
        pokedex = jsonReader.readForPokedex();
        user = jsonReader.readForTrainer();
        System.out.println("Loaded data from " + DATA_STORE);
    }

    // EFFECTS: determines if user wants to save their Pokedex and trainer info to file
    private void determineSaveData() {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("Do you want to save your data (y/n)?");
            String choice = input.next();
            choice = choice.toLowerCase();

            if (choice.equals("y")) {
                saveData();
                keepGoing = false;
            } else if (choice.equals("n")) {
                keepGoing = false;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // Based on the supplied Workroom example for CPSC 210
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the Pokedex and user's trainer info to file
    private void saveData() {
        try {
            System.out.println("Saving Data...");
            jsonWriter.open();
            jsonWriter.write(pokedex, user);
            jsonWriter.close();
            System.out.println("Data has been saved to " + DATA_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + DATA_STORE);
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
    private void initUserTrainer() {
        System.out.println("Name your trainer (don't enter null string): ");
        String name = input.next();
        user = new Trainer(name);
    }

    // MODIFIES: this
    // EFFECTS: initializes the starting Pokedex
    private void initPokedex() {
        Pokemon pikachu = initPikachu();
        Pokemon charmander = initCharmander();
        Pokemon squirtle = initSquirtle();
        Pokemon bulbasaur = initBulbasaur();

        pokedex = new Pokedex();
        pokedex.addPokemonToPokedex(pikachu);
        pokedex.addPokemonToPokedex(charmander);
        pokedex.addPokemonToPokedex(squirtle);
        pokedex.addPokemonToPokedex(bulbasaur);
    }

    // MODIFIES: this
    // EFFECTS: initializes the CPU trainer
    private void initCpuTrainer() {
        // reference: https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
        Random rand = new Random();
        int p1 = rand.nextInt(pokedex.getUsablePokemon().size());
        int p2 = rand.nextInt(pokedex.getUsablePokemon().size());
        int p3 = rand.nextInt(pokedex.getUsablePokemon().size());


        red = new Trainer("Red");
        red.addTeamMember(pokedex.getUsablePokemon().get(p1));
        red.addTeamMember(pokedex.getUsablePokemon().get(p2));
        red.addTeamMember(pokedex.getUsablePokemon().get(p3));
    }

    // EFFECTS: displays the menu options
    private void displayMenu() {
        System.out.println("Select: 'b = Battle' | 'c = Create Pokemon' | 'q = Quit'");
    }

    // EFFECTS: creates and returns the starting Pokemon Pikachu
    private Pokemon initPikachu() {
        Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 30);
        pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);

        return pikachu;
    }

    // EFFECTS: creates and returns the starting Pokemon Charmander
    private Pokemon initCharmander() {
        Pokemon charmander = new Pokemon("Charmander", "Fire", 39, 52, 43);
        charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
        charmander.addMoveToMoveSet("Fire Spin", 35, 15, 85);
        charmander.addMoveToMoveSet("Dragon Breath", 60, 20, 100);
        charmander.addMoveToMoveSet("Slash", 70, 20, 100);

        return charmander;
    }

    // EFFECTS: creates and returns the starting Pokemon Squirtle
    private Pokemon initSquirtle() {
        Pokemon squirtle = new Pokemon("Squirtle", "Water", 44, 48, 65);
        squirtle.addMoveToMoveSet("Hydro Pump", 110, 5, 80);
        squirtle.addMoveToMoveSet("Water Pulse", 60, 20, 100);
        squirtle.addMoveToMoveSet("Bite", 60, 25, 100);
        squirtle.addMoveToMoveSet("Rapid Spin", 50, 40, 100);

        return squirtle;
    }

    // EFFECTS: creates and returns the starting Pokemon Bulbasaur
    private Pokemon initBulbasaur() {
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass", 45, 49, 49);
        bulbasaur.addMoveToMoveSet("Solar Beam", 120, 10, 100);
        bulbasaur.addMoveToMoveSet("Seed Bomb", 80, 15, 100);
        bulbasaur.addMoveToMoveSet("Razor Leaf", 55, 25, 95);
        bulbasaur.addMoveToMoveSet("Vine Whip", 45, 25, 100);

        return bulbasaur;
    }

}
