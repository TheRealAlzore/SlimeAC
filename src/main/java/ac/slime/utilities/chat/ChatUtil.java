package ac.slime.utilities.chat;

import org.bukkit.ChatColor;

public class ChatUtil {

    public static String color(String output) {
        return ChatColor.translateAlternateColorCodes('&', output);
    }
}
