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

## Workstream 4: Representation-exposure hardening

**Status:** Completed with intentional shared-reference findings documented

Hardened mutable collection/array boundaries by:

- Returning an array snapshot from `Inventory.getItems()`.
- Returning unmodifiable views from `Creature.effects()`,
  `Item.writtenSpells()`, `Line.getPoints()`, and `Path.points()`.
- Updating `InventoryTest` to verify callers cannot mutate inventory through a
  returned array snapshot.

The remaining SpotBugs representation findings are intentional shared game
model references: creatures retain their world, AI, equipment, and controller
links; AI/UI objects retain the model objects they operate on. Copying these
objects would break identity-based gameplay behavior, so they remain assessed
for a later targeted ownership/API redesign rather than being suppressed
broadly.

### Validation results

- JUnit: 37 tests passed with 0 failures, errors, or skips.
- SpotBugs: analysis completed successfully; findings reduced from 38 to 33.
- Remaining findings: 19 `EI_EXPOSE_REP2`, 3 intentional object getter
  findings, and the pre-existing non-representation findings.
- PIT: completed successfully with 36% line coverage, 23% mutation coverage,
  and 62% test strength.

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
