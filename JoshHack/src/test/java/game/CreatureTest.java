
package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

public class CreatureTest {
    
    private World world;
    private Creature creature;
    @BeforeEach
    void setUp() {
        Tile[][][] tiles = new Tile[3][3][1];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                tiles[x][y][0] = Tile.FLOOR;
            }
        }

        world = new World(tiles);

        creature = new Creature(world, '@', Color.GRAY, "josh", 100, 10, 5);

        creature.x = 1;
        creature.y = 1;
        creature.z = 0;
        new CreatureAi(creature);
    }

    @Test
    void testModifyHp() {
        creature.modifyHp(10, "spiders");

        assertEquals(100, creature.hp());

    }

    @Test 
    void testModifyHpDeath() {
        creature.modifyHp(-100, "spiders");

        assertEquals(0, creature.hp());
        assertEquals("spiders", creature.causeOfDeath());
    }

    @Test
    void testModifyHpDoesNotExceedMaximum() {
        creature.modifyHp(-50, "spiders");
        creature.modifyHp(75, "healing");

        assertEquals(100, creature.hp());
        assertEquals("healing", creature.causeOfDeath());
    }

    @Test
    void testManaIsBoundedByZeroAndMaximum() {
        creature.modifyMana(100);
        assertEquals(5, creature.mana());

        creature.modifyMana(-100);
        assertEquals(0, creature.mana());
    }

    @Test
    void testOvereatingUpdatesFoodCapacityAndDamagesCreature() {
        creature.modifyFood(1000);

        assertEquals(1333, creature.maxFood());
        assertEquals(1333, creature.food());
        assertEquals(99, creature.hp());
    }

    @Test
    void testPlayerStarvesWhenFoodReachesZero() {
        creature.modifyFood(-1000);

        assertTrue(creature.hp() < 1);
        assertEquals("Starved to death.", creature.causeOfDeath());
    }

    @Test
    void testLevelUpAfterGainingEnoughExperience() {
        RecordingCreatureAi ai = new RecordingCreatureAi(creature);

        creature.modifyXp(26);

        assertEquals(26, creature.xp());
        assertEquals(2, creature.level());
        assertEquals(1, ai.levelUps);
    }

    @Test
    void testEquipmentChangesDerivedCombatValues() {
        Item weapon = new Item('/', Color.WHITE, "sword", null);
        weapon.modifyAttackValue(4);
        Item armor = new Item('[', Color.WHITE, "armor", null);
        armor.modifyDefenseValue(3);

        creature.equip(weapon);
        creature.equip(armor);

        assertSame(weapon, creature.weapon());
        assertSame(armor, creature.armor());
        assertEquals(14, creature.attackValue());
        assertEquals(8, creature.defenseValue());
        assertTrue(creature.inventory().contains(weapon));
        assertTrue(creature.inventory().contains(armor));

        creature.unequip(weapon);
        assertEquals(10, creature.attackValue());
        assertTrue(creature.inventory().contains(weapon));
    }

    @Test
    void testEffectStartsUpdatesAndEnds() {
        RecordingEffect effect = new RecordingEffect(1);
        Item potion = new Item('!', Color.WHITE, "potion", null);
        potion.setQuaffEffect(effect);
        creature.inventory().add(potion);

        creature.quaff(potion);

        assertEquals(1, effect.starts);
        assertEquals(1, creature.effects().size());
        assertSame(effect, creature.effects().get(0));
        assertFalse(creature.inventory().contains(potion));

        creature.update();

        assertEquals(1, effect.updates);
        assertEquals(1, effect.ends);
        assertTrue(creature.effects().isEmpty());
    }

    @Test
    void testMeleeAttackDealsBoundedDamageAndConsumesFood() {
        Creature target = new Creature(world, 'g', Color.GREEN, "goblin", 100, 5, 5);
        int targetHpBefore = target.hp();
        int foodBefore = creature.food();

        creature.meleeAttack(target);

        int damage = targetHpBefore - target.hp();
        assertTrue(damage >= 1 && damage <= 6);
        assertEquals(foodBefore - 2, creature.food());
    }

    private static class RecordingCreatureAi extends CreatureAi {
        private int levelUps;

        RecordingCreatureAi(Creature creature) {
            super(creature);
        }

        @Override
        public void onGainLevel() {
            levelUps++;
        }
    }

    private static class RecordingEffect extends Effect {
        private int starts;
        private int updates;
        private int ends;

        RecordingEffect(int duration) {
            super(duration);
        }

        @Override
        public void start(Creature creature) {
            starts++;
        }

        @Override
        public void update(Creature creature) {
            updates++;
            super.update(creature);
        }

        @Override
        public void end(Creature creature) {
            ends++;
        }
    }
}
