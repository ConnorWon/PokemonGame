package ui.gui;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public CreatePokemonGUI(JFrame appWindow, Pokedex pokedex) {
        moveFields = new HashMap<>();
        this.appWindow = appWindow;
        this.pokedex = pokedex;
        appWindow.setMinimumSize(new Dimension(500, 500));
        appWindow.setPreferredSize(new Dimension(500, 500));
        appWindow.setMaximumSize(new Dimension(500, 500));

        setLayout(new BoxLayout(this, Y_AXIS));
//        setMinimumSize(new Dimension(500, 500));
////        setPreferredSize(new Dimension(500, 500));
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
        nameField.addActionListener(this);
//        nameField.setAlignmentX((float) 0.175);

        typeField = new JComboBox<>();
        typeField.setPreferredSize(new Dimension(100, 25));
        typeField.addActionListener(this);
//        typeField.setAlignmentX((float) 0.175);

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

        hpField = new JFormattedTextField(formatter("###"));
        hpField.setColumns(3);
//        hpField.setMinimumSize(new Dimension(35, 20));
//        hpField.setPreferredSize(new Dimension(35, 20));
//        hpField.setMaximumSize(new Dimension(35, 20));
        fields[fieldNum++] = hpField;

        atkField = new JFormattedTextField(formatter("###"));
        atkField.setColumns(3);
//        atkField.setPreferredSize(new Dimension(35, 20));
        fields[fieldNum++] = atkField;

        defField = new JFormattedTextField(formatter("###"));
        defField.setColumns(3);
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

        JFormattedTextField powerField = new JFormattedTextField(formatter("###"));
        powerField.setColumns(2);
        fields.add(powerField);

        JFormattedTextField ppField = new JFormattedTextField(formatter("###"));
        ppField.setColumns(2);
        fields.add(ppField);

        JFormattedTextField accField = new JFormattedTextField(formatter("###"));
        accField.setColumns(2);
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

        JButton createPkmnButton = new JButton("Create");
        createPkmnButton.setActionCommand("create");
        createPkmnButton.setPreferredSize(new Dimension(100, 35));

        backButton.addActionListener(this);
        createPkmnButton.addActionListener(this);

        btnPanel.add(backButton);
        btnPanel.add(createPkmnButton);
        return btnPanel;
    }

    private void addPokemon() {
        String name = nameField.getText();
//        String type = typeField
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
        ArrayList<JTextField> move = moveFields.get("move" + i);
        String name = move.get(0).getText();
        int power = parseInt(move.get(1).getText());
        int pp = parseInt(move.get(2).getText());
        int accuracy = parseInt(move.get(3).getText());

        p.addMoveToMoveSet(name, power, pp, accuracy);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("create".equals(e.getActionCommand())) {
            // add pokemon to Pokedex
            addPokemon();
        } else if ("back".equals(e.getActionCommand())) {
            appWindow.remove(this);
            new MainMenuGUI(appWindow);
        } else {
            // save option pop-up
            System.exit(0);
        }
    }
}
