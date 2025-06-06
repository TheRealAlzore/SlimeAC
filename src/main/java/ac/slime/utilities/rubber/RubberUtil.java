package ac.slime.utilities.rubber;

import ac.slime.player.SlimePlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RubberUtil {

    public static void fail(SlimePlayer slimePlayer) {
        Player player = slimePlayer.getBukkitPlayer();
        Location lastLocation = slimePlayer.getLastLocation();
        if (lastLocation != null) {
            player.teleport(lastLocation);
        } else {
            player.teleport(player.getLocation());
        }
    }
}