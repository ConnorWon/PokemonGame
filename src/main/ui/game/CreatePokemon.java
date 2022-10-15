package ui.game;

import model.pokedex.Move;
import model.pokedex.Pokedex;
import model.pokedex.Pokemon;

import java.util.Scanner;

// The menu that allows the user to create Pokemon of their own
public class CreatePokemon {

    private Pokedex pokedex;
    private Scanner input;

    // EFFECTS: runs the Pokemon creation menu
    public CreatePokemon(Pokedex p) {
        pokedex = p;
        runCreatePokemon();
    }

    // TODO: look for more cases in pokedex is modified
    // MODIFIES: this
    // EFFECTS: creates a Pokemon to add to the list of usable Pokemon based on user inputs
    private void runCreatePokemon() {
        boolean keepRunning = true;

        initInput();

        while (keepRunning) {
            String name = setPokemonName();
            String type = setPokemonType();
            int hp = setStat("HP");
            int atk = setStat("ATK");
            int def = setStat("DEF");
            Pokemon pokemon = new Pokemon(name, type, hp, atk, def);

            createPokemonMoves(pokemon);

            printPokemonSpecs(pokemon);
            pokedex.addPokemonToPokedex(pokemon);

            keepRunning = repeatStep(false);
        }
    }

    // MODIFIES: p
    // EFFECTS: creates the Pokemon's moves
    private void createPokemonMoves(Pokemon p) {
        boolean end = true;
        int counter = 1;

        while (p.getMoveSet().size() < 4 && end) {
            String moveName = setMoveName(counter);
            int power = setStat("Power");
            int pp = setStat("PP");
            int accuracy = setStat("Accuracy");
            p.addMoveToMoveSet(moveName, power, pp, accuracy);

            if (p.getMoveSet().size() < 4) {
                end = repeatStep(true);
                counter++;
            }
        }
    }

    // EFFECTS: returns whether user wants to repeat step
    private boolean repeatStep(boolean move) {
        boolean keepRunning = true;

        while (keepRunning) {
            if (move) {
                System.out.println("Do you want to add another move (y/n)? ");
            } else {
                System.out.println("Do you want to create another Pokemon (y/n)? ");
            }
            String another = input.next();
            another = another.toLowerCase();

            if (another.equals("y")) {
                keepRunning = false;
            } else if (another.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid choice");
            }
        }
        return true;
    }

    // REQUIRES: input != ""
    // EFFECTS: gets the user's name for their Pokemon creation
    private String setPokemonName() {
        System.out.println("Pokemon Name: ");
        return input.next();
    }

    // REQUIRES: input != ""
    // EFFECTS: returns the type the user wants for their Pokemon creation
    private String setPokemonType() {
        System.out.println("Type: ");
        return input.next();
    }

    // EFFECTS: returns the name for the move they are creating for their Pokemon creation
    private String setMoveName(int counter) {
        System.out.println("Move " + counter + " Name");
        return input.next();
    }

    // EFFECTS: returns the number a user wants a certain stat to be, number must be greater than 0
    private int setStat(String stat) {
        boolean keepRunning = true;
        int statNum = 1;

        while (keepRunning) {
            System.out.println(stat + ": ");
            statNum = input.nextInt();

            if (statNum > 0) {
                keepRunning = false;
            } else {
                System.out.println("Invalid input");
            }
        }
        return statNum;
    }

    // EFFECTS: prints the specifications of the Pokemon's moves
    private void printMoveSpecs(Pokemon p) {
        int count = 1;
        System.out.println("Moves: ");
        for (Move m : p.getMoveSet()) {
            System.out.println("\t Move " + count + " Name: " + m.getName());
            System.out.println("\t Power: " + m.getPower());
            System.out.println("\t PP: " + m.getPP());
            System.out.println("\t Accuracy: " + m.getAccuracy() + "\n");
            count++;
        }
    }

    // EFFECTS: prints the information of the Pokemon just created
    private void printPokemonSpecs(Pokemon p) {
        System.out.println("Pokemon Name: " + p.getName());
        System.out.println("Type: " + p.getType());
        System.out.println("Stats: ");
        System.out.println("\t HP: " + p.getHP());
        System.out.println("\t Attack: " + p.getAtk());
        System.out.println("\t Defense: " + p.getDef());
        printMoveSpecs(p);
    }

    // MODIFIES: this
    // EFFECTS: initializes the user inputs
    private void initInput() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

}
