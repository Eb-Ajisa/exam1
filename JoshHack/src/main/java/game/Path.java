package game;

import java.util.List;
import java.util.Collections;

public class Path {

	private static PathFinder pf = new PathFinder();
	
	private List<Point> points;
	public List<Point> points() {
		return points == null ? null : Collections.unmodifiableList(points);
	}
	
	public Path(Creature creature, int x, int y){
		points = pf.findPath(creature, new Point(creature.x, creature.y, creature.z), new Point(x, y, creature.z), 300);
	}
}
