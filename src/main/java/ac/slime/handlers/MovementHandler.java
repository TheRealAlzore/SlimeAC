package ac.slime.handlers;

import ac.slime.SlimePlugin;
import ac.slime.player.SlimePlayer;
import ac.slime.check.CheckDispatcher;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementHandler implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        SlimePlayer slimePlayer = SlimePlugin.getInstance().getSlimePlayer(event.getPlayer());
        CheckDispatcher dispatcher = SlimePlugin.getInstance().getCheckDispatcher();
        dispatcher.handle(slimePlayer);
    }
}