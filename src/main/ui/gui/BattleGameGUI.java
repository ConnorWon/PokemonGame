package ui.gui;

import model.battle.BattlingPokemon;
import model.pokedex.Pokedex;
import model.trainers.Trainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BattleGameGUI implements ActionListener {

    private JFrame frame;
    private Pokedex pokedex;
    private Trainer user;
    private Trainer cpu;
    private String userName;
    private String cpuName;
    private ArrayList<BattlingPokemon> userTeam;
    private ArrayList<BattlingPokemon> cpuTeam;
    private BattlingPokemon userCurrent;
    private BattlingPokemon cpuCurrent;
    private int index;
    private int turn;

    public BattleGameGUI(JFrame frame, Pokedex pokedex, Trainer user, Trainer cpu) {
        index = 0;
        turn = 1;

        this.frame = frame;
        this.pokedex = pokedex;
        this.user = user;
        this.cpu = cpu;

        userName = user.getName();
        cpuName = cpu.getName();
        userTeam = user.getBattleTeam();
        cpuTeam = cpu.getBattleTeam();
        userCurrent = userTeam.get(index);
        cpuCurrent = cpuTeam.get(index);

        frame.setMinimumSize(new Dimension(916, 618));
        frame.setPreferredSize(new Dimension(916, 618));
        frame.setMaximumSize(new Dimension(916, 618));

        frame.add(createBattleImage());
        frame.add(createBattleBox(), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createBattleImage() {
        JPanel battlePanel = new JPanel();
        JLayeredPane battleImageLayers = new JLayeredPane();
        battleImageLayers.setPreferredSize(new Dimension(916, 468));

        battleImageLayers.add(createBattleBackground(), 1);
        battleImageLayers.add(createPkmnUserSprite(), 0);
        battleImageLayers.add(createPkmnCpuSprite(), 0);
        battleImageLayers.add(createUserHPLabel(), 0);
        battleImageLayers.add(createCpuHPLabel(), 0);

        battlePanel.add(battleImageLayers);

        return battlePanel;
    }

    private JLabel createBattleBackground() {
        String sep = System.getProperty("file.separator");
        ImageIcon backgroundImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "battleBackground.png").getImage().getScaledInstance(916, 468,
                Image.SCALE_DEFAULT));
        JLabel backgroundAsLabel = new JLabel(backgroundImage);
        backgroundAsLabel.setBounds(0, 0, 916, 468);

        return backgroundAsLabel;
    }

    private JLabel createPkmnUserSprite() {
        String sep = System.getProperty("file.separator");
        ImageIcon userSprite = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "subSpriteUser.png").getImage().getScaledInstance(240, 240,
                Image.SCALE_DEFAULT));
        JLabel userSpriteAsLabel = new JLabel(userSprite);
        userSpriteAsLabel.setBounds(160, 230, 240, 240);

        return userSpriteAsLabel;
    }

    private JLabel createPkmnCpuSprite() {
        String sep = System.getProperty("file.separator");
        ImageIcon cpuSprite = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "subSpriteOpponent.png").getImage().getScaledInstance(240, 240,
                Image.SCALE_DEFAULT));
        JLabel cpuSpriteAsLabel = new JLabel(cpuSprite);
        cpuSpriteAsLabel.setBounds(500, 110, 240, 240);

        return cpuSpriteAsLabel;
    }

    private JPanel createUserHPLabel() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBounds(550, 320, 300, 50);
        namePanel.setBackground(Color.white);

        JLabel name = new JLabel(userCurrent.getName());
        name.setFont(new Font("", Font.BOLD, 22));
        JLabel hp = new JLabel("HP: " + userCurrent.getHP());
        hp.setFont(new Font("", Font.BOLD, 22));

        namePanel.add(name);
        namePanel.add(hp);

        return namePanel;
    }

    private JPanel createCpuHPLabel() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBounds(10, 80, 300, 50);
        namePanel.setBackground(Color.white);

        JLabel name = new JLabel(cpuCurrent.getName());
        name.setFont(new Font("", Font.BOLD, 22));
        JLabel hp = new JLabel("HP: " + cpuCurrent.getHP());
        hp.setFont(new Font("", Font.BOLD, 22));

        namePanel.add(name);
        namePanel.add(hp);

        return namePanel;
    }

    private JLayeredPane createBattleBox() {
        JLayeredPane textBoxPanel = new JLayeredPane();
        textBoxPanel.setPreferredSize(new Dimension(916, 130));

        textBoxPanel.add(createButtonBackground(), 1);
        textBoxPanel.add(createFightButton(), 0);
        textBoxPanel.add(createSwitchButton(), 0);

        return textBoxPanel;
    }

    private JLabel createButtonBackground() {
        String sep = System.getProperty("file.separator");
        ImageIcon battleTextBox = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "battleTextBox.png").getImage().getScaledInstance(916, 130,
                Image.SCALE_DEFAULT));
        JLabel battleTextBoxAsLabel = new JLabel(battleTextBox);
        battleTextBoxAsLabel.setBounds(0, 0, 916, 130);

        return battleTextBoxAsLabel;
    }

    private JButton createFightButton() {
        JButton fightButton = new JButton("Fight");
        fightButton.setActionCommand("fight");
        fightButton.addActionListener(this);
        fightButton.setPreferredSize(new Dimension(300, 80));
        fightButton.setBounds(90, 25, 300, 80);

        return fightButton;
    }

    private JButton createSwitchButton() {
        JButton switchButton = new JButton("Switch");
        switchButton.setActionCommand("switch");
        switchButton.addActionListener(this);
        switchButton.setPreferredSize(new Dimension(300, 80));
        switchButton.setBounds(510, 25, 300, 80);

        return switchButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
