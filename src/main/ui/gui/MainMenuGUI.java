package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame implements ActionListener {

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

    public MainMenuGUI() {
        super("Pokemon Clash");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        createButtons();
        createTitle();

        btnPanel.setBackground(Color.LIGHT_GRAY);
        add(btnPanel, BorderLayout.SOUTH);
        add(titlePanel);
        setMinimumSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));

        pack();
        setVisible(true);
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
            remove(btnPanel);
            remove(titlePanel);
            new CreatePokemonGUI(this);
        } else {
            // save option pop-up
            System.exit(0);
        }
    }
}
