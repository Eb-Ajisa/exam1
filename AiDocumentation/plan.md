# JoshHack Software Quality Improvement Plan

## Priorities retained

1. Expand behavioral tests around core game rules and state transitions.
2. Add tests for AI, world/pathfinding, item, spell, and UI interaction logic.
4. Resolve or explicitly assess SpotBugs representation-exposure findings.

Workstreams 2 and 4 remain deferred while workstream 1 is implemented
individually.

## Workstream 1: Core behavioral tests

### Proposal

Expand `JoshHack/src/test/java/game/CreatureTest.java` with deterministic tests
for:

- HP damage/healing boundaries, death, and cause-of-death state.
- Mana and food boundary behavior.
- XP accumulation and level-up transitions.
- Weapon/armor equipment and derived attack/defense values.
- Effect start, update, end, and removal lifecycle.
- Melee combat damage bounds and food consumption.

Use the existing small `World` fixture and a recording `CreatureAi`/`Effect`
test helper. Production code should remain unchanged unless a minimal seam is
required for deterministic testing; no intentional behavior change is part of
this workstream.

### Evidence

The original suite had only 19 tests, with `CreatureTest` covering two HP
cases. PIT reported 10% line coverage, 3% mutation coverage, and 40% test
strength, with broad `Creature.java` no-coverage and surviving mutants.

### Validation

Run:

```powershell
cd JoshHack
.\mvnw.cmd test
.\mvnw.cmd test-compile org.pitest:pitest-maven:1.20.2:mutationCoverage
```

The expected result is a passing JUnit suite and measurable improvement in PIT
coverage and mutation detection.

## Later workstreams

### Workstream 2

Add deterministic tests for AI subclasses, world/tile navigation, pathfinding,
field of view, item/spell effects, and UI/controller input handling.

### Workstream 4

Review the 19 `EI_EXPOSE_REP2` and 8 `EI_EXPOSE_REP` findings. Define ownership
contracts for mutable arrays, lists, and model references before choosing
defensive copies, read-only views, or narrowly justified suppressions.
