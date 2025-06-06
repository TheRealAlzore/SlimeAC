package ac.slime.events;

import ac.slime.SlimePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SlimePlugin.getInstance().removeSlimePlayer(event.getPlayer());
    }
}