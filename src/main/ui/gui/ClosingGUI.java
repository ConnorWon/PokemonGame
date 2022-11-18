package ui.gui;

import model.pokedex.Pokedex;
import model.trainers.Trainer;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// References:
//      Buttons - https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
// The closing screen
public class ClosingGUI extends JPanel implements ActionListener {

    private static final String DATA_STORE = "./data/savedPokedexAndUser.json";
    private Pokedex pokedex;
    private Trainer user;
    private JsonWriter jsonWriter;

    // EFFECTS: constructs the GUI for the closing screen
    public ClosingGUI(JFrame frame, Pokedex pokedex, Trainer user) {
        this.pokedex = pokedex;
        this.user = user;
        jsonWriter = new JsonWriter(DATA_STORE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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

    // EFFECTS: creates the save data prompt
    private JPanel createPromptText() {
        JPanel panel = new JPanel();
        JLabel prompt = new JLabel("Do you want to save your current data?");
        panel.add(prompt);
        return panel;
    }

    // EFFECTS: creates prompt response buttons
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
            saveData();
            System.exit(0);
        } else if ("no".equals(e.getActionCommand())) {
            System.exit(0);
        }
    }

    // Based on the supplied Workroom example for CPSC 210
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: saves the Pokedex and user's trainer info to file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(pokedex, user);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + DATA_STORE);
        }
    }
}
