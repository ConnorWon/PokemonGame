package model;

import model.trainers.Trainer;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerTest {

    Trainer trainer;
    ArrayList<Pokemon> team;
    Pokemon pikachu;
    Pokemon charmander;

    @BeforeEach
    public void runBefore() {
        trainer = new Trainer("Red");
        team = new ArrayList<>();
        pikachu = new Pokemon("Pikachu", "Electric", 50, 35, 40);
        charmander = new Pokemon("Charmander", "Fire", 25, 30, 45);
    }

    @Test
    public void testConstructor() {
        assertEquals("Red", trainer.getName());
        assertEquals(team, trainer.getTeam());
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
        team.add(pikachu);
        trainer.addTeamMember(charmander);
        team.add(charmander);
        trainer.addTeamMember(pikachu);
        team.add(pikachu);
        trainer.addTeamMember(pikachu);
        assertEquals(team, trainer.getTeam());
    }

}
