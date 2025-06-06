package ac.slime.utilities.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PredictionUtil {

    public static Location predictLocation(Location currentLocation, Vector velocity, int ticksAhead) {
        Vector predicted = velocity.clone().multiply(ticksAhead);
        return currentLocation.clone().add(predicted);
    }
    public static Vector predictVelocityWithFriction(Vector velocity, int ticks, double friction) {
        Vector result = velocity.clone();
        for (int i = 0; i < ticks; i++) {
            result.multiply(1 - friction);
        }
        return result;
    }
    public static Vector estimateVelocity(Location from, Location to) {
        return to.toVector().subtract(from.toVector());
    }
}