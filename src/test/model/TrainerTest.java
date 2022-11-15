package model;

import model.battle.BattlingPokemon;
import model.pokedex.Move;
import model.trainers.Trainer;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// tests methods in the Trainer class
public class TrainerTest {

    private Trainer trainer;
    private ArrayList<Pokemon> team;
    private Pokemon pikachu;
    private Pokemon charmander;

    @BeforeEach
    public void runBefore() {
        trainer = new Trainer("Red");
        team = new ArrayList<>();

        pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 50);
        pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);

        charmander = new Pokemon("Charmander", "Fire", 39, 52, 43);
        charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
        charmander.addMoveToMoveSet("Ember", 30, 35, 100);
    }

    @Test
    public void testConstructor() {
        assertEquals("Red", trainer.getName());
        assertTrue(trainer.getTeam().isEmpty());
        assertTrue(trainer.getBattleTeam().isEmpty());
    }

    @Test
    public void testAddTeamMember() {
        trainer.addTeamMember(pikachu);
        team.add(pikachu);
        assertEquals(team, trainer.getTeam());
    }

    @Test
    public void testAddTeamMemberMultipleTimes() {
        trainer.addTeamMember(pikachu);
        team.add(pikachu);
        assertEquals(team, trainer.getTeam());

        trainer.addTeamMember(charmander);
        team.add(charmander);
        assertEquals(team, trainer.getTeam());
    }

    @Test
    public void testAddTeamMemberMoreThan3Times() {
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(pikachu);
        team.add(pikachu);
        team.add(charmander);
        team.add(pikachu);
        assertEquals(team, trainer.getTeam());
    }

    @Test
    public void testClearTeam() {
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.clearTeam();
        assertTrue(trainer.getTeam().isEmpty());
    }

    @Test
    public void testPrepareForBattle() {
        Pokemon squirtle = new Pokemon("Squirtle", "Water", 50, 50, 50);
        squirtle.addMoveToMoveSet("Water Gun", 30, 40, 100);
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.addTeamMember(squirtle);
        trainer.prepareForBattle();

        BattlingPokemon battlePikachu = trainer.getBattleTeam().get(0);
        BattlingPokemon battleCharmander = trainer.getBattleTeam().get(1);
        BattlingPokemon battleSquirtle = trainer.getBattleTeam().get(2);
        assertEquals(3, trainer.getBattleTeam().size());

        checkPokemon("Pikachu", "Electric", 180, 115, 105, battlePikachu);
        checkMove("Thunderbolt", 90, 15, 100, battlePikachu.getMoveSet().get(0));
        checkMove("Iron Tail", 100, 15, 75, battlePikachu.getMoveSet().get(1));
        checkMove("Quick Attack", 40, 30, 100, battlePikachu.getMoveSet().get(2));

        checkPokemon("Charmander", "Fire", 188, 109, 91, battleCharmander);
        checkMove("Flamethrower", 90, 15, 100, battleCharmander.getMoveSet().get(0));
        checkMove("Ember", 30, 35, 100, battleCharmander.getMoveSet().get(1));

        checkPokemon("Squirtle", "Water", 210, 105, 105, battleSquirtle);
        checkMove("Water Gun", 30, 40, 100, battleSquirtle.getMoveSet().get(0));
    }

    @Test
    public void testClearActiveTeam() {
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.prepareForBattle();
        trainer.clearBattleTeam();
        assertTrue(trainer.getBattleTeam().isEmpty());
    }

    @Test
    public void testRemoveTeamMember() {
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.removeTeamMember(0);
        assertEquals(1, trainer.getTeam().size());
        assertTrue(trainer.getTeam().contains(charmander));
        assertFalse(trainer.getTeam().contains(pikachu));
    }

    @Test
    public void testRemoveTeamMemberMultipleTimes() {
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.removeTeamMember(0);
        trainer.removeTeamMember(0);
        assertTrue(trainer.getTeam().isEmpty());
    }

    @Test
    public void testRemoveTeamMemberIndexOverTeamSize() {
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.removeTeamMember(0);
        trainer.removeTeamMember(1);
        assertEquals(1, trainer.getTeam().size());
        assertTrue(trainer.getTeam().contains(charmander));
        assertFalse(trainer.getTeam().contains(pikachu));
    }

    // EFFECTS: checks to see if BattlingPokemon bp has the correct info
    private void checkPokemon(String name, String type, int hp, int atk, int def, BattlingPokemon bp) {
        assertEquals(name, bp.getName());
        assertEquals(type, bp.getType());
        assertEquals(hp, bp.getHP());
        assertEquals(atk, bp.getAtk());
        assertEquals(def, bp.getDef());
    }

    // EFFECTS: checks to see if Move m has the correct info
    private void checkMove(String name, int power, int pp, int accuracy, Move m) {
        assertEquals(name, m.getName());
        assertEquals(power, m.getPower());
        assertEquals(pp, m.getPP());
        assertEquals(accuracy, m.getAccuracy());
    }

}
