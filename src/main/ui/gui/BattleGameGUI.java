package ui.gui;

import model.battle.BattlingPokemon;
import model.pokedex.Move;
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
    private JLayeredPane textBoxPanel;
    JPanel userNamePanel;
    JLayeredPane battleImageLayers;

    public BattleGameGUI(JFrame frame, Pokedex pokedex, Trainer user, Trainer cpu) {
        index = 0;

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
        battleImageLayers = new JLayeredPane();
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
        userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.Y_AXIS));
        userNamePanel.setBounds(550, 320, 300, 50);
        userNamePanel.setBackground(Color.white);

        JLabel name = new JLabel(userCurrent.getName());
        name.setFont(new Font("", Font.BOLD, 22));
        JLabel hp = new JLabel("HP: " + userCurrent.getHP());
        hp.setFont(new Font("", Font.BOLD, 22));

        userNamePanel.add(name);
        userNamePanel.add(hp);

        return userNamePanel;
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
        textBoxPanel = new JLayeredPane();
        textBoxPanel.setPreferredSize(new Dimension(916, 130));

        textBoxPanel.add(createBattleTextBox(), 1);
        textBoxPanel.add(createFightButton(), 0);
        textBoxPanel.add(createSwitchButton(), 0);

        return textBoxPanel;
    }

    private JLabel createBattleTextBox() {
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
        if ("switch".equals(e.getActionCommand())) {
            frame.remove(textBoxPanel);
            switchMenu();
        } else if ("fight".equals(e.getActionCommand())) {
            frame.remove(textBoxPanel);
            fightMenu();
        }
    }

    private void switchMenu() {
        textBoxPanel = new JLayeredPane();
        textBoxPanel.setPreferredSize(new Dimension(916, 130));
        textBoxPanel.add(createBattleTextBox(), 1);

        SwitchMenuListener switchMenuListener = new SwitchMenuListener();

        textBoxPanel.add(createPkmn1Button(switchMenuListener), 0);
        textBoxPanel.add(createPkmn2Button(switchMenuListener), 0);
        textBoxPanel.add(createPkmn3Button(switchMenuListener), 0);
        textBoxPanel.add(createSwitchMenuBackButton(switchMenuListener), 0);

        frame.add(textBoxPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton createPkmn1Button(SwitchMenuListener listener) {
        BattlingPokemon p1 = user.getBattleTeam().get(0);

        JButton pkmn1 = new JButton(p1.getName() + ": " + p1.getHP() + " HP");
        pkmn1.setActionCommand("p1");
        pkmn1.addActionListener(listener);
        pkmn1.setPreferredSize(new Dimension(300, 40));
        pkmn1.setBounds(90, 20, 300, 40);

        if (p1.getHP() == 0 || p1 == userCurrent) {
            pkmn1.setEnabled(false);
        }

        return pkmn1;
    }

    private JButton createPkmn2Button(SwitchMenuListener listener) {
        BattlingPokemon p2 = user.getBattleTeam().get(1);

        JButton pkmn2 = new JButton(p2.getName() + ": " + p2.getHP() + " HP");
        pkmn2.setActionCommand("p2");
        pkmn2.addActionListener(listener);
        pkmn2.setPreferredSize(new Dimension(300, 40));
        pkmn2.setBounds(510, 20, 300, 40);

        if (p2.getHP() == 0 || p2 == userCurrent) {
            pkmn2.setEnabled(false);
        }

        return pkmn2;
    }

    private JButton createPkmn3Button(SwitchMenuListener listener) {
        BattlingPokemon p3 = user.getBattleTeam().get(2);

        JButton pkmn3 = new JButton(p3.getName() + ": " + p3.getHP() + " HP");
        pkmn3.setActionCommand("p3");
        pkmn3.addActionListener(listener);
        pkmn3.setPreferredSize(new Dimension(300, 40));
        pkmn3.setBounds(90, 70, 300, 40);

        if (p3.getHP() == 0 || p3 == userCurrent) {
            pkmn3.setEnabled(false);
        }

        return pkmn3;
    }

    private JButton createSwitchMenuBackButton(SwitchMenuListener listener) {
        JButton switchMenuBackBtn = new JButton("Back");
        switchMenuBackBtn.setActionCommand("back");
        switchMenuBackBtn.addActionListener(listener);
        switchMenuBackBtn.setPreferredSize(new Dimension(300, 40));
        switchMenuBackBtn.setBounds(510, 70, 300, 40);

        return switchMenuBackBtn;
    }

    class SwitchMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ("p1".equals(e.getActionCommand())) {
                userCurrent = userTeam.get(0);
                changingPokemon();
                // TODO: damage phase
            } else if ("p2".equals(e.getActionCommand())) {
                userCurrent = userTeam.get(1);
                changingPokemon();
                // TODO: damage phase
            } else if ("p3".equals(e.getActionCommand())) {
                userCurrent = userTeam.get(2);
                changingPokemon();
                // TODO: damage phase
            } else if ("back".equals(e.getActionCommand())) {
                frame.remove(textBoxPanel);
                frame.add(createBattleBox(), BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }

    private void changingPokemon() {
        battleImageLayers.remove(userNamePanel);
        battleImageLayers.add(createUserHPLabel());

        frame.remove(textBoxPanel);
        frame.add(createBattleBox(), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void fightMenu() {
        textBoxPanel = new JLayeredPane();
        textBoxPanel.setPreferredSize(new Dimension(916, 130));
        textBoxPanel.add(createBattleTextBox(), 1);

        FightMenuListener fightMenuListener = new FightMenuListener();

        textBoxPanel.add(createMove1Button(fightMenuListener), 0);

        if (userCurrent.getMoveSet().size() >= 2) {
            textBoxPanel.add(createMove2Button(fightMenuListener), 0);
        }

        if (userCurrent.getMoveSet().size() >= 3) {
            textBoxPanel.add(createMove3Button(fightMenuListener), 0);
        }

        if (userCurrent.getMoveSet().size() == 4) {
            textBoxPanel.add(createMove4Button(fightMenuListener), 0);
        }
        textBoxPanel.add(createFightMenuBackButton(fightMenuListener), 0);

        frame.add(textBoxPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton createMove1Button(FightMenuListener listener) {
        Move m1 = userCurrent.getMoveSet().get(0);

        JButton move = new JButton(m1.getName() + ": " + m1.getPP() + " PP");
        move.setActionCommand("m1");
        move.addActionListener(listener);
        move.setPreferredSize(new Dimension(200, 40));
        move.setBounds(80, 20, 200, 40);

        if (m1.getPP() == 0) {
            move.setEnabled(false);
        }

        return move;
    }

    private JButton createMove2Button(FightMenuListener listener) {
        Move m2 = userCurrent.getMoveSet().get(1);


        JButton move = new JButton(m2.getName() + ": " + m2.getPP() + " PP");
        move.setActionCommand("m2");
        move.addActionListener(listener);
        move.setPreferredSize(new Dimension(200, 40));
        move.setBounds(300, 20, 200, 40);

        if (m2.getPP() == 0) {
            move.setEnabled(false);
        }

        return move;
    }

    private JButton createMove3Button(FightMenuListener listener) {
        Move m3 = userCurrent.getMoveSet().get(2);

        JButton move = new JButton(m3.getName() + ": " + m3.getPP() + " PP");
        move.setActionCommand("m3");
        move.addActionListener(listener);
        move.setPreferredSize(new Dimension(200, 40));
        move.setBounds(80, 70, 200, 40);

        if (m3.getPP() == 0) {
            move.setEnabled(false);
        }

        return move;
    }

    private JButton createMove4Button(FightMenuListener listener) {
        Move m4 = userCurrent.getMoveSet().get(3);

        JButton move = new JButton(m4.getName() + ": " + m4.getPP() + " PP");
        move.setActionCommand("m4");
        move.addActionListener(listener);
        move.setPreferredSize(new Dimension(200, 40));
        move.setBounds(300, 70, 200, 40);

        if (m4.getPP() == 0) {
            move.setEnabled(false);
        }

        return move;
    }

    private JButton createFightMenuBackButton(FightMenuListener listener) {
        JButton fightMenuBackBtn = new JButton("Back");
        fightMenuBackBtn.setActionCommand("back");
        fightMenuBackBtn.addActionListener(listener);
        fightMenuBackBtn.setPreferredSize(new Dimension(270, 80));
        fightMenuBackBtn.setBounds(550, 25, 270, 80);

        return fightMenuBackBtn;
    }

    class FightMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ("m1".equals(e.getActionCommand())) {
                // TODO: damage phase
            } else if ("m2".equals(e.getActionCommand())) {
                // TODO: damage phase
            } else if ("m3".equals(e.getActionCommand())) {
                // TODO: damage phase
            } else if ("m4".equals(e.getActionCommand())) {
                // TODO: damage phase
            } else if ("back".equals(e.getActionCommand())) {
                frame.remove(textBoxPanel);
                frame.add(createBattleBox(), BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }
}
