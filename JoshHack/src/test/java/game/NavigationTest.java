package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.List;

import org.junit.jupiter.api.Test;

class NavigationTest {

    @Test
    void pathFinderReturnsAdjacentWalkablePath() {
        World world = worldWithFloor();
        Creature creature = creatureAt(world, 1, 1);
        Path path = new Path(creature, 4, 4);

        assertNotNull(path.points());
        assertFalse(path.points().isEmpty());

        Point previous = new Point(creature.x, creature.y, creature.z);
        for (Point point : path.points()) {
            assertTrue(Math.abs(point.x - previous.x) <= 1);
            assertTrue(Math.abs(point.y - previous.y) <= 1);
            assertEquals(0, point.z);
            assertTrue(world.tile(point.x, point.y, point.z).isGround());
            previous = point;
        }
        assertEquals(new Point(4, 4, 0), previous);
    }

    @Test
    void fieldOfViewRevealsGroundAndStopsAtWalls() {
        Tile[][][] tiles = filledTiles(5, 1, Tile.FLOOR);
        tiles[2][0][0] = Tile.WALL;
        FieldOfView fieldOfView = new FieldOfView(new World(tiles));

        fieldOfView.update(0, 0, 0, 4);

        assertTrue(fieldOfView.isVisible(0, 0, 0));
        assertTrue(fieldOfView.isVisible(1, 0, 0));
        assertTrue(fieldOfView.isVisible(2, 0, 0));
        assertFalse(fieldOfView.isVisible(3, 0, 0));
        assertEquals(Tile.WALL, fieldOfView.tile(2, 0, 0));
        assertEquals(Tile.UNKNOWN, fieldOfView.tile(3, 0, 0));
    }

    @Test
    void creatureAiTracksItemNamesAndVisibility() {
        World world = worldWithFloor();
        Creature creature = creatureAt(world, 1, 1);
        CreatureAi ai = new CreatureAi(creature);
        Item potion = new Item('!', Color.WHITE, "potion", "red bottle");

        assertEquals("red bottle", ai.getName(potion));
        ai.setName(potion, "healing potion");

        assertEquals("healing potion", ai.getName(potion));
        assertTrue(ai.canSee(3, 1, 0));
        assertFalse(ai.canSee(1, 1, 1));
    }

    @Test
    void creatureAiCannotSeeThroughWall() {
        Tile[][][] tiles = filledTiles(5, 1, Tile.FLOOR);
        tiles[2][0][0] = Tile.WALL;
        World world = new World(tiles);
        Creature creature = creatureAt(world, 0, 0);
        CreatureAi ai = new CreatureAi(creature);

        assertFalse(ai.canSee(4, 0, 0));
        assertTrue(ai.canSee(1, 0, 0));
    }

    @Test
    void lineAndPointProvideExpectedGeometry() {
        List<Point> points = new Line(0, 0, 2, 0).getPoints();

        assertEquals(3, points.size());
        assertEquals(new Point(0, 0, 0), points.get(0));
        assertEquals(new Point(2, 0, 0), points.get(2));
        assertEquals(8, new Point(1, 1, 0).neighbors8().size());
    }

    @Test
    void tileClassifiesGroundAndDiggableTiles() {
        assertTrue(Tile.FLOOR.isGround());
        assertFalse(Tile.WALL.isGround());
        assertFalse(Tile.BOUNDS.isGround());
        assertTrue(Tile.WALL.isDiggable());
        assertFalse(Tile.FLOOR.isDiggable());
    }

    private static Creature creatureAt(World world, int x, int y) {
        Creature creature = new Creature(world, '@', Color.GRAY, "hero", 100, 10, 5);
        creature.x = x;
        creature.y = y;
        creature.z = 0;
        return creature;
    }

    private static World worldWithFloor() {
        return new World(filledTiles(5, 5, Tile.FLOOR));
    }

    private static Tile[][][] filledTiles(int width, int height, Tile tile) {
        Tile[][][] tiles = new Tile[width][height][1];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y][0] = tile;
            }
        }
        return tiles;
    }
}
