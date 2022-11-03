package ui.gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import static javax.swing.BoxLayout.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class CreatePokemonGUI extends JPanel {

    private JPanel pkmnStatsTextFields;
    private JTextField nameField;
    private JComboBox<String> typeField;
    private JFormattedTextField hpField;
    private JFormattedTextField atkField;
    private JFormattedTextField defField;
    private JFrame appWindow;

    public CreatePokemonGUI(JFrame appWindow) {
        this.appWindow = appWindow;
        setLayout(new BoxLayout(this, Y_AXIS));

        createTextFields();
        appWindow.setPreferredSize(new Dimension(500, 500));

        appWindow.add(pkmnStatsTextFields);

        appWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);

        appWindow.pack();
        appWindow.setVisible(true);

    }

    // based on TextInput Demo from documentation website
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void createTextFields() {
        pkmnStatsTextFields  = new JPanel();

        String[] labelsStrings = {
                "Name: ",
                "Type: ",
                "HP: ",
                "ATK: ",
                "DEF: "
        };

        JLabel[] labels = new JLabel[labelsStrings.length];
        JComponent[] fields = new JComponent[labelsStrings.length];
        int fieldNum = 0;

        nameField = new JTextField();
        nameField.setColumns(10);
        fields[fieldNum++] = nameField;

        typeField = new JComboBox<>();
        fields[fieldNum++] = typeField;

        hpField = new JFormattedTextField(formatter("###"));
        hpField.setColumns(3);
        fields[fieldNum++] = hpField;

        atkField = new JFormattedTextField(formatter("###"));
        atkField.setColumns(3);
        fields[fieldNum++] = atkField;

        defField = new JFormattedTextField(formatter("###"));
        defField.setColumns(3);
        fields[fieldNum] = defField;

        for (int i = 0; i < labelsStrings.length; i++) {
            labels[i] = new JLabel(labelsStrings[i], JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            pkmnStatsTextFields.add(labels[i]);
            pkmnStatsTextFields.add(fields[i]);
        }

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





}
