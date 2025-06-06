package ac.slime.utilities.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathUtil {

    public static final double GRAVITY = -0.08D;
    public static final double DRAG = 0.9800000190734863D;
    public static final double TERMINAL_VELOCITY = -3.92D;
    public static final double JUMP_MOTION = 0.42D;
    public static double horizontalDistance(Location from, Location to) {
        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }
    public static double verticalDistance(Location from, Location to) {
        return Math.abs(to.getY() - from.getY());
    }
    public static double totalDistance(Location from, Location to) {
        return from.distance(to);
    }
    public static double getSpeed(Vector velocity) {
        return Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());
    }
    public static double getTotalSpeed(Vector velocity) {
        return velocity.length();
    }
    public static double squaredDistance(Location from, Location to) {
        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();
        double dz = to.getZ() - from.getZ();
        return dx * dx + dy * dy + dz * dz;
    }
    public static Vector getDelta(Location from, Location to) {
        return to.toVector().subtract(from.toVector());
    }
    public static double applyGravity(double motionY) {
        return (motionY + GRAVITY) * DRAG;
    }
    public static double predictFallVelocity(double initialMotionY, int ticks) {
        double motion = initialMotionY;
        for (int i = 0; i < ticks; i++) {
            motion = applyGravity(motion);
            if (motion < TERMINAL_VELOCITY) break;
        }
        return motion;
    }
    public static double predictJumpHeight(int ticks) {
        double motionY = JUMP_MOTION;
        double y = 0;
        for (int i = 0; i < ticks; i++) {
            y += motionY;
            motionY = applyGravity(motionY);
        }
        return y;
    }
    public static Vector getAcceleration(Vector previousVelocity, Vector currentVelocity) {
        return currentVelocity.clone().subtract(previousVelocity);
    }
    public static boolean exceedsTerminalVelocity(double motionY) {
        return motionY < TERMINAL_VELOCITY;
    }
    public static double estimateFriction(Vector velocity, Vector previousVelocity) {
        double currentSpeed = getSpeed(velocity);
        double previousSpeed = getSpeed(previousVelocity);
        if (previousSpeed == 0) return 0.0;
        return currentSpeed / previousSpeed;
    }
    public static double getAngle(Vector from, Vector to) {
        double dot = from.dot(to);
        double lengths = from.length() * to.length();
        if (lengths == 0) return 0.0;
        double cos = dot / lengths;
        return Math.toDegrees(Math.acos(Math.max(-1.0, Math.min(1.0, cos))));
    }
    public static double hypot(double a, double b) {
        return Math.sqrt(a * a + b * b);
    }
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public static double round(double value, int decimals) {
        double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }
    public static float wrapAngleTo180(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) angle -= 360.0f;
        if (angle < -180.0f) angle += 360.0f;
        return angle;
    }
}