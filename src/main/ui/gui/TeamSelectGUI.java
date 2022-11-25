package ui.gui;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

// References:
//      JList - https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
//      JComboBox - https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html
//      JButton - https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
// Team Select Menu GUI
public class TeamSelectGUI extends JPanel implements ActionListener {

    private DefaultListModel pokemonListModel;
    private DefaultListModel teamListModel;
    private JList usablePokemonList;
    private JList userPokemonList;
    private Trainer user;
    private Trainer cpu;
    private Pokedex pokedex;
    private ArrayList<Pokemon> filteredPokedex;
    private JFrame frame;
    private JButton addPkmn;
    private JButton removePkmn;
    private JComboBox<String> typeFilter;
    private JButton filterPkmn;
    private JPanel teamListPanel;
    private JScrollPane teamScrollPane;
    private JPanel usablePokemonListPanel;
    private JScrollPane usablePokemonScrollPane;
    private JPanel mainButtonsPanel;

    // EFFECTS: constructs the GUI for the team select menu
    public TeamSelectGUI(JFrame frame, Pokedex pokedex, Trainer user, Trainer cpu) {
        this.frame = frame;
        this.pokedex = pokedex;
        this.user = user;
        this.cpu = cpu;

        setLayout(new BoxLayout(this, Y_AXIS));

        frame.setMinimumSize(new Dimension(500, 500));
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setMaximumSize(new Dimension(500, 500));

        JLabel title = new JLabel("Choose your team");
        title.setFont(new Font("Times", Font.BOLD, 22));
        title.setAlignmentX(CENTER_ALIGNMENT);

        add(title);
        add(createListsPanel());
        add(createButtons());

        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel that displays a list of usable Pokemon and the user's current team and the
    //          corresponding event graphics for the two lists
    private JPanel createListsPanel() {
        JPanel listsPanel = new JPanel();

        listsPanel.add(createUsablePokemonPanel());
        listsPanel.add(createTeamPanel());
        return listsPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that displays a list of usable Pokemon and its corresponding event graphics
    private JPanel createUsablePokemonPanel() {
        JPanel usablePokemonPanel = new JPanel();

        usablePokemonPanel.add(createUsablePokemonList());
        usablePokemonPanel.add(createUsablePokemonPanelEvents());

        return usablePokemonPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that displays the Pokemon usable in battle
    private JPanel createUsablePokemonList() {
        usablePokemonListPanel = new JPanel();
        usablePokemonListPanel.setLayout(new BoxLayout(usablePokemonListPanel, Y_AXIS));
        JLabel listTitle = new JLabel("Available Pokemon");

        filteredPokedex = pokedex.filterPokedex("None");
        pokemonListModel = new DefaultListModel<>();
        for (Pokemon p : filteredPokedex) {
            pokemonListModel.addElement(p.getName());
        }

        usablePokemonList = new JList<>(pokemonListModel);
        usablePokemonScrollPane = constructScrollListPane(usablePokemonList, 10);
        usablePokemonListPanel.add(listTitle);
        usablePokemonListPanel.add(usablePokemonScrollPane);

        return usablePokemonListPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that displays the events related to the usable Pokemon list
    private JPanel createUsablePokemonPanelEvents() {
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, Y_AXIS));
        eventPanel.add(createUsablePokemonListSorter());
        eventPanel.add(createUsablePokemonAddButton());

        return eventPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a button and combo box used to filter the usable Pokemon list by Pokemon typing
    private JPanel createUsablePokemonListSorter() {
        JPanel panel = new JPanel();

        String[] pkmnTypes = { "None", "Normal", "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost",
                "Steel", "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark", "Fairy"};

        typeFilter = new JComboBox<>(pkmnTypes);
        typeFilter.setSelectedIndex(0);
        typeFilter.addActionListener(this);
        JLabel typeLabel = new JLabel("Sort by type:", JLabel.TRAILING);
        typeLabel.setLabelFor(typeFilter);

        filterPkmn = new JButton("Filter");
        filterPkmn.setActionCommand("filter");
        filterPkmn.addActionListener(this);
        filterPkmn.setPreferredSize(new Dimension(70, 35));

        panel.add(typeLabel);
        panel.add(typeFilter);
        panel.add(filterPkmn);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates the "add Pokemon to team" button for the usable Pokemon list
    private JPanel createUsablePokemonAddButton() {
        JPanel panel = new JPanel();

        addPkmn = new JButton("Add");
        addPkmn.setActionCommand("add");
        addPkmn.addActionListener(this);
        addPkmn.setPreferredSize(new Dimension(70, 35));

        JLabel addLabel = new JLabel("Add selected to team:", JLabel.TRAILING);
        addLabel.setLabelFor(addPkmn);

        panel.add(addLabel);
        panel.add(addPkmn);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that displays the user's current team and its corresponding event graphics
    private JPanel createTeamPanel() {
        JPanel teamPanel = new JPanel();

        teamPanel.add(createTeamList());
        teamPanel.add(createTeamPanelEvents());

        return teamPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that displays a list of the user's current team
    private JPanel createTeamList() {
        teamListPanel = new JPanel();
        teamListPanel.setLayout(new BoxLayout(teamListPanel, Y_AXIS));
        JLabel listTitle = new JLabel("Current Team");

        teamListModel = new DefaultListModel<>();
        for (Pokemon p : user.getTeam()) {
            teamListModel.addElement(p.getName());
        }

        userPokemonList = new JList<>(teamListModel);
        teamScrollPane = constructScrollListPane(userPokemonList, 3);
        teamListPanel.add(listTitle);
        teamListPanel.add(teamScrollPane);

        return teamListPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the "remove Pokemon from team" button for the user's current team list
    private JPanel createTeamPanelEvents() {
        JPanel panel = new JPanel();

        removePkmn = new JButton("Remove");
        removePkmn.setActionCommand("remove");
        removePkmn.addActionListener(this);
        removePkmn.setPreferredSize(new Dimension(70, 35));

        JLabel addLabel = new JLabel("Remove selected from team:", JLabel.TRAILING);
        addLabel.setLabelFor(removePkmn);

        panel.add(addLabel);
        panel.add(removePkmn);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates the back button, which allows the user to return to the main menu, and the battle button, which
    //          allows the user to begin a battle
    private JPanel createButtons() {
        mainButtonsPanel = new JPanel();

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);
        backButton.setPreferredSize(new Dimension(100, 35));

        JButton battleButton = new JButton("Battle");
        battleButton.setActionCommand("battle");
        battleButton.addActionListener(this);
        battleButton.setPreferredSize(new Dimension(100, 35));

        if (user.getTeam().size() < 3) {
            battleButton.setEnabled(false);
        }

        mainButtonsPanel.add(backButton);
        mainButtonsPanel.add(Box.createRigidArea(new Dimension(100, 50)));
        mainButtonsPanel.add(battleButton);

        return mainButtonsPanel;
    }

    // MODIFIES: this
    // EFFECTS: determines what response should occur if a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("back".equals(e.getActionCommand())) {
            frame.remove(this);
            new MainMenuGUI(frame, pokedex, user, cpu);
        } else if ("add".equals(e.getActionCommand())) {
            teamListPanel.remove(teamScrollPane);
            addSelectedPokemon();
        } else if ("remove".equals(e.getActionCommand())) {
            teamListPanel.remove(teamScrollPane);
            removeSelectedPokemon();
        } else if ("battle".equals(e.getActionCommand())) {
            frame.remove(this);
            user.prepareForBattle();
            cpu.prepareForBattle();
            new BattleGameGUI(frame, pokedex, user, cpu);
        } else if ("filter".equals((e.getActionCommand()))) {
            usablePokemonListPanel.remove(usablePokemonScrollPane);
            filteredPokedex.clear();
            filterUsablePokemon();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the selected Pokemon in the usable Pokemon list to the user's current team and its corresponding
    //          graphic
    private void addSelectedPokemon() {
        int index = usablePokemonList.getSelectedIndex();

        user.addTeamMember(filteredPokedex.get(index));

        teamListModel = new DefaultListModel<>();
        for (Pokemon p : user.getTeam()) {
            teamListModel.addElement(p.getName());
        }

        userPokemonList = new JList<>(teamListModel);
        teamScrollPane = constructScrollListPane(userPokemonList, 3);
        teamListPanel.add(teamScrollPane);

        this.remove(mainButtonsPanel);
        add(createButtons());

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: removes the selected Pokemon in the user's current team list from the user's current team and its
    //          corresponding graphic
    private void removeSelectedPokemon() {
        int index = userPokemonList.getSelectedIndex();

        user.removeTeamMember(index);

        teamListModel = new DefaultListModel<>();
        for (Pokemon p : user.getTeam()) {
            teamListModel.addElement(p.getName());
        }

        userPokemonList = new JList<>(teamListModel);
        teamScrollPane = constructScrollListPane(userPokemonList, 3);
        teamListPanel.add(teamScrollPane);
        this.remove(mainButtonsPanel);
        add(createButtons());

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: filters the usable Pokemon list by the type specified in the combo box
    private void filterUsablePokemon() {
        String type = (String) typeFilter.getSelectedItem();
        pokemonListModel = new DefaultListModel<>();
        filteredPokedex = pokedex.filterPokedex(type);

        for (Pokemon p : filteredPokedex) {
            pokemonListModel.addElement(p.getName());
        }

        usablePokemonList = new JList<>(pokemonListModel);
        usablePokemonScrollPane = constructScrollListPane(usablePokemonList, 10);
        usablePokemonListPanel.add(usablePokemonScrollPane);

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this/list
    // EFFECTS: sets the JList to its proper settings and returns it as a JScrollPane
    private JScrollPane constructScrollListPane(JList list, int rows) {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(rows);
        list.setFixedCellWidth(100);

        return new JScrollPane(list);
    }
}
