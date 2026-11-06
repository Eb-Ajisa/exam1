package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CombatRulesTest {

    @BeforeEach 
    void setUp() {
    }

    @AfterEach 
    void tearDown() {
    }

    @ParameterizedTest
    @CsvSource({
        "50, 49, 100, 99",
        "50, 51, 100, 100",
        "50, 50, 100, 100",
        "50, -49, 100, 1",
        "50, -51, 100, -1",
        "50, -50, 100, 0",
    })
    void testCalculateNewHp(int currentHp, int amount, int maxHp, int expected) {
        int newHp = CombatRules.calculateNewHp(currentHp, maxHp, amount);

        assertEquals(expected, newHp);

    }

    @ParameterizedTest 
    @ValueSource (ints = {0, -1,})
    void testIsDeadTrue(int hp) {
        boolean isDead = CombatRules.isDead(hp);

        assertTrue(isDead);

    }

    @ParameterizedTest 
    @ValueSource (ints = {1, 99, 100})
    void testIsDeadFalse(int hp) {
        boolean isDead = CombatRules.isDead(hp);

        assertFalse(isDead);

    }
}
