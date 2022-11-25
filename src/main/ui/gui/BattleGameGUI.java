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
import java.util.Random;

// References:
//      TrafficLightGUI from class lecture lab
//      JLayeredPane - https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
//      JButton - https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
//      JLabel - https://docs.oracle.com/javase/tutorial/uiswing/components/label.html
// GUI for Battle Game
public class BattleGameGUI implements ActionListener {

    private static final int SWITCHING = -2;
    private static final int STRUGGLE = -1;
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
    private JPanel userNamePanel;
    private JLayeredPane battleImageLayers;
    private JPanel cpuNamePanel;

    // EFFECTS: constructs the GUI for the battle game
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
        frame.add(createMainBattleMenu(), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the main battle scene, with the Pokemon on the field and their names and HPs
    private JLayeredPane createBattleImage() {
        battleImageLayers = new JLayeredPane();
        battleImageLayers.setPreferredSize(new Dimension(916, 468));

        battleImageLayers.add(createBattleBackground(), 1);
        battleImageLayers.add(createPkmnUserSprite(), 0);
        battleImageLayers.add(createPkmnCpuSprite(), 0);
        battleImageLayers.add(createUserHPLabel(), 0);
        battleImageLayers.add(createCpuHPLabel(), 0);

        return battleImageLayers;
    }

    // EFFECTS: creates the main battle scene's background
    private JLabel createBattleBackground() {
        String sep = System.getProperty("file.separator");
        ImageIcon backgroundImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "battleBackground.png").getImage().getScaledInstance(916, 468,
                Image.SCALE_DEFAULT));
        JLabel backgroundAsLabel = new JLabel(backgroundImage);
        backgroundAsLabel.setBounds(0, 0, 916, 468);

        return backgroundAsLabel;
    }

    // EFFECTS: creates the sprite for the user's Pokemon
    private JLabel createPkmnUserSprite() {
        String sep = System.getProperty("file.separator");
        ImageIcon userSprite = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "subSpriteUser.png").getImage().getScaledInstance(240, 240,
                Image.SCALE_DEFAULT));
        JLabel userSpriteAsLabel = new JLabel(userSprite);
        userSpriteAsLabel.setBounds(160, 230, 240, 240);

        return userSpriteAsLabel;
    }

    // EFFECTS: creates the sprite for the cpu's Pokemon
    private JLabel createPkmnCpuSprite() {
        String sep = System.getProperty("file.separator");
        ImageIcon cpuSprite = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "subSpriteOpponent.png").getImage().getScaledInstance(240, 240,
                Image.SCALE_DEFAULT));
        JLabel cpuSpriteAsLabel = new JLabel(cpuSprite);
        cpuSpriteAsLabel.setBounds(500, 110, 240, 240);

        return cpuSpriteAsLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates the label for the user's Pokemon and its HP
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

    // MODIFIES: this
    // EFFECTS: creates the label for the cpu's Pokemon and its HP
    private JPanel createCpuHPLabel() {
        cpuNamePanel = new JPanel();
        cpuNamePanel.setLayout(new BoxLayout(cpuNamePanel, BoxLayout.Y_AXIS));
        cpuNamePanel.setBounds(10, 80, 300, 50);
        cpuNamePanel.setBackground(Color.white);

        JLabel name = new JLabel(cpuCurrent.getName());
        name.setFont(new Font("", Font.BOLD, 22));
        JLabel hp = new JLabel("HP: " + cpuCurrent.getHP());
        hp.setFont(new Font("", Font.BOLD, 22));

        cpuNamePanel.add(name);
        cpuNamePanel.add(hp);

        return cpuNamePanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the main battle menu
    private JLayeredPane createMainBattleMenu() {
        textBoxPanel = new JLayeredPane();
        textBoxPanel.setPreferredSize(new Dimension(916, 130));

        textBoxPanel.add(createBattleTextBox(), 1);
        textBoxPanel.add(createFightButton(), 0);
        textBoxPanel.add(createSwitchButton(), 0);

        return textBoxPanel;
    }

    // EFFECTS: creates the border for the battle menu
    private JLabel createBattleTextBox() {
        String sep = System.getProperty("file.separator");
        ImageIcon battleTextBox = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "data"
                + sep + "images" + sep + "battleTextBox.png").getImage().getScaledInstance(916, 130,
                Image.SCALE_DEFAULT));
        JLabel battleTextBoxAsLabel = new JLabel(battleTextBox);
        battleTextBoxAsLabel.setBounds(0, 0, 916, 130);

        return battleTextBoxAsLabel;
    }

    // EFFECTS: creates the fight button, which is used to initiate an attack with the user's Pokemon
    private JButton createFightButton() {
        JButton fightButton = new JButton("Fight");
        fightButton.setActionCommand("fight");
        fightButton.addActionListener(this);
        fightButton.setPreferredSize(new Dimension(300, 80));
        fightButton.setBounds(90, 25, 300, 80);

        return fightButton;
    }

    // EFFECTS: creates the switch Pokemon button
    private JButton createSwitchButton() {
        JButton switchButton = new JButton("Switch");
        switchButton.setActionCommand("switch");
        switchButton.addActionListener(this);
        switchButton.setPreferredSize(new Dimension(300, 80));
        switchButton.setBounds(510, 25, 300, 80);

        return switchButton;
    }

    // MODIFIES: this
    // EFFECTS: determines what effects will occur when either the fight, switch, or end button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("switch".equals(e.getActionCommand())) {
            frame.remove(textBoxPanel);
            switchMenu();
        } else if ("fight".equals(e.getActionCommand())) {
            frame.remove(textBoxPanel);
            if (movesHavePP(userCurrent)) {
                fightMenu();
            } else {
                damagePhase(STRUGGLE);
            }
        } else if ("end".equals(e.getActionCommand())) {
            frame.remove(textBoxPanel);
            frame.remove(battleImageLayers);
            user.clearBattleTeam();
            cpu.clearTeam();
            cpu.clearBattleTeam();
            new MainMenuGUI(frame, pokedex, user, cpu);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the switch Pokemon menu
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

    // EFFECTS: creates the button used to switch to the first member of the user's team
    private JButton createPkmn1Button(SwitchMenuListener listener) {
        BattlingPokemon p1 = userTeam.get(0);

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

    // EFFECTS: creates the button used to switch to the second member of the user's team
    private JButton createPkmn2Button(SwitchMenuListener listener) {
        BattlingPokemon p2 = userTeam.get(1);

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

    // EFFECTS: creates the button used to switch to the third member of the user's team
    private JButton createPkmn3Button(SwitchMenuListener listener) {
        BattlingPokemon p3 = userTeam.get(2);

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

    // EFFECTS: creates the button used to back out of the switch menu and return to the main battle menu
    private JButton createSwitchMenuBackButton(SwitchMenuListener listener) {
        JButton switchMenuBackBtn = new JButton("Back");
        switchMenuBackBtn.setActionCommand("back");
        switchMenuBackBtn.addActionListener(listener);
        switchMenuBackBtn.setPreferredSize(new Dimension(300, 40));
        switchMenuBackBtn.setBounds(510, 70, 300, 40);

        if (userCurrent.getHP() == 0) {
            switchMenuBackBtn.setEnabled(false);
        }

        return switchMenuBackBtn;
    }

    // an action listener for buttons in the switch Pokemon menu
    class SwitchMenuListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: determines what effects will occur if a button in the switch Pokemon menu is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("p1".equals(e.getActionCommand())) {
                switchPokemon(0);
            } else if ("p2".equals(e.getActionCommand())) {
                switchPokemon(1);
            } else if ("p3".equals(e.getActionCommand())) {
                switchPokemon(2);
            } else if ("back".equals(e.getActionCommand())) {
                frame.remove(textBoxPanel);
                frame.add(createMainBattleMenu(), BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: switches the user's current Pokemon for the Pokemon at index i in the user's team and updates the GUI
    //          accordingly
    private void switchPokemon(int i) {
        if (userCurrent.getHP() == 0) {
            userCurrent = userTeam.get(i);
            changingPokemon();
        } else {
            userCurrent = userTeam.get(i);
            changingPokemon();
            damagePhase(SWITCHING);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the GUI of the Battle Game for when the user switches Pokemon
    private void changingPokemon() {
        battleImageLayers.remove(userNamePanel);
        battleImageLayers.add(createUserHPLabel(), 0);

        frame.remove(textBoxPanel);
        frame.add(createMainBattleMenu(), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the fight menu, which is used to allow the user to select an attack for their Pokemon
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

    // EFFECTS: creates a button for the user's Pokemon's first move
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

    // REQUIRES: userCurrent.getMoveSet().size() >= 2
    // EFFECTS: creates a button for the user's Pokemon's second move
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

    // REQUIRES: userCurrent.getMoveSet().size() >= 3
    // EFFECTS: creates a button for the user's Pokemon's third move
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

    // REQUIRES: userCurrent.getMoveSet().size() == 4
    // EFFECTS: creates a button for the user's Pokemon's fourth move
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

    // EFFECTS: creates the button used to back out of the fight menu and return to the main battle menu
    private JButton createFightMenuBackButton(FightMenuListener listener) {
        JButton fightMenuBackBtn = new JButton("Back");
        fightMenuBackBtn.setActionCommand("back");
        fightMenuBackBtn.addActionListener(listener);
        fightMenuBackBtn.setPreferredSize(new Dimension(270, 80));
        fightMenuBackBtn.setBounds(550, 25, 270, 80);

        return fightMenuBackBtn;
    }

    // an action listener for the fight menu
    class FightMenuListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: determines what effect should occur if a button in the fight menu is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("m1".equals(e.getActionCommand())) {
                damagePhase(0);
            } else if ("m2".equals(e.getActionCommand())) {
                damagePhase(1);
            } else if ("m3".equals(e.getActionCommand())) {
                damagePhase(2);
            } else if ("m4".equals(e.getActionCommand())) {
                damagePhase(3);
            } else if ("back".equals(e.getActionCommand())) {
                frame.remove(textBoxPanel);
                frame.add(createMainBattleMenu(), BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the events of the damage phase
    private void damagePhase(int move) {
        userDamageCalculations(move);
        if (cpuCurrent.getHP() != 0) {
            cpuDamageCalculations();
        }

        updateDisplay();

        if (allPokemonFainted(userTeam) || allPokemonFainted(cpuTeam)) {
            battleEnd();
        } else {
            if (cpuCurrent.getHP() == 0) {
                index++;
                cpuCurrent = cpuTeam.get(index);
            }

            if (userCurrent.getHP() == 0) {
                frame.remove(textBoxPanel);
                switchMenu();
            } else {
                updateDisplay();
                frame.remove(textBoxPanel);
                frame.add(createMainBattleMenu(), BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: calculates the damage output by the user's Pokemon and then processes that damage onto the cpu's Pokemon
    private void userDamageCalculations(int move) {
        if (move != SWITCHING) {
            int userDamage;
            if (move != STRUGGLE) {
                userDamage = userCurrent.damageOutput(userCurrent.getMoveSet().get(move), cpuCurrent);
            } else {
                userDamage = userCurrent.struggle(cpuCurrent);
            }
            cpuCurrent.damageTaken(userDamage);
        }
    }

    // MODIFIES: this
    // EFFECTS: calculates the damage output by the cpu's Pokemon and then processes that damage onto the user's Pokemon
    private void cpuDamageCalculations() {
        if (movesHavePP(cpuCurrent)) {
            boolean keepGoing = true;
            while (keepGoing) {
                // reference: https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
                Random rand = new Random();
                int choice = rand.nextInt(cpuCurrent.getMoveSet().size());

                if (cpuCurrent.getMoveSet().get(choice).getPP() != 0) {
                    int cpuDamage = cpuCurrent.damageOutput(cpuCurrent.getMoveSet().get(choice), userCurrent);
                    userCurrent.damageTaken(cpuDamage);
                    keepGoing = false;
                }
            }
        } else {
            int cpuDamage = cpuCurrent.struggle(userCurrent);
            userCurrent.damageTaken(cpuDamage);
        }
    }

    // EFFECTS: determines if bp has any PP on any of its moves
    private boolean movesHavePP(BattlingPokemon bp) {
        for (Move m : bp.getMoveSet()) {
            if (m.getPP() != 0) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: updates the main battle scene
    private void updateDisplay() {
        battleImageLayers.remove(userNamePanel);
        battleImageLayers.remove(cpuNamePanel);
        battleImageLayers.add(createUserHPLabel(), 0);
        battleImageLayers.add(createCpuHPLabel(), 0);

        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: determines if all the Pokemon of a trainer have fainted
    private boolean allPokemonFainted(ArrayList<BattlingPokemon> team) {
        return team.get(0).getHP() == 0 && team.get(1).getHP() == 0 && team.get(2).getHP() == 0;
    }

    // MODIFIES: this
    // EFFECTS: displays the battle end text and the button to return to the Main Menu
    private void battleEnd() {
        frame.remove(textBoxPanel);
        textBoxPanel = new JLayeredPane();
        textBoxPanel.setPreferredSize(new Dimension(916, 130));

        textBoxPanel.add(createBattleTextBox(), 1);
        textBoxPanel.add(createBattleEndText(), 0);
        textBoxPanel.add(createBattleEndButton(), 0);

        frame.add(textBoxPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: creates the battle end text
    private JLabel createBattleEndText() {
        JLabel endText;
        if (allPokemonFainted(userTeam) && allPokemonFainted(cpuTeam)) {
            endText = new JLabel("Battle Over! It's a draw!");
        } else if (allPokemonFainted(userTeam)) {
            endText = new JLabel("Battle Over! " + cpuName + " Won!");
        } else {
            endText = new JLabel("Battle Over! " + userName + " Won!");
        }
        endText.setFont(new Font("", Font.BOLD, 22));
        endText.setBounds(80, 20, 300, 22);

        return endText;
    }

    // EFFECTS: creates the button used to return to the Main Menu
    private JButton createBattleEndButton() {
        JButton endButton = new JButton("End Battle");
        endButton.setActionCommand("end");
        endButton.addActionListener(this);
        endButton.setPreferredSize(new Dimension(270, 80));
        endButton.setBounds(550, 25, 270, 80);

        return endButton;
    }
}
