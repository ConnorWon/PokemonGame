package model;

import model.battle.BattlingPokemon;
import model.pokedex.Move;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BattlingPokemonTest {

    Pokemon pikachu;
    BattlingPokemon battlePikachu;
    BattlingPokemon battlePikachu2;

    @BeforeEach
    public void runBefore() {
        pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 50);
        battlePikachu2 = new BattlingPokemon(pikachu);
    }

    @Test
    public void testConstructorMinMoves() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        battlePikachu = new BattlingPokemon(pikachu);

        assertEquals("Pikachu", battlePikachu.getName());
        assertEquals("Electric", battlePikachu.getType());
        assertEquals(180, battlePikachu.getHP());
        assertEquals(115, battlePikachu.getAtk());
        assertEquals(105, battlePikachu.getDef());

        Move move = battlePikachu.getMoveSet().get(0);
        assertEquals("Thunderbolt", move.getName());
        assertEquals(95, move.getPower());
        assertEquals(15, move.getPP());
        assertEquals(100, move.getAccuracy());

        assertEquals(1, battlePikachu.getMoveSet().size());
    }

    @Test
    public void testConstructorMaxMoves() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        pikachu.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachu.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
        battlePikachu = new BattlingPokemon(pikachu);

        assertEquals("Pikachu", battlePikachu.getName());
        assertEquals("Electric", battlePikachu.getType());
        assertEquals(180, battlePikachu.getHP());
        assertEquals(115, battlePikachu.getAtk());
        assertEquals(105, battlePikachu.getDef());

        Move move = battlePikachu.getMoveSet().get(0);
        assertEquals("Thunderbolt", move.getName());
        assertEquals(95, move.getPower());
        assertEquals(15, move.getPP());
        assertEquals(100, move.getAccuracy());

        Move move2 = battlePikachu.getMoveSet().get(1);
        assertEquals("Quick Attack", move2.getName());
        assertEquals(40, move2.getPower());
        assertEquals(30, move2.getPP());
        assertEquals(100, move2.getAccuracy());

        Move move3 = battlePikachu.getMoveSet().get(2);
        assertEquals("Iron Tail", move3.getName());
        assertEquals(100, move3.getPower());
        assertEquals(15, move3.getPP());
        assertEquals(75, move3.getAccuracy());

        Move move4 = battlePikachu.getMoveSet().get(3);
        assertEquals("Volt Tackle", move4.getName());
        assertEquals(120, move4.getPower());
        assertEquals(15, move4.getPP());
        assertEquals(100, move4.getAccuracy());

        assertEquals(4, battlePikachu.getMoveSet().size());
    }

    @Test
    public void testDamageOutputGuaranteedHit() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        battlePikachu = new BattlingPokemon(pikachu);
        int damage = battlePikachu.damageOutput(battlePikachu.getMoveSet().get(0), battlePikachu);
        int expectedDamage = (int) Math.round(((42.0 * 95 * 115 / 105)/50.0 + 2)
                * battlePikachu.getDamageRoll() / 100.0);
        assertEquals(expectedDamage, damage);
    }

    @Test
    public void testDamageOutputGuaranteedMiss() {
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 0);
        battlePikachu = new BattlingPokemon(pikachu);
        int damage = battlePikachu.damageOutput(battlePikachu.getMoveSet().get(0), battlePikachu);
        assertEquals(0, damage);
    }

    @Test
    public void testDamageOutputUncertain() {
        pikachu.addMoveToMoveSet("Iron Tail", 100, 15, 50);
        battlePikachu = new BattlingPokemon(pikachu);

        int damage = battlePikachu.damageOutput(battlePikachu.getMoveSet().get(0), battlePikachu);
        int expectedDamage = (int) Math.round(((42.0 * 100 * 115 / 105)/50.0 + 2) *
                battlePikachu.getDamageRoll() / 100.0);

        if (battlePikachu.getHitChance() <= battlePikachu.getMoveSet().get(0).getAccuracy()) {
            assertEquals(expectedDamage, damage);
        } else {
            assertEquals(0, damage);
        }
    }

    @Test
    public void testDamageTaken() {
        battlePikachu2.damageTaken(100);
        assertEquals(80, battlePikachu2.getHP());
    }

    @Test
    public void testDamageTakenMultipleTimes() {
        battlePikachu2.damageTaken(100);
        assertEquals(80, battlePikachu2.getHP());
        battlePikachu2.damageTaken(40);
        assertEquals(40, battlePikachu2.getHP());
    }

    @Test
    public void testDamageTakenDamageGreaterThanHealth() {
        battlePikachu2.damageTaken(190);
        assertEquals(0, battlePikachu2.getHP());
    }

    @Test
    public void testUsedMove() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        battlePikachu = new BattlingPokemon(pikachu);
        battlePikachu.usedMove(battlePikachu.getMoveSet().get(0));
        assertEquals(14, battlePikachu.getMoveSet().get(0).getPP());
    }

    @Test
    public void testUsedMoveMultipleTimes() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        battlePikachu = new BattlingPokemon(pikachu);
        battlePikachu.usedMove(battlePikachu.getMoveSet().get(0));
        assertEquals(14, battlePikachu.getMoveSet().get(0).getPP());
        battlePikachu.usedMove(battlePikachu.getMoveSet().get(0));
        assertEquals(13, battlePikachu.getMoveSet().get(0).getPP());
    }

    @Test
    public void testUsedMoveDifferentMoves() {
        pikachu.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        pikachu.addMoveToMoveSet("Iron Tail", 100, 10, 75);
        battlePikachu = new BattlingPokemon(pikachu);
        battlePikachu.usedMove(battlePikachu.getMoveSet().get(0));
        assertEquals(14, battlePikachu.getMoveSet().get(0).getPP());
        battlePikachu.usedMove(battlePikachu.getMoveSet().get(1));
        assertEquals(9, battlePikachu.getMoveSet().get(1).getPP());
    }
}
