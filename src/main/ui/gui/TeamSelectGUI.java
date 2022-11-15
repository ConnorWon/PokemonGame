package ui.gui;

import model.pokedex.Pokedex;
import model.pokedex.Pokemon;
import model.trainers.Trainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class TeamSelectGUI extends JPanel implements ActionListener {

    private DefaultListModel pokemonListModel;
    private DefaultListModel teamListModel;
    private JList usablePokemonList;
    private JList userPokemonList;
    private Trainer user;
    private Trainer cpu;
    private Pokedex pokedex;
    private Pokedex sortedPokedex;
    private JFrame frame;
    private JButton addPkmn;
    private JButton removePkmn;
    private JComboBox<String> typeFilter;
    private JButton filterPkmn;
    private JPanel teamListPanel;
    private JScrollPane teamScrollPane;
    private JPanel usablePokemonListPanel;
    private JScrollPane usablePokemonScrollPane;

    public TeamSelectGUI(JFrame frame, Pokedex pokedex, Trainer user, Trainer cpu) {
        this.frame = frame;
        this.pokedex = pokedex;
        this.user = user;
        this.cpu = cpu;
        sortedPokedex = new Pokedex();

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

    private JPanel createListsPanel() {
        JPanel listsPanel = new JPanel();

        listsPanel.add(createUsablePokemonPanel());
        listsPanel.add(createTeamPanel());
        return listsPanel;
    }

    private JPanel createUsablePokemonPanel() {
        JPanel usablePokemonPanel = new JPanel();

        usablePokemonPanel.add(createUsablePokemonList());
        usablePokemonPanel.add(createUsablePokemonPanelEvents());

        return usablePokemonPanel;
    }

    private JPanel createUsablePokemonList() {
        usablePokemonListPanel = new JPanel();
        usablePokemonListPanel.setLayout(new BoxLayout(usablePokemonListPanel, Y_AXIS));
        JLabel listTitle = new JLabel("Available Pokemon");

        pokemonListModel = new DefaultListModel<>();
        for (Pokemon p : pokedex.getUsablePokemon()) {
            pokemonListModel.addElement(p.getName());
            sortedPokedex.addPokemonToPokedex(p);
        }

        usablePokemonList = new JList<>(pokemonListModel);
        usablePokemonScrollPane = constructScrollListPane(usablePokemonList, 10);
        usablePokemonListPanel.add(listTitle);
        usablePokemonListPanel.add(usablePokemonScrollPane);

        return usablePokemonListPanel;
    }

    private JPanel createUsablePokemonPanelEvents() {
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, Y_AXIS));
        eventPanel.add(createUsablePokemonListSorter());
        eventPanel.add(createUsablePokemonAddButton());

        return eventPanel;
    }

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

    private JPanel createTeamPanel() {
        JPanel teamPanel = new JPanel();

        teamPanel.add(createTeamList());
        teamPanel.add(createTeamPanelEvents());

        return teamPanel;
    }

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

    private JPanel createButtons() {
        JPanel panel = new JPanel();

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);
        backButton.setPreferredSize(new Dimension(100, 35));

        JButton battleButton = new JButton("Battle");
        battleButton.setActionCommand("battle");
        battleButton.addActionListener(this);
        battleButton.setPreferredSize(new Dimension(100, 35));

        panel.add(backButton);
        panel.add(Box.createRigidArea(new Dimension(100, 50)));
        panel.add(battleButton);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("back".equals(e.getActionCommand())) {
            frame.remove(this);
            new MainMenuGUI(frame, pokedex, user);
        } else if ("add".equals(e.getActionCommand())) {
            teamListPanel.remove(teamScrollPane);
            addSelectedPokemon();
        } else if ("remove".equals(e.getActionCommand())) {
            teamListPanel.remove(teamScrollPane);
            removeSelectedPokemon();
        } else if ("battle".equals(e.getActionCommand())) {
            frame.remove(this);
            // create battle teams
            new BattleGameGUI();
        } else if ("filter".equals((e.getActionCommand()))) {
            usablePokemonListPanel.remove(usablePokemonScrollPane);
            sortedPokedex.getUsablePokemon().clear();
            filterUsablePokemon();
        }
    }

    private void addSelectedPokemon() {
        int index = usablePokemonList.getSelectedIndex();

        user.addTeamMember(sortedPokedex.getUsablePokemon().get(index));

        teamListModel = new DefaultListModel<>();
        for (Pokemon p : user.getTeam()) {
            teamListModel.addElement(p.getName());
        }

        userPokemonList = new JList<>(teamListModel);
        teamScrollPane = constructScrollListPane(userPokemonList, 3);
        teamListPanel.add(teamScrollPane);

        frame.pack();
        frame.setVisible(true);
    }

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

        frame.pack();
        frame.setVisible(true);
    }

    private void filterUsablePokemon() {
        String type = (String) typeFilter.getSelectedItem();
        pokemonListModel = new DefaultListModel<>();

        if (type.equals("None")) {
            for (Pokemon p : pokedex.getUsablePokemon()) {
                pokemonListModel.addElement(p.getName());
                sortedPokedex.addPokemonToPokedex(p);
            }
        } else {
            for (Pokemon p : pokedex.getUsablePokemon()) {
                if (p.getType().equals(type)) {
                    pokemonListModel.addElement(p.getName());
                    sortedPokedex.addPokemonToPokedex(p);
                }
            }
        }

        usablePokemonList = new JList<>(pokemonListModel);
        usablePokemonScrollPane = constructScrollListPane(usablePokemonList, 10);
        usablePokemonListPanel.add(usablePokemonScrollPane);

        frame.pack();
        frame.setVisible(true);
    }

    private JScrollPane constructScrollListPane(JList list, int rows) {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(rows);
        list.setFixedCellWidth(100);

        return new JScrollPane(list);
    }
}
