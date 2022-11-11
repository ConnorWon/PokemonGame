package ui.gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import static javax.swing.BoxLayout.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class CreatePokemonGUI extends JPanel {

    private JPanel pkmnInfoTextFields;
    private JPanel moveInputs;
    private JTextField nameField;
    private JComboBox<String> typeField;
    private JFormattedTextField hpField;
    private JFormattedTextField atkField;
    private JFormattedTextField defField;
    private JFrame appWindow;

    public CreatePokemonGUI(JFrame appWindow) {
        this.appWindow = appWindow;
        appWindow.setMinimumSize(new Dimension(500, 500));
        appWindow.setPreferredSize(new Dimension(500, 500));
        appWindow.setMaximumSize(new Dimension(500, 500));

        setLayout(new BoxLayout(this, Y_AXIS));
        setMinimumSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));
        setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = new JLabel("Create Pokemon");
        title.setFont(new Font("Times", Font.BOLD, 22));
        add(title);
        add(createTextFields());
        add(createMoveInputs());

        appWindow.add(this);
        appWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        appWindow.pack();
        appWindow.setVisible(true);

    }

    // based on TextInput Demo from documentation website
    private JPanel createTextFields() {
        pkmnInfoTextFields = new JPanel();
        pkmnInfoTextFields.setLayout(new BoxLayout(pkmnInfoTextFields, Y_AXIS));
        pkmnInfoTextFields.setMaximumSize(new Dimension(500, 200));
        pkmnInfoTextFields.setAlignmentX(CENTER_ALIGNMENT);

        pkmnInfoTextFields.add(createNameAndTypeInputs());
        pkmnInfoTextFields.add(Box.createRigidArea(new Dimension(0, 20)));
        pkmnInfoTextFields.add(createStatsInputs());

        return pkmnInfoTextFields;
    }

    private JPanel createNameAndTypeInputs() {
        JPanel nameAndTypeInputs = new JPanel();
        nameAndTypeInputs.setLayout(new BoxLayout(nameAndTypeInputs, X_AXIS));

        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setMaximumSize(new Dimension(100, 20));
//        nameField.setAlignmentX((float) 0.175);

        typeField = new JComboBox<>();
        typeField.setMaximumSize(new Dimension(100, 25));
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
        statsInputs.setLayout(new BoxLayout(statsInputs, X_AXIS));

        String[] labelsStrings = new String[] {
                "HP:",
                "ATK:",
                "DEF:"
        };

        JLabel[] labels = new JLabel[labelsStrings.length];
        JComponent[] fields = new JComponent[labelsStrings.length];
        int fieldNum = 0;

        hpField = new JFormattedTextField(formatter("###"));
        hpField.setColumns(10);
        hpField.setMaximumSize(new Dimension(35, 20));
        fields[fieldNum++] = hpField;

        atkField = new JFormattedTextField(formatter("###"));
        atkField.setColumns(10);
        atkField.setMaximumSize(new Dimension(35, 20));
        fields[fieldNum++] = atkField;

        defField = new JFormattedTextField(formatter("###"));
        defField.setColumns(10);
        defField.setMaximumSize(new Dimension(35, 20));
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
        moveInputs.setMaximumSize(new Dimension(500, 200));
        moveInputs.setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = new JLabel("Move Set");
        title.setFont(new Font("", Font.BOLD, 14));
        JPanel move1And2 = new JPanel();
        move1And2.setLayout(new BoxLayout(move1And2, X_AXIS));
        JPanel move3And4 = new JPanel();
        move3And4.setLayout(new BoxLayout(move3And4, X_AXIS));

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
        movePanel.add(title);
        movePanel.add(createMoveNameAndPowerInput());
        movePanel.add(createMovePPAndAccuracyInput());

        return movePanel;
    }

    private JPanel createMoveNameAndPowerInput() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, X_AXIS));

        JTextField nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setMaximumSize(new Dimension(100, 20));

        JFormattedTextField powerField = new JFormattedTextField(formatter("###"));
        powerField.setColumns(10);
        powerField.setMaximumSize(new Dimension(35, 20));

        JLabel nameLabel = new JLabel("Name:", JLabel.TRAILING);
        nameLabel.setLabelFor(nameField);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel powerLabel = new JLabel("Power:", JLabel.TRAILING);
        powerLabel.setLabelFor(powerField);
        panel.add(powerLabel);
        panel.add(powerField);

        return panel;
    }

    private JPanel createMovePPAndAccuracyInput() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, X_AXIS));

        JFormattedTextField ppField = new JFormattedTextField(formatter("###"));
        ppField.setColumns(10);
        ppField.setMaximumSize(new Dimension(35, 20));

        JFormattedTextField accField = new JFormattedTextField(formatter("###"));
        accField.setColumns(10);
        accField.setMaximumSize(new Dimension(35, 20));

        JLabel ppLabel = new JLabel("PP:", JLabel.TRAILING);
        ppLabel.setLabelFor(ppField);
        panel.add(ppLabel);
        panel.add(ppField);

        JLabel accLabel = new JLabel("Accuracy:", JLabel.TRAILING);
        accLabel.setLabelFor(accField);
        panel.add(accLabel);
        panel.add(accField);

        return panel;
    }
}
