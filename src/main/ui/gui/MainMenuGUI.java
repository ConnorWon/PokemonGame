package ui.gui;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class MainMenuGUI implements ActionListener {

    private JPanel btnPanel;
    private JButton battleButton;
    private JButton createPkmnButton;
    private JButton quitButton;
    private JLayeredPane titlePane;
    private ImageIcon titleText;
    private ImageIcon titleImage;
    private JLabel titleTextAsLabel;
    private JLabel titleImageAsLabel;
    private JPanel titlePanel;
    private JFrame frame;
    private Pokedex pokedex;
    private Trainer user;
    private Trainer red;

    public MainMenuGUI(JFrame frame) {
        initPokedex();
        initCpuTrainer();

        this.frame = frame;
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        createButtons();
        createTitle();

        btnPanel.setBackground(Color.LIGHT_GRAY);
        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.add(titlePanel);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setMaximumSize(new Dimension(500, 500));

        frame.pack();
        frame.setVisible(true);
    }

    private void createButtons() {
        btnPanel = new JPanel();

        battleButton = new JButton("Battle");
        battleButton.setActionCommand("battle");
        battleButton.setPreferredSize(new Dimension(150, 50));

        createPkmnButton = new JButton("Create Pokemon");
        createPkmnButton.setActionCommand("create");
        createPkmnButton.setPreferredSize(new Dimension(150, 50));

        quitButton = new JButton("Quit");
        quitButton.setActionCommand("quit");
        quitButton.setPreferredSize(new Dimension(150, 50));

        battleButton.addActionListener(this);
        createPkmnButton.addActionListener(this);
        quitButton.addActionListener(this);

        btnPanel.add(battleButton);
        btnPanel.add(createPkmnButton);
        btnPanel.add(quitButton);
    }

    private void createTitle() {
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(500, 450));
        titlePane = new JLayeredPane();
        titlePane.setPreferredSize(new Dimension(500, 450));

        createTitleTextLabel();
        createTitleImageLabel();

        titlePane.add(titleImageAsLabel, 1);
        titlePane.add(titleTextAsLabel, 0);

        titlePanel.add(titlePane);
    }

    private void createTitleTextLabel() {
        String sep = System.getProperty("file.separator");
        titleText = new ImageIcon(System.getProperty("user.dir") + sep + "data" + sep + "images" + sep
                + "titleText.png");
        titleTextAsLabel = new JLabel(titleText);
        titleTextAsLabel.setBounds(15, 5, 466, 87);
    }

    private void createTitleImageLabel() {
        String sep = System.getProperty("file.separator");
        titleImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data" + sep + "images"
                + sep + "titleScreenImage.jpg").getImage().getScaledInstance(565, 450, Image.SCALE_DEFAULT));
        titleImageAsLabel = new JLabel(titleImage);
        titleImageAsLabel.setBounds(0, 0, 565, 450);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("battle".equals(e.getActionCommand())) {
            new TeamSelectGUI();
        } else if ("create".equals(e.getActionCommand())) {
            frame.remove(btnPanel);
            frame.remove(titlePanel);
            new CreatePokemonGUI(frame, pokedex);
        } else {
            // save option pop-up
            System.exit(0);
        }
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
