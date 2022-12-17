package ui.gui;

import model.pokedex.Pokedex;
import model.trainers.Trainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// References:
//      JButton - https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
//      JLayeredPane - https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
// The main menu GUI
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

    // EFFECTS: constructs the GUI for the Main Menu
    public MainMenuGUI(JFrame frame, Pokedex pokedex, Trainer user, Trainer red) {
        this.pokedex = pokedex;
        this.user = user;
        this.frame = frame;
        this.red = red;
        initCpuTrainer();

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

    // MODIFIES: this
    // EFFECTS: creates buttons for the Menu
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

    // MODIFIES: this
    // EFFECTS: creates the title screen image for the Main Menu
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

    // MODIFIES: this
    // EFFECTS: creates the title text image for the GUI
    private void createTitleTextLabel() {
        String sep = System.getProperty("file.separator");
        titleText = new ImageIcon(System.getProperty("user.dir") + sep + "data" + sep + "images" + sep
                + "titleText.png");
        titleTextAsLabel = new JLabel(titleText);
        titleTextAsLabel.setBounds(15, 5, 466, 87);
    }

    // MODIFIES: this
    // EFFECTS: creates the title image for the GUI
    private void createTitleImageLabel() {
        String sep = System.getProperty("file.separator");
        titleImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data" + sep + "images"
                + sep + "titleScreenImage.jpg").getImage().getScaledInstance(565, 450, Image.SCALE_DEFAULT));
        titleImageAsLabel = new JLabel(titleImage);
        titleImageAsLabel.setBounds(0, 0, 565, 450);
    }

    // MODIFIES: this
    // EFFECTS: determines what response should occur if a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.remove(btnPanel);
        frame.remove(titlePanel);
        if ("battle".equals(e.getActionCommand())) {
            new TeamSelectGUI(frame, pokedex, user, red);
        } else if ("create".equals(e.getActionCommand())) {
            new CreatePokemonGUI(frame, pokedex, user, red);
        } else {
            new ClosingGUI(frame, pokedex, user);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the CPU trainer
    private void initCpuTrainer() {
        Random rand = new Random();
        int p1 = rand.nextInt(pokedex.getUsablePokemon().size());
        int p2 = rand.nextInt(pokedex.getUsablePokemon().size());
        int p3 = rand.nextInt(pokedex.getUsablePokemon().size());

        red.addTeamMember(pokedex.getUsablePokemon().get(p1));
        red.addTeamMember(pokedex.getUsablePokemon().get(p2));
        red.addTeamMember(pokedex.getUsablePokemon().get(p3));
    }
}
