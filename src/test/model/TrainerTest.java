package model;

import model.battle.BattlingPokemon;
import model.trainers.Trainer;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// tests methods in the Trainer class
public class TrainerTest {

    private Trainer trainer;
    private ArrayList<BattlingPokemon> team;
    private Pokemon pikachu;
    private Pokemon charmander;
    private BattlingPokemon battlePikachu;
    private BattlingPokemon battleCharmander;

    @BeforeEach
    public void runBefore() {
        trainer = new Trainer("Red");
        team = new ArrayList<>();
        pikachu = new Pokemon("Pikachu", "Electric", 50, 35, 40);
        battlePikachu = new BattlingPokemon(pikachu);
        charmander = new Pokemon("Charmander", "Fire", 25, 30, 45);
        battleCharmander = new BattlingPokemon(charmander);
    }

    @Test
    public void testConstructor() {
        assertEquals("Red", trainer.getName());
        assertTrue(trainer.getTeam().isEmpty());
    }

    @Test
    public void testAddTeamMember() {
        trainer.addTeamMember(battlePikachu);
        team.add(battlePikachu);
        assertEquals(team, trainer.getTeam());
    }

    @Test
    public void testAddTeamMemberMultipleTimes() {
        trainer.addTeamMember(battlePikachu);
        team.add(battlePikachu);
        assertEquals(team, trainer.getTeam());

        trainer.addTeamMember(battleCharmander);
        team.add(battleCharmander);
        assertEquals(team, trainer.getTeam());
    }

    @Test
    public void testAddTeamMemberMoreThan3Times() {
        trainer.addTeamMember(battlePikachu);
        trainer.addTeamMember(battleCharmander);
        trainer.addTeamMember(battlePikachu);
        trainer.addTeamMember(battlePikachu);
        team.add(battlePikachu);
        team.add(battleCharmander);
        team.add(battlePikachu);
        assertEquals(team, trainer.getTeam());
    }

    @Test
    public void testClearTeam() {
        trainer.addTeamMember(battlePikachu);
        trainer.addTeamMember(battleCharmander);
        trainer.clearTeam();
        assertTrue(trainer.getTeam().isEmpty());
    }

}
