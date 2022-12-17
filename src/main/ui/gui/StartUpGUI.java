package ui.gui;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;
import persistence.JsonReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

// References:
//      JButton - https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
//      JTextField - https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
//      DocumentListener - https://docs.oracle.com/javase/tutorial/uiswing/events/documentlistener.html
// The startup screen
public class StartUpGUI extends JPanel implements ActionListener {

    private static final String DATA_STORE = "./data/savedPokedexAndUser.json";
    private JFrame frame;
    private Pokedex pokedex;
    private Trainer user;
    private JsonReader jsonReader;
    private JTextField name;
    private JButton create;
    private JPanel trainerPanel;

    // EFFECTS: constructs the GUI for the startup screen
    public StartUpGUI() {
        jsonReader = new JsonReader(DATA_STORE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        frame = new JFrame("Pokemon Clash");
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setMaximumSize(new Dimension(500, 500));

        add(Box.createRigidArea(new Dimension(0, 180)));
        add(createPromptText());
        add(createButtons());
        add(Box.createRigidArea(new Dimension(0, 180)));

        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: creates the load data prompt
    private JPanel createPromptText() {
        JPanel panel = new JPanel();
        JLabel prompt = new JLabel("Do you want to load your previous data?");
        panel.add(prompt);
        return panel;
    }

    // EFFECTS: creates load data prompt's response buttons
    private JPanel createButtons() {
        JPanel btnPanel = new JPanel();

        JButton yes = new JButton("Yes");
        yes.setActionCommand("yes");
        yes.setPreferredSize(new Dimension(100, 35));

        JButton no = new JButton("No");
        no.setActionCommand("no");
        no.setPreferredSize(new Dimension(100, 35));

        yes.addActionListener(this);
        no.addActionListener(this);

        btnPanel.add(no);
        btnPanel.add(yes);
        return btnPanel;
    }

    // MODIFIES: this
    // EFFECTS: determines what response should occur if a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("yes".equals(e.getActionCommand())) {
            try {
                loadData();
                frame.remove(this);
                new MainMenuGUI(frame, pokedex, user, new Trainer("Red"));
            } catch (Exception error) {
                System.out.println("Error with loading the data");
                frame.remove(this);
                createUserTrainerPanel();
                initPokedex();
            }
        } else if ("no".equals(e.getActionCommand())) {
            frame.remove(this);
            createUserTrainerPanel();
            initPokedex();
        } else if ("create".equals(e.getActionCommand())) {
            user = new Trainer(name.getText());
            frame.remove(trainerPanel);
            new MainMenuGUI(frame, pokedex, user, new Trainer("Red"));
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Pokedex and user's trainer info from file
    private void loadData() throws IOException {
        pokedex = jsonReader.readForPokedex();
        user = jsonReader.readForTrainer();
    }

    // MODIFIES: this
    // EFFECTS: creates the panel used to create the user's trainer
    private void createUserTrainerPanel() {
        trainerPanel = new JPanel();
        trainerPanel.setLayout(new BoxLayout(trainerPanel, BoxLayout.Y_AXIS));

        create = new JButton("Create");
        create.setActionCommand("create");
        create.setPreferredSize(new Dimension(100, 35));
        create.setEnabled(false);
        create.setAlignmentX(CENTER_ALIGNMENT);
        create.addActionListener(this);

        trainerPanel.add(Box.createRigidArea(new Dimension(0, 180)));
        trainerPanel.add(createUserTextField());
        trainerPanel.add(create);
        trainerPanel.add(Box.createRigidArea(new Dimension(0, 220)));

        frame.add(trainerPanel);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the TextField that allows the user to name their trainer
    private JPanel createUserTextField() {
        JPanel panel = new JPanel();
        JLabel namePrompt = new JLabel("Name your trainer:", JLabel.TRAILING);
        namePrompt.setAlignmentX(CENTER_ALIGNMENT);

        name = new JTextField();
        name.setColumns(10);
        name.getDocument().addDocumentListener(new MyDocumentListener());
        name.setAlignmentX(CENTER_ALIGNMENT);
        namePrompt.setLabelFor(name);

        panel.add(namePrompt);
        panel.add(name);

        return panel;
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

    // EFFECTS: determines if the name TextField contains a non-empty response
    private boolean requiredFieldFilled() {
        return name.getText().length() > 0;
    }

    // document listener used to detect changes in the text field of the startup screen
    class MyDocumentListener implements DocumentListener {

        // MODIFIES: this
        // EFFECTS: when an input is inserted into a text field in the menu, determines whether to enable or disable the
        //          create button
        @Override
        public void insertUpdate(DocumentEvent e) {
            create.setEnabled(requiredFieldFilled());
        }

        // MODIFIES: this
        // EFFECTS: when an input is removed from a text field in the menu, determines whether to enable or disable the
        //          create button
        @Override
        public void removeUpdate(DocumentEvent e) {
            create.setEnabled(requiredFieldFilled());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            //
        }
    }
}
