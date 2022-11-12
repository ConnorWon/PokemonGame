package ui;

import ui.game.MainMenu;
import ui.gui.MainMenuGUI;

import javax.swing.*;

// starts the application
public class Main {
    public static void main(String[] args) {
        new MainMenuGUI(new JFrame("Pokemon Clash"));
    }
}
