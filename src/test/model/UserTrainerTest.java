package model;

import model.pokedex.Pokemon;
import model.trainers.UserTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTrainerTest {

    UserTrainer user = new UserTrainer("Red");
    Pokemon pikachu = new Pokemon("Pikachu", "Electric", 50, 35, 40);
    Pokemon charmander = new Pokemon("Charmander", "Fire", 25, 30, 45);
    ArrayList<Pokemon> emptyTeam = new ArrayList<>();

    @Test
    public void testClearTeam() {
        user.addTeamMember(pikachu);
        user.addTeamMember(charmander);
        user.clearTeam();
        assertEquals(emptyTeam, user.getTeam());
    }

}
