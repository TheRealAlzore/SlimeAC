package ac.slime.events;

import ac.slime.SlimePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SlimePlugin.getInstance().getSlimePlayer(event.getPlayer());
    }
}