package ui.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {

    private JPanel btnPanel;
    private JButton battleButton;
    private JButton createPkmnButton;
    private JButton quitButton;

    public MainMenuGUI() {
        super("Pokemon Clash");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createButtons();

        btnPanel.setBackground(Color.CYAN);
        btnPanel.setOpaque(true);
        setContentPane(btnPanel);
        setMinimumSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));

        pack();
        setVisible(true);
    }

    private void createButtons() {
        btnPanel = new JPanel();

        battleButton = new JButton("Battle");
        battleButton.setActionCommand("battle");
        battleButton.setPreferredSize(new Dimension(100, 50));

        createPkmnButton = new JButton("Create Pokemon");
        battleButton.setActionCommand("create");
        createPkmnButton.setPreferredSize(new Dimension(150, 50));

        quitButton = new JButton("Quit");
        battleButton.setActionCommand("quit");
        quitButton.setPreferredSize(new Dimension(100, 50));

        btnPanel.add(battleButton);
        btnPanel.add(createPkmnButton);
        btnPanel.add(quitButton);
    }

}
