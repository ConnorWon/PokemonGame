package ui.game;

import model.pokedex.Move;
import model.pokedex.Pokemon;

import java.util.Scanner;

public class CreatePokemon {

    private Scanner input;
    private String command;

    public CreatePokemon() {
        runCreatePokemon();
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void runCreatePokemon() {
        boolean keepRunning = true;

        initInput();

        while (keepRunning) {
            System.out.println("Pokemon Name: ");
            String name = input.next();

            System.out.println("Type: ");
            String type = input.next();

            System.out.println("HP: ");
            int hp = input.nextInt();

            System.out.println("ATK: ");
            int atk = input.nextInt();

            System.out.println("DEF: ");
            int def = input.nextInt();

            Pokemon pokemon = new Pokemon(name, type, hp, atk, def);

            boolean end = true;

            int counter = 1;

            while (pokemon.getMoveSet().size() < 4 && end) {

                System.out.println("Move " + counter + " Name");
                String moveName = input.next();

                System.out.println("Power: ");
                int power = input.nextInt();

                System.out.println("PP: ");
                int pp = input.nextInt();

                System.out.println("Accuracy: ");
                int accuracy = input.nextInt();

                pokemon.addMoveToMoveSet(moveName, power, pp, accuracy);

                // delete later (or add to a final function at the bottom that displays all the data of new Pokemon)
                System.out.println("Move Name: " + pokemon.getMoveSet().get(counter - 1).getName());
                System.out.println("Power: " + pokemon.getMoveSet().get(counter - 1).getPower());
                System.out.println("PP: " + pokemon.getMoveSet().get(counter - 1).getPp());
                System.out.println("Accuracy: " + pokemon.getMoveSet().get(counter - 1).getAccuracy());

                while (pokemon.getMoveSet().size() < 4) {
                    System.out.println("Do you want to add another move (y/n)? ");
                    String another = input.next();
                    another = another.toLowerCase();

                    if (another.equals("y")) {
                        counter++;
                        break;
                    } else if (another.equals("n")) {
                        end = false;
                        break;
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
            }

            while (0 == 0) {
                System.out.println("Do you want to create another Pokemon (y/n)? ");
                command = input.next();
                command = command.toLowerCase();

                if (command.equals("n")) {
                    keepRunning = false;
                    break;
                } else if (!command.equals("y")) {
                    System.out.println("Invalid choice");
                } else {
                    break;
                }
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the user inputs
    private void initInput() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

}
