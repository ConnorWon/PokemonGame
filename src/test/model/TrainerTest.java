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
        pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 50);
        pikachu.addMoveToMoveSet("Thunderbolt", 90, 15, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        battlePikachu = new BattlingPokemon(pikachu);
        charmander = new Pokemon("Charmander", "Fire", 39, 52, 43);
        charmander.addMoveToMoveSet("Flamethrower", 90, 15, 100);
        charmander.addMoveToMoveSet("Ember", 30, 35, 100);
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

    @Test
    public void testRestoreHPAndPP() {
        battlePikachu.damageTaken(100);
        battlePikachu.getMoveSet().get(0).usedMove();
        assertEquals(80, battlePikachu.getHP());
        assertEquals(14, battlePikachu.getMoveSet().get(0).getPP());
        trainer.addTeamMember(battlePikachu);
        trainer.restorePokemonHPAndPP();
        assertEquals(180, trainer.getTeam().get(0).getHP());
        assertEquals(15, trainer.getTeam().get(0).getMoveSet().get(0).getPP());
    }

    @Test
    public void testRestoreHPAndPPMultiplePokemonAndMoves() {
        battlePikachu.damageTaken(100);
        battlePikachu.getMoveSet().get(0).usedMove();
        battlePikachu.getMoveSet().get(2).usedMove();
        assertEquals(80, battlePikachu.getHP());
        assertEquals(14, battlePikachu.getMoveSet().get(0).getPP());
        assertEquals(29, battlePikachu.getMoveSet().get(2).getPP());
        trainer.addTeamMember(battlePikachu);

        battleCharmander.damageTaken(10);
        battleCharmander.getMoveSet().get(0).usedMove();
        battleCharmander.getMoveSet().get(1).usedMove();
        assertEquals(178, battleCharmander.getHP());
        assertEquals(14, battleCharmander.getMoveSet().get(0).getPP());
        assertEquals(34, battleCharmander.getMoveSet().get(1).getPP());
        trainer.addTeamMember(battleCharmander);

        trainer.restorePokemonHPAndPP();
        assertEquals(180, trainer.getTeam().get(0).getHP());
        assertEquals(15, trainer.getTeam().get(0).getMoveSet().get(0).getPP());
        assertEquals(30, trainer.getTeam().get(0).getMoveSet().get(2).getPP());
        assertEquals(188, trainer.getTeam().get(1).getHP());
        assertEquals(15, trainer.getTeam().get(1).getMoveSet().get(0).getPP());
        assertEquals(35, trainer.getTeam().get(1).getMoveSet().get(1).getPP());
    }

}
