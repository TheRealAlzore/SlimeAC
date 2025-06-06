package ac.slime.compensation;

import java.lang.reflect.Field;
import org.bukkit.entity.Player;

public class Ping {
    public static int getPing(Player player) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Field pingField = handle.getClass().getDeclaredField("ping");
            return pingField.getInt(handle);
        } catch (Exception e) {
            return 0;
        }
    }
}