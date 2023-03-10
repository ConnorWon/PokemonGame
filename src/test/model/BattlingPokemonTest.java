package model;

import model.battle.BattlingPokemon;
import model.pokedex.Move;
import model.pokedex.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests methods in the Battling Pokemon class
public class BattlingPokemonTest {

    private Pokemon pikachuOneMove;
    private Pokemon pikachuNoPP;
    private Pokemon pikachuMiss;
    private BattlingPokemon battlePikachuOneMove;
    private BattlingPokemon battlePikachuNoPP;
    private BattlingPokemon battlePikachuMaxMoves;
    private BattlingPokemon battlePikachuMiss;

    @BeforeEach
    public void runBefore() {
        pikachuOneMove = new Pokemon("Pikachu", "Electric", 35, 55, 50);
        pikachuMiss = new Pokemon("Pikachu", "Electric", 35, 55, 50);
        pikachuOneMove.addMoveToMoveSet("Thunderbolt", 95, 15, 100);
        battlePikachuOneMove = new BattlingPokemon(pikachuOneMove);
    }

    @Test
    public void testConstructorMinMoves() {
        assertEquals("Pikachu", battlePikachuOneMove.getName());
        assertEquals("Electric", battlePikachuOneMove.getType());
        assertEquals(180, battlePikachuOneMove.getHP());
        assertEquals(115, battlePikachuOneMove.getAtk());
        assertEquals(105, battlePikachuOneMove.getDef());

        checkMove("Thunderbolt", 95, 15, 100, battlePikachuOneMove.getMoveSet().get(0));
        assertEquals(1, battlePikachuOneMove.getMoveSet().size());
    }

    @Test
    public void testConstructorMaxMoves() {
        pikachuOneMove.addMoveToMoveSet("Quick Attack", 40, 30, 100);
        pikachuOneMove.addMoveToMoveSet("Iron Tail", 100, 15, 75);
        pikachuOneMove.addMoveToMoveSet("Volt Tackle", 120, 15, 100);
        battlePikachuMaxMoves = new BattlingPokemon(pikachuOneMove);

        assertEquals("Pikachu", battlePikachuMaxMoves.getName());
        assertEquals("Electric", battlePikachuMaxMoves.getType());
        assertEquals(180, battlePikachuMaxMoves.getHP());
        assertEquals(115, battlePikachuMaxMoves.getAtk());
        assertEquals(105, battlePikachuMaxMoves.getDef());

        checkMove("Thunderbolt", 95, 15, 100, battlePikachuMaxMoves.getMoveSet().get(0));
        checkMove("Quick Attack", 40, 30, 100, battlePikachuMaxMoves.getMoveSet().get(1));
        checkMove("Iron Tail", 100, 15, 75, battlePikachuMaxMoves.getMoveSet().get(2));
        checkMove("Volt Tackle", 120, 15, 100, battlePikachuMaxMoves.getMoveSet().get(3));
        assertEquals(4, battlePikachuMaxMoves.getMoveSet().size());
    }

    @Test
    public void testDamageOutputGuaranteedHit() {
        int damage = battlePikachuOneMove.damageOutput(battlePikachuOneMove.getMoveSet().get(0), battlePikachuOneMove);
        int expectedDamage = (int) Math.round(((42.0 * 95 * 115 / 105)/50.0 + 2)
                * battlePikachuOneMove.getDamageRoll() / 100.0);
        assertEquals(expectedDamage, damage);
    }

    @Test
    public void testDamageOutputGuaranteedMiss() {
        pikachuMiss.addMoveToMoveSet("Iron Tail", 100, 15, 0);
        battlePikachuMiss = new BattlingPokemon(pikachuMiss);
        int damage = battlePikachuMiss.damageOutput(battlePikachuMiss.getMoveSet().get(0), battlePikachuMiss);
        assertEquals(0, damage);
    }

    @Test
    public void testDamageOutputUncertain() {
        pikachuMiss.addMoveToMoveSet("Iron Tail", 100, 15, 50);
        battlePikachuMiss = new BattlingPokemon(pikachuMiss);

        int damage = battlePikachuMiss.damageOutput(battlePikachuMiss.getMoveSet().get(0), battlePikachuMiss);
        int expectedDamage = (int) Math.round(((42.0 * 100 * 115 / 105)/50.0 + 2) *
                battlePikachuMiss.getDamageRoll() / 100.0);

        if (battlePikachuMiss.getHitChance() <= battlePikachuMiss.getMoveSet().get(0).getAccuracy()) {
            assertEquals(expectedDamage, damage);
        } else {
            assertEquals(0, damage);
        }
    }

    @Test
    public void testDamageTaken() {
        battlePikachuOneMove.damageTaken(100);
        assertEquals(80, battlePikachuOneMove.getHP());
    }

    @Test
    public void testDamageTakenMultipleTimes() {
        battlePikachuOneMove.damageTaken(100);
        assertEquals(80, battlePikachuOneMove.getHP());
        battlePikachuOneMove.damageTaken(40);
        assertEquals(40, battlePikachuOneMove.getHP());
    }

    @Test
    public void testDamageTakenDamageGreaterThanHealth() {
        battlePikachuOneMove.damageTaken(190);
        assertEquals(0, battlePikachuOneMove.getHP());
    }

    @Test
    public void testStruggle() {
        pikachuNoPP = new Pokemon("Pikachu", "Electric", 35, 55, 50);
        pikachuNoPP.addMoveToMoveSet("Thunderbolt", 100, 1, 1);
        battlePikachuNoPP = new BattlingPokemon(pikachuNoPP);
        battlePikachuNoPP.getMoveSet().get(0).usedMove();

        int maxHP = battlePikachuNoPP.getHP();
        int damage = battlePikachuNoPP.struggle(battlePikachuNoPP);
        int expectedDamage = (int) Math.round(((42.0 * 50 * 115 / 105)/50.0 + 2)
                * battlePikachuNoPP.getDamageRoll() / 100.0);
        int recoil = expectedDamage / 2;

        assertEquals(expectedDamage, damage);
        assertEquals(maxHP - recoil, battlePikachuNoPP.getHP());
    }

    // EFFECTS: checks to see if Move m has the correct info
    private void checkMove(String name, int power, int pp, int accuracy, Move m) {
        assertEquals(name, m.getName());
        assertEquals(power, m.getPower());
        assertEquals(pp, m.getPP());
        assertEquals(accuracy, m.getAccuracy());
    }
}
