package ac.slime.utilities.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtil {

    public static double horizontalDistance(Vector from, Vector to) {
        return Math.hypot(to.getX() - from.getX(), to.getZ() - from.getZ());
    }
    public static double distance(Vector from, Vector to) {
        return from.clone().subtract(to).length();
    }
    public static boolean nearlyEqual(Vector v1, Vector v2, double tolerance) {
        return v1.clone().subtract(v2).lengthSquared() <= tolerance * tolerance;
    }
    public static Vector direction(Location from, Location to) {
        return to.toVector().subtract(from.toVector()).normalize();
    }
    public static Vector horizontalize(Vector vec) {
        return new Vector(vec.getX(), 0.0, vec.getZ());
    }
    public static Vector getDirectionVector(Location loc, boolean ignoreY) {
        Vector dir = loc.getDirection().normalize();
        if (ignoreY) {
            dir.setY(0);
            dir.normalize();
        }
        return dir;
    }
    public static Vector applyFriction(Vector vec, double factor) {
        return vec.clone().multiply(1.0 - factor);
    }
    public static Vector vector(Location loc) {
        return new Vector(loc.getX(), loc.getY(), loc.getZ());
    }
}