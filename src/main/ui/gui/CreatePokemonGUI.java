package ui.gui;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static javax.swing.BoxLayout.*;

// References:
//      JButton - https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
//      JTextField - https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
//      JFormattedTextField - https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html
//      JComboBox - https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html
// The create Pokemon menu
public class CreatePokemonGUI extends JPanel implements ActionListener {

    private JPanel pkmnInfoTextFields;
    private JPanel moveInputs;
    private JTextField nameField;
    private JComboBox<String> typeField;
    private JFormattedTextField hpField;
    private JFormattedTextField atkField;
    private JFormattedTextField defField;
    private JFrame appWindow;
    private Map<String, ArrayList<JTextField>> moveFields;
    private Pokedex pokedex;
    private Trainer user;
    private NumberFormat numFormat = NumberFormat.getNumberInstance();
    private JButton createPkmnButton;

    // EFFECTS: constructs the GUI for the create Pokemon menu
    public CreatePokemonGUI(JFrame appWindow, Pokedex pokedex, Trainer user) {
        moveFields = new HashMap<>();
        this.appWindow = appWindow;
        this.pokedex = pokedex;
        this.user = user;
        appWindow.setMinimumSize(new Dimension(500, 500));
        appWindow.setPreferredSize(new Dimension(500, 500));
        appWindow.setMaximumSize(new Dimension(500, 500));

        numFormat.setParseIntegerOnly(true);
        numFormat.setMinimumIntegerDigits(1);
        numFormat.setMaximumIntegerDigits(3);
        numFormat.setMinimumFractionDigits(0);

        setLayout(new BoxLayout(this, Y_AXIS));

        JLabel title = new JLabel("Create Pokemon");
        title.setFont(new Font("Times", Font.BOLD, 22));
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createPkmnStatsInputs());
        add(createMoveInputs());
        add(createButtons());

        appWindow.add(this);
        appWindow.pack();
        appWindow.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the input fields for the Pokemon's stats/info
    private JPanel createPkmnStatsInputs() {
        pkmnInfoTextFields = new JPanel();
        pkmnInfoTextFields.setLayout(new BoxLayout(pkmnInfoTextFields, Y_AXIS));
        pkmnInfoTextFields.add(createNameAndTypeInputs());
        pkmnInfoTextFields.add(createStatsInputs());

        return pkmnInfoTextFields;
    }

    // MODIFIES: this
    // EFFECTS: creates the input fields for the Pokemon's name and type
    private JPanel createNameAndTypeInputs() {
        JPanel nameAndTypeInputs = new JPanel();

        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.getDocument().addDocumentListener(new MyDocumentListener());

        String[] pkmnTypes = { "Normal", "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost", "Steel",
                "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark", "Fairy"};

        typeField = new JComboBox<>(pkmnTypes);
        typeField.setSelectedIndex(0);
        typeField.addActionListener(this);

        JLabel nameLabel = new JLabel("Name:", JLabel.TRAILING);
        nameLabel.setLabelFor(nameField);
        nameAndTypeInputs.add(nameLabel);
        nameAndTypeInputs.add(nameField);

        nameAndTypeInputs.add(Box.createRigidArea(new Dimension(50, 0)));

        JLabel typeLabel = new JLabel("Type:", JLabel.TRAILING);
        typeLabel.setLabelFor(typeField);
        nameAndTypeInputs.add(typeLabel);
        nameAndTypeInputs.add(typeField);

        return nameAndTypeInputs;
    }

    // MODIFIES: this
    // EFFECTS: creates the input fields for the Pokemon's hp, atk, and def
    private JPanel createStatsInputs() {
        JComponent[] fields = new JComponent[3];
        int fieldNum = 0;

        hpField = new JFormattedTextField(numFormat);
        hpField.setColumns(3);
        hpField.getDocument().addDocumentListener(new MyDocumentListener());
        fields[fieldNum++] = hpField;

        atkField = new JFormattedTextField(numFormat);
        atkField.setColumns(3);
        atkField.getDocument().addDocumentListener(new MyDocumentListener());
        fields[fieldNum++] = atkField;

        defField = new JFormattedTextField(numFormat);
        defField.setColumns(3);
        defField.getDocument().addDocumentListener(new MyDocumentListener());
        fields[fieldNum] = defField;

        return combineStatsInputs(fields);
    }

    // EFFECTS: combines the different Pokemon stats input fields into one JPanel and returns it
    private JPanel combineStatsInputs(JComponent[] fields) {
        JPanel statsInputs = new JPanel();
        String[] labelsStrings = new String[] {
                "HP:",
                "ATK:",
                "DEF:"
        };

        JLabel[] labels = new JLabel[labelsStrings.length];
        for (int i = 0; i < labelsStrings.length; i++) {
            labels[i] = new JLabel(labelsStrings[i], JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            statsInputs.add(labels[i]);
            statsInputs.add(fields[i]);
            statsInputs.add(Box.createRigidArea(new Dimension(35, 0)));
        }
        return statsInputs;
    }

    // MODIFIES: this
    // EFFECTS: creates the input fields for the Pokemon's move set
    private JPanel createMoveInputs() {
        moveInputs = new JPanel();
        moveInputs.setLayout(new BoxLayout(moveInputs, Y_AXIS));

        JLabel title = new JLabel("Move Set");
        title.setFont(new Font("", Font.BOLD, 14));
        title.setAlignmentX(CENTER_ALIGNMENT);
        JPanel move1And2 = new JPanel();
        JPanel move3And4 = new JPanel();

        for (int i = 0; i < 2; i++) {
            move1And2.add(createMoveInput(i + 1));
        }

        for (int i = 0; i < 2; i++) {
            move3And4.add(createMoveInput(i + 3));
        }

        moveInputs.add(title);
        moveInputs.add(move1And2);
        moveInputs.add(move3And4);
        return moveInputs;
    }

    // MODIFIES: this
    // EFFECTS: creates an input field for one of the Pokemon's moves
    private JPanel createMoveInput(int i) {
        JPanel movePanel = new JPanel();
        movePanel.setLayout(new BoxLayout(movePanel, Y_AXIS));

        JLabel title = new JLabel("Move " + i);
        title.setAlignmentX(CENTER_ALIGNMENT);

        movePanel.add(title);
        movePanel.add(createMoveNameInput(i));
        movePanel.add(createMovePPPowerAccuracyInput(i));

        return movePanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the input fields for the name of the Pokemon's move
    private JPanel createMoveNameInput(int i) {
        JPanel panel = new JPanel();
        ArrayList<JTextField> fields = new ArrayList<>();

        JTextField nameField = new JTextField();
        nameField.setColumns(10);
        nameField.getDocument().addDocumentListener(new MyDocumentListener());
        fields.add(nameField);
        moveFields.put("move" + i, fields);

        JLabel nameLabel = new JLabel("Name:", JLabel.TRAILING);
        nameLabel.setLabelFor(nameField);
        panel.add(nameLabel);
        panel.add(nameField);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates the input fields for the power, pp, and accuracy of the Pokemon's move
    private JPanel createMovePPPowerAccuracyInput(int i) {
        ArrayList<JTextField> fields = moveFields.get("move" + i);
        JFormattedTextField[] textFields = new JFormattedTextField[3];
        int fieldNum = 0;

        JFormattedTextField powerField = new JFormattedTextField(numFormat);
        powerField.setColumns(2);
        powerField.getDocument().addDocumentListener(new MyDocumentListener());
        fields.add(powerField);
        textFields[fieldNum++] = powerField;

        JFormattedTextField ppField = new JFormattedTextField(numFormat);
        ppField.setColumns(2);
        ppField.getDocument().addDocumentListener(new MyDocumentListener());
        fields.add(ppField);
        textFields[fieldNum++] = ppField;

        JFormattedTextField accField = new JFormattedTextField(numFormat);
        accField.setColumns(2);
        accField.getDocument().addDocumentListener(new MyDocumentListener());
        fields.add(accField);
        textFields[fieldNum] = accField;

        return combineMoveInputs(textFields);
    }

    // EFFECTS: combines the power, pp, and accuracy input fields into one JPanel and returns it
    private JPanel combineMoveInputs(JComponent[] fields) {
        JPanel panel = new JPanel();
        String[] labelsStrings = new String[] {"POW:", "PP:", "ACC:"};

        JLabel[] labels = new JLabel[labelsStrings.length];
        for (int i = 0; i < labelsStrings.length; i++) {
            labels[i] = new JLabel(labelsStrings[i], JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);
        }
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates a back button and create button, which is disabled at first, for the GUI
    private JPanel createButtons() {
        JPanel btnPanel = new JPanel();

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setPreferredSize(new Dimension(100, 35));

        createPkmnButton = new JButton("Create");
        createPkmnButton.setActionCommand("create");
        createPkmnButton.setPreferredSize(new Dimension(100, 35));
        createPkmnButton.setEnabled(false);

        backButton.addActionListener(this);
        createPkmnButton.addActionListener(this);

        btnPanel.add(backButton);
        btnPanel.add(createPkmnButton);
        return btnPanel;
    }

    // MODIFIES: this
    // EFFECTS: based on the inputs in the different input fields, creates a Pokemon which is then added to the Pokedex
    private void addPokemon() {
        String name = nameField.getText();
        String type = (String) typeField.getSelectedItem();
        int hp = parseInt(hpField.getText());
        int atk = parseInt(atkField.getText());
        int def = parseInt(defField.getText());

        Pokemon pokemon = new Pokemon(name, type, hp, atk, def);

        for (int i = 1; i < 5; i++) {
            addMove(pokemon, i);
        }

        pokedex.addPokemonToPokedex(pokemon);
    }

    // MODIFIES: p
    // EFFECTS: adds moves corresponding to the values in the different move input fields to the Pokemon
    private void addMove(Pokemon p, int i) {
        boolean movePresent = true;
        int power = 0;
        int pp = 0;
        int accuracy = 0;

        ArrayList<JTextField> move = moveFields.get("move" + i);
        String name = move.get(0).getText();

        try {
            power = parseInt(move.get(1).getText());
            pp = parseInt(move.get(2).getText());
            accuracy = parseInt(move.get(3).getText());
        } catch (NumberFormatException e) {
            movePresent = false;
        }

        if (movePresent) {
            if (power != 0 && pp != 0 && accuracy != 0 && name != null) {
                p.addMoveToMoveSet(name, power, pp, accuracy);
            }
        }
    }

    // EFFECTS: determines if all the fields required for a Pokemon to be created are filled
    private boolean allRequiredFieldsFilled() {
        boolean name = nameField.getText().length() > 0;
        int hp = 0;
        int atk = 0;
        int def = 0;
        boolean statsOK = true;

        try {
            hp = parseInt(hpField.getText());
            atk = parseInt(atkField.getText());
            def = parseInt(defField.getText());
        } catch (NumberFormatException e) {
            statsOK = false;
        }

        if (hp <= 0 || atk <= 0 || def <= 0 || !name) {
            statsOK = false;
        }

        return statsOK && allRequiredMoveFieldsFilled();
    }

    // EFFECTS: determines if all the fields are filled for at least one move field
    private boolean allRequiredMoveFieldsFilled() {
        boolean movesPresent = false;

        for (int i = 1; i < 5; i++) {
            ArrayList<JTextField> move = moveFields.get("move" + i);
            boolean moveName = move.get(0).getText().length() > 0;
            int power = 0;
            int pp = 0;
            int accuracy = 0;
            boolean movePresent = true;

            try {
                power = parseInt(move.get(1).getText());
                pp = parseInt(move.get(2).getText());
                accuracy = parseInt(move.get(3).getText());
            } catch (NumberFormatException e) {
                movePresent = false;
            }

            if (moveName && movePresent) {
                if (power > 0 && pp > 0 && accuracy > 0) {
                    movesPresent = true;
                    break;
                }
            }
        }

        return movesPresent;
    }

    // MODIFIES: this
    // EFFECTS: clears all the fields of its values
    private void clearFields() {
        nameField.setText("");
        hpField.setText("");
        atkField.setText("");
        defField.setText("");

        for (int i = 1; i < 5; i++) {
            ArrayList<JTextField> move = moveFields.get("move" + i);
            move.get(0).setText("");
            move.get(1).setText("");
            move.get(2).setText("");
            move.get(3).setText("");
        }
    }

    // MODIFIES: this
    // EFFECTS: determines what response should occur if a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("create".equals(e.getActionCommand())) {
            addPokemon();
            clearFields();
        } else if ("back".equals(e.getActionCommand())) {
            appWindow.remove(this);
            new MainMenuGUI(appWindow, pokedex, user);
        }
    }

    class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            createPkmnButton.setEnabled(allRequiredFieldsFilled());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            createPkmnButton.setEnabled(allRequiredFieldsFilled());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            //
        }
    }
}
