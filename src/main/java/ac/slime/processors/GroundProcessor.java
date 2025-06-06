package ac.slime.processors;

import ac.slime.player.SlimePlayer;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.HashSet;
import java.util.Set;

public class GroundProcessor {

    private static final double GROUND_EPSILON = 0.0625;
    private static final Set<Material> SOLID_MATERIALS = new HashSet<>();
    static {
        for (Material mat : Material.values()) {
            if (mat.isSolid() && mat.isBlock() && mat.isOccluding()) {
                SOLID_MATERIALS.add(mat);
            }
        }
    }
    public static boolean isMathematicallyOnGround(Location location) {
        double y = location.getY();
        return Math.abs(y - Math.floor(y)) < GROUND_EPSILON;
    }
    public static boolean isStandingOnSolidBlock(Player player) {
        Location loc = player.getLocation().clone().subtract(0, 0.01, 0);
        Block block = loc.getBlock();
        return SOLID_MATERIALS.contains(block.getType());
    }
    public static boolean isGhostBlockBelow(Player player) {
        Location loc = player.getLocation();
        Block blockBelow = loc.clone().subtract(0, 0.1, 0).getBlock();
        return !blockBelow.getType().isSolid() && isMathematicallyOnGround(loc);
    }
    public static boolean isSpoofingGround(SlimePlayer slimePlayer) {
        Player player = slimePlayer.getBukkitPlayer();
        Location loc = player.getLocation();
        boolean solid = player.isOnGround() && !isStandingOnSolidBlock(player);
        boolean ghost = player.isOnGround() && isGhostBlockBelow(player);
        return solid || ghost;
    }
    public static boolean rayCastGround(Player player) {
        Location eye = player.getEyeLocation();
        Vector direction = new Vector(0, -1, 0);
        World world = player.getWorld();
        RayTraceResult result = world.rayTraceBlocks(eye, direction, 2.0, FluidCollisionMode.NEVER, true);
        return result != null && result.getHitBlock() != null && SOLID_MATERIALS.contains(result.getHitBlock().getType());
    }
    public static boolean boundingBoxTouchingGround(Player player) {
        BoundingBox box = player.getBoundingBox();
        World world = player.getWorld();
        for (double x = box.getMinX(); x <= box.getMaxX(); x += 0.3) {
            for (double z = box.getMinZ(); z <= box.getMaxZ(); z += 0.3) {
                Location below = new Location(world, x, box.getMinY() - 0.01, z);
                if (SOLID_MATERIALS.contains(below.getBlock().getType())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isLegitimatelyOnGround(SlimePlayer slimePlayer) {
        Player player = slimePlayer.getBukkitPlayer();

        return isStandingOnSolidBlock(player)
                || boundingBoxTouchingGround(player)
                || rayCastGround(player);
    }
    public static boolean shouldFlagGroundSpoof(SlimePlayer slimePlayer) {
        return slimePlayer.getBukkitPlayer().isOnGround() && !isLegitimatelyOnGround(slimePlayer);
    }
    public static boolean isCollisionFlight(SlimePlayer slimePlayer) {
        Player player = slimePlayer.getBukkitPlayer();
        Location loc = player.getLocation();
        boolean onGround = player.isOnGround();
        boolean noBlockBelow = !isStandingOnSolidBlock(player) && !boundingBoxTouchingGround(player);
        boolean flatMotion = Math.abs(loc.getY() - slimePlayer.getLastLocation().getY()) < 0.01;
        boolean lowYVelocity = Math.abs(player.getVelocity().getY()) < 0.01;
        return !onGround && noBlockBelow && flatMotion && lowYVelocity;
    }
}