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
        assertTrue(trainer.getActiveTeam().isEmpty());
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
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.addTeamMember(squirtle);
        trainer.prepareForBattle();

        BattlingPokemon battlePikachu = trainer.getActiveTeam().get(0);
        BattlingPokemon battleCharmander = trainer.getActiveTeam().get(1);
        BattlingPokemon battleSquirtle = trainer.getActiveTeam().get(2);
        assertEquals("Pikachu", battlePikachu.getName());
        assertEquals("Electric", battlePikachu.getType());
        assertEquals(180, battlePikachu.getHP());
        assertEquals(115, battlePikachu.getAtk());
        assertEquals(105, battlePikachu.getDef());

        Move move = battlePikachu.getMoveSet().get(0);
        assertEquals("Thunderbolt", move.getName());
        assertEquals(90, move.getPower());
        assertEquals(15, move.getPP());
        assertEquals(100, move.getAccuracy());

        Move move2 = battlePikachu.getMoveSet().get(1);
        assertEquals("Iron Tail", move2.getName());
        assertEquals(100, move2.getPower());
        assertEquals(15, move2.getPP());
        assertEquals(75, move2.getAccuracy());

        Move move3 = battlePikachu.getMoveSet().get(2);
        assertEquals("Quick Attack", move3.getName());
        assertEquals(40, move3.getPower());
        assertEquals(30, move3.getPP());
        assertEquals(100, move3.getAccuracy());

        assertEquals("Charmander", battleCharmander.getName());
        assertEquals("Fire", battleCharmander.getType());
        assertEquals(188, battleCharmander.getHP());
        assertEquals(109, battleCharmander.getAtk());
        assertEquals(91, battleCharmander.getDef());

        Move moveC1 = battleCharmander.getMoveSet().get(0);
        assertEquals("Flamethrower", moveC1.getName());
        assertEquals(90, moveC1.getPower());
        assertEquals(15, moveC1.getPP());
        assertEquals(100, moveC1.getAccuracy());

        Move moveC2 = battleCharmander.getMoveSet().get(1);
        assertEquals("Ember", moveC2.getName());
        assertEquals(30, moveC2.getPower());
        assertEquals(35, moveC2.getPP());
        assertEquals(100, moveC2.getAccuracy());

        assertEquals("Squirtle", battleSquirtle.getName());
        assertEquals("Water", battleSquirtle.getType());
        assertEquals(210, battleSquirtle.getHP());
        assertEquals(105, battleSquirtle.getAtk());
        assertEquals(105, battleSquirtle.getDef());
    }

    @Test
    public void testClearActiveTeam() {
        trainer.addTeamMember(pikachu);
        trainer.addTeamMember(charmander);
        trainer.prepareForBattle();
        trainer.clearActiveTeam();
        assertTrue(trainer.getActiveTeam().isEmpty());
    }


}
