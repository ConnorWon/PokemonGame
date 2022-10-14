package model;

import model.battle.BattlingPokemon;
import model.pokedex.Pokemon;
import model.trainers.CpuTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// TODO: make it so I only put code that repeats in every test in the runBefore method
public class CpuTrainerTest {

    CpuTrainer cpu;
    Pokemon pikachu;
    Pokemon charmander;
    BattlingPokemon battlePikachu;
    BattlingPokemon battleCharmander;

    @BeforeEach
    public void runBefore() {
        cpu = new CpuTrainer("Red");
        pikachu = new Pokemon("Pikachu", "Electric", 50, 35, 40);
        battlePikachu = new BattlingPokemon(pikachu);
        charmander = new Pokemon("Charmander", "Fire", 25, 30, 45);
        battleCharmander = new BattlingPokemon(charmander);
    }

    @Test
    public void testDetermineFullHPSinglePokemon() {
        ArrayList<Integer> compare = new ArrayList<>();
        compare.add(battlePikachu.getHP());
        cpu.addTeamMember(battlePikachu);
        cpu.determineFullHPs();
        assertEquals(compare, cpu.getFullHPs());
    }

    @Test
    public void testDetermineFullHPMultiplePokemon() {
        ArrayList<Integer> compare = new ArrayList<>();
        compare.add(battlePikachu.getHP());
        compare.add(battleCharmander.getHP());
        cpu.addTeamMember(battlePikachu);
        cpu.addTeamMember(battleCharmander);
        cpu.determineFullHPs();
        assertEquals(compare, cpu.getFullHPs());
    }

    @Test
    public void testRestorePokemonHPSinglePokemon() {
        int compare = battlePikachu.getHP();

        cpu.addTeamMember(battlePikachu);
        cpu.determineFullHPs();

        battlePikachu.setHP(0);
        assertEquals(0, cpu.getTeam().get(0).getHP());

        cpu.restorePokemonHP();
        assertEquals(compare, cpu.getTeam().get(0).getHP());
    }

    @Test
    public void testRestorePokemonHPMultiplePokemon() {
        int compareP = battlePikachu.getHP();
        int compareC = battleCharmander.getHP();

        cpu.addTeamMember(battlePikachu);
        cpu.addTeamMember(battleCharmander);
        cpu.determineFullHPs();

        battlePikachu.setHP(0);
        assertEquals(0, cpu.getTeam().get(0).getHP());

        battleCharmander.setHP(0);
        assertEquals(0, cpu.getTeam().get(1).getHP());

        cpu.restorePokemonHP();
        assertEquals(compareP, cpu.getTeam().get(0).getHP());
        assertEquals(compareC, cpu.getTeam().get(1).getHP());
    }

}
