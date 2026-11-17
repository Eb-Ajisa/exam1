package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class ItemSpellTest {

    @Test
    void itemTracksCombatFoodAndAppearanceValues() {
        Item item = new Item('!', Color.WHITE, "potion", "blue bottle");

        item.modifyAttackValue(2);
        item.modifyDefenseValue(3);
        item.modifyThrownAttackValue(4);
        item.modifyRangedAttackValue(5);
        item.modifyFoodValue(6);

        assertEquals("blue bottle", item.appearance());
        assertEquals(2, item.attackValue());
        assertEquals(3, item.defenseValue());
        assertEquals(5, item.rangedAttackValue());
        assertEquals(5, item.thrownAttackValue());
        assertEquals(6, item.foodValue());
        assertTrue(item.details().contains("attack:2"));
        assertTrue(item.details().contains("defense:3"));
        assertTrue(item.details().contains("food:6"));
    }

    @Test
    void itemDefaultsAppearanceToNameAndStoresWrittenSpells() {
        Item item = new Item('?', Color.WHITE, "spellbook", null);
        Effect effect = new Effect(2);

        item.addWrittenSpell("spark", 2, effect);

        assertEquals("spellbook", item.appearance());
        assertEquals(1, item.writtenSpells().size());
        assertEquals("spark", item.writtenSpells().get(0).name());
        assertEquals(2, item.writtenSpells().get(0).manaCost());
    }

    @Test
    void spellCopiesItsEffectWhenRead() {
        Effect effect = new Effect(3);
        Spell spell = new Spell("freeze", 2, effect);

        Effect copy = spell.effect();

        assertNotSame(effect, copy);
        assertEquals(spell.effect().isDone(), copy.isDone());
        assertEquals("freeze", spell.name());
        assertEquals(2, spell.manaCost());
        assertTrue(spell.requiresTarget());
    }

    @Test
    void itemCanStoreAndReturnQuaffEffect() {
        Item item = new Item('!', Color.WHITE, "potion", null);
        Effect effect = new Effect(1);

        item.setQuaffEffect(effect);

        assertSame(effect, item.quaffEffect());
    }
}
