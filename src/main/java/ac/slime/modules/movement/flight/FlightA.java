package ac.slime.modules.movement.flight;

import ac.slime.check.Check;
import ac.slime.player.SlimePlayer;
import ac.slime.processors.GroundProcessor;
import ac.slime.compensation.Tps;
import ac.slime.compensation.Ping;
import ac.slime.utilities.math.MathUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class FlightA extends Check {

    private int hoverTicks;

    public FlightA(SlimePlayer slimePlayer, FileConfiguration checksConfig) {
        super(slimePlayer, "FlightA", "A", checksConfig);
    }
    public void handle() {
        if (!this.isEnabled()) return;
        Player player = slimePlayer.getBukkitPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getAllowFlight()) return;
        if (player.hasPotionEffect(PotionEffectType.JUMP)) return;
        if (holdingMaceItem(player)) return;
        if (Tps.getTPS() < 17.0 || Ping.getPing(player) > 300) return;
        if (player.isOnGround() || GroundProcessor.isLegitimatelyOnGround(slimePlayer)) {
            hoverTicks = 0;
            return;
        }
        Vector velocity = player.getVelocity();
        double horizontalSpeed = MathUtil.getSpeed(velocity);
        double verticalSpeed = Math.abs(velocity.getY());
        if (horizontalSpeed > 0.45 || verticalSpeed > 0.42) {
            fail();
            return;
        }
        if (verticalSpeed < 0.005) {
            hoverTicks++;
            if (hoverTicks > 6) {
                fail();
            }
        } else {
            hoverTicks = 0;
        }
    }
    private boolean holdingMaceItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        return item != null &&
                item.getType() != Material.AIR &&
                item.hasItemMeta() &&
                item.getItemMeta().hasDisplayName() &&
                item.getItemMeta().getDisplayName().toLowerCase().contains("mace");
    }
}