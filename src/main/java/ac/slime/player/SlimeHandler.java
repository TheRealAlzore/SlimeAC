package ac.slime.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SlimeHandler {

    private static final Map<UUID, SlimePlayer> players = new HashMap<>();

    public static void addPlayer(SlimePlayer slimePlayer) {
        players.put(slimePlayer.getBukkitPlayer().getUniqueId(), slimePlayer);
    }
    public static void removePlayer(UUID uuid) {
        players.remove(uuid);
    }
    public static SlimePlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }
    public static boolean contains(UUID uuid) {
        return players.containsKey(uuid);
    }
}