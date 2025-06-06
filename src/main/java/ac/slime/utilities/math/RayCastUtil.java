package ac.slime.utilities.math;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class RayCastUtil {

    public static boolean rayHitsEntity(Location start, Vector direction, Entity target, double maxReach) {
        Vector ray = direction.clone().normalize();
        Vector to = target.getLocation().toVector();
        Vector hitboxSize = new Vector(0.4, 1.8, 0.4);
        for (double i = 0; i < maxReach; i += 0.1) {
            Vector point = start.toVector().add(ray.clone().multiply(i));
            Vector min = to.clone().subtract(hitboxSize);
            Vector max = to.clone().add(hitboxSize);
            if (isWithinBoundingBox(point, min, max)) {
                return true;
            }
        }
        return false;
    }
    private static boolean isWithinBoundingBox(Vector point, Vector min, Vector max) {
        return point.getX() >= min.getX() && point.getX() <= max.getX()
                && point.getY() >= min.getY() && point.getY() <= max.getY()
                && point.getZ() >= min.getZ() && point.getZ() <= max.getZ();
    }
}