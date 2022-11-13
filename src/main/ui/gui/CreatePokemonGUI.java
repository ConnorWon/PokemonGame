package ui.gui;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static javax.swing.BoxLayout.*;

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
    private NumberFormat numFormat = NumberFormat.getNumberInstance();
    private JButton createPkmnButton;

    public CreatePokemonGUI(JFrame appWindow, Pokedex pokedex) {
        moveFields = new HashMap<>();
        this.appWindow = appWindow;
        this.pokedex = pokedex;
        appWindow.setMinimumSize(new Dimension(500, 500));
        appWindow.setPreferredSize(new Dimension(500, 500));
        appWindow.setMaximumSize(new Dimension(500, 500));

        numFormat.setParseIntegerOnly(true);
        numFormat.setMinimumIntegerDigits(1);
        numFormat.setMaximumIntegerDigits(3);
        numFormat.setMinimumFractionDigits(0);

        setLayout(new BoxLayout(this, Y_AXIS));
//        setMinimumSize(new Dimension(500, 500));
//        setPreferredSize(new Dimension(500, 500));
//        setMaximumSize(new Dimension(500, 500));
//        setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = new JLabel("Create Pokemon");
        title.setFont(new Font("Times", Font.BOLD, 22));
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createTextFields());
        add(createMoveInputs());
        add(createButtons());

        appWindow.add(this);
        appWindow.pack();
        appWindow.setVisible(true);
    }

    // based on TextInput Demo from documentation website
    private JPanel createTextFields() {
        pkmnInfoTextFields = new JPanel();
        pkmnInfoTextFields.setLayout(new BoxLayout(pkmnInfoTextFields, Y_AXIS));
//        pkmnInfoTextFields.setMaximumSize(new Dimension(500, 200));
//        pkmnInfoTextFields.setAlignmentX(CENTER_ALIGNMENT);

        pkmnInfoTextFields.add(createNameAndTypeInputs());
//        pkmnInfoTextFields.add(Box.createRigidArea(new Dimension(0, 20)));
        pkmnInfoTextFields.add(createStatsInputs());

        return pkmnInfoTextFields;
    }

    private JPanel createNameAndTypeInputs() {
        JPanel nameAndTypeInputs = new JPanel();
//        nameAndTypeInputs.setLayout(new BoxLayout(nameAndTypeInputs, X_AXIS));

        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setMaximumSize(new Dimension(100, 20));
        nameField.getDocument().addDocumentListener(new MyDocumentListener());
//        nameField.setAlignmentX((float) 0.175);

        String[] pkmnTypes = { "Normal", "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost", "Steel",
                "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark", "Fairy"};

        typeField = new JComboBox<>(pkmnTypes);
        typeField.setSelectedIndex(0);
//        typeField.setPreferredSize(new Dimension(100, 25));
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

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private JPanel createStatsInputs() {
        JPanel statsInputs = new JPanel();
//        statsInputs.setLayout(new BoxLayout(statsInputs, X_AXIS));

        String[] labelsStrings = new String[] {
                "HP:",
                "ATK:",
                "DEF:"
        };

        JLabel[] labels = new JLabel[labelsStrings.length];
        JComponent[] fields = new JComponent[labelsStrings.length];
        int fieldNum = 0;

        hpField = new JFormattedTextField(numFormat);
        hpField.setColumns(3);
        hpField.getDocument().addDocumentListener(new MyDocumentListener());
//        hpField.setMinimumSize(new Dimension(35, 20));
//        hpField.setPreferredSize(new Dimension(35, 20));
//        hpField.setMaximumSize(new Dimension(35, 20));
        fields[fieldNum++] = hpField;

        atkField = new JFormattedTextField(numFormat);
        atkField.setColumns(3);
        atkField.getDocument().addDocumentListener(new MyDocumentListener());
//        atkField.setPreferredSize(new Dimension(35, 20));
        fields[fieldNum++] = atkField;

        defField = new JFormattedTextField(numFormat);
        defField.setColumns(3);
        defField.getDocument().addDocumentListener(new MyDocumentListener());
//        defField.setPreferredSize(new Dimension(35, 20));
        fields[fieldNum] = defField;

//        statsInputs.add(Box.createRigidArea(new Dimension(20, 0)));

        for (int i = 0; i < labelsStrings.length; i++) {
            labels[i] = new JLabel(labelsStrings[i], JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            statsInputs.add(labels[i]);
            statsInputs.add(fields[i]);
            statsInputs.add(Box.createRigidArea(new Dimension(35, 0)));
        }

        return statsInputs;
    }

    // based on TextInput Demo from documentation website
    private MaskFormatter formatter(String s) {
        MaskFormatter format = null;
        try {
            format = new MaskFormatter(s);
        } catch (java.text.ParseException e) {
            System.out.println("Formatter error");
            System.exit(-1);
        }
        return format;
    }

    private JPanel createMoveInputs() {
        moveInputs = new JPanel();
        moveInputs.setLayout(new BoxLayout(moveInputs, Y_AXIS));
//        moveInputs.setMaximumSize(new Dimension(500, 200));
//        moveInputs.setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = new JLabel("Move Set");
        title.setFont(new Font("", Font.BOLD, 14));
        title.setAlignmentX(CENTER_ALIGNMENT);
        JPanel move1And2 = new JPanel();
//        move1And2.setLayout(new BoxLayout(move1And2, X_AXIS));
        JPanel move3And4 = new JPanel();
//        move3And4.setLayout(new BoxLayout(move3And4, X_AXIS));

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

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private JPanel createMovePPPowerAccuracyInput(int i) {
        JPanel panel = new JPanel();
        ArrayList<JTextField> fields = moveFields.get("move" + i);

        JFormattedTextField powerField = new JFormattedTextField(numFormat);
        powerField.setColumns(2);
        powerField.getDocument().addDocumentListener(new MyDocumentListener());
        fields.add(powerField);

        JFormattedTextField ppField = new JFormattedTextField(numFormat);
        ppField.setColumns(2);
        ppField.getDocument().addDocumentListener(new MyDocumentListener());
        fields.add(ppField);

        JFormattedTextField accField = new JFormattedTextField(numFormat);
        accField.setColumns(2);
        accField.getDocument().addDocumentListener(new MyDocumentListener());
        fields.add(accField);

        JLabel powerLabel = new JLabel("POW:", JLabel.TRAILING);
        powerLabel.setLabelFor(powerField);
        panel.add(powerLabel);
        panel.add(powerField);

        JLabel ppLabel = new JLabel("PP:", JLabel.TRAILING);
        ppLabel.setLabelFor(ppField);
        panel.add(ppLabel);
        panel.add(ppField);

        JLabel accLabel = new JLabel("ACC:", JLabel.TRAILING);
        accLabel.setLabelFor(accField);
        panel.add(accLabel);
        panel.add(accField);

        return panel;
    }

    // make it so create btn is not active until all fields are filled out (only 1 move fields must be filled)
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

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
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
                if (power != 0 && pp != 0 && accuracy != 0) {
                    movesPresent = true;
                    break;
                }
            }
        }
        return statsOK && movesPresent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("create".equals(e.getActionCommand())) {
            // add pokemon to Pokedex
            addPokemon();
        } else if ("back".equals(e.getActionCommand())) {
            appWindow.remove(this);
            new MainMenuGUI(appWindow);
//        } else {
//            if (allRequiredFieldsFilled()) {
//                createPkmnButton.setEnabled(true);
//            } else {
//                createPkmnButton.setEnabled(false);
//            }
        }
    }

    class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (allRequiredFieldsFilled()) {
                createPkmnButton.setEnabled(true);
            } else {
                createPkmnButton.setEnabled(false);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (allRequiredFieldsFilled()) {
                createPkmnButton.setEnabled(true);
            } else {
                createPkmnButton.setEnabled(false);
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            //
        }
    }
}
