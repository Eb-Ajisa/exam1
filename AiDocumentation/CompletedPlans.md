# Completed Plans

## Workstream 1: Core behavioral tests

**Status:** Completed

Expanded `JoshHack/src/test/java/game/CreatureTest.java` with coverage for:

- HP damage/healing boundaries, death, and cause-of-death state.
- Mana and food boundary behavior.
- XP accumulation and level-up transitions.
- Weapon/armor equipment and derived attack/defense values.
- Effect start, update, end, and removal lifecycle.
- Melee combat damage bounds and food consumption.

### Validation results

- JUnit: 27 tests passed with 0 failures, errors, or skips.
- PIT line coverage: improved from 10% to 22%.
- PIT mutation coverage: improved from 3% to 10%.
- PIT test strength: improved from 40% to 49%.
- No production Java source was changed.

## Workstream 2: Navigation and item/spell behavioral tests

**Status:** Completed

Added deterministic tests in:

- `JoshHack/src/test/java/game/NavigationTest.java`
- `JoshHack/src/test/java/game/ItemSpellTest.java`

Coverage includes path construction, field-of-view visibility and wall
blocking, AI item-name and line-of-sight behavior, geometry and tile
classification, item stat/details behavior, written spells, spell effect
copying, and quaff effects.

### Validation results

- JUnit: 37 tests passed with 0 failures, errors, or skips.
- PIT line coverage: improved from 22% to 36%.
- PIT mutation coverage: improved from 10% to 23%.
- PIT test strength: improved from 49% to 61%.
- No production Java source was changed.
