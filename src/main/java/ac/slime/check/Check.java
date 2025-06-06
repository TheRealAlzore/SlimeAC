package ac.slime.check;

import ac.slime.SlimePlugin;
import ac.slime.player.SlimePlayer;
import ac.slime.utilities.chat.ChatUtil;
import ac.slime.utilities.rubber.RubberUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class Check {

    protected final SlimePlayer slimePlayer;
    protected final String name;
    protected final String type;
    private final FileConfiguration config;
    private final FileConfiguration checksConfig;
    private final boolean enabled;

    public Check(SlimePlayer slimePlayer, String name, String type, FileConfiguration config) {
        this.slimePlayer = slimePlayer;
        this.name = name;
        this.type = type;
        this.config = config != null ? config : SlimePlugin.getInstance().getConfigFile().getConfig();
        this.checksConfig = SlimePlugin.getInstance().getChecksFile().getChecksConfig();
        this.enabled = checksConfig.getBoolean("checks." + name + ".enabled", true);
    }
    public abstract void handle();
    public void fail() {
        if (!this.isEnabled()) return;
        Player bukkitPlayer = slimePlayer.getBukkitPlayer();
        if (bukkitPlayer.getGameMode() == GameMode.CREATIVE || bukkitPlayer.getGameMode() == GameMode.SPECTATOR)
            return;
        int vl = slimePlayer.addViolation(this.name);
        String nameForMessage = this.name;
        if (this.name.length() > 1 && Character.isLetter(this.name.charAt(this.name.length() - 1))) {
            nameForMessage = this.name.substring(0, this.name.length() - 1);
        }
        String message = config.getString("flag-message", "&aSlimeAC &7| &f{player} &fFailed &b{check} &7(&b{type}&7) &c{vl}&7x")
                .replace("{player}", bukkitPlayer.getName())
                .replace("{check}", nameForMessage)
                .replace("{type}", this.type)
                .replace("{vl}", String.valueOf(vl));
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("slime.alerts")) {
                online.sendMessage(ChatUtil.color(message));
            }
        }
        if (checksConfig.getBoolean("checks." + this.name + ".rubber", false)) {
            RubberUtil.fail(slimePlayer);
        }
        int maxVl = checksConfig.getInt("checks." + this.name + ".max-violation", -1);
        if (maxVl > 0 && vl >= maxVl) {
            String punishment = checksConfig.getString("checks." + this.name + ".punishment", "kick {player} Cheating")
                    .replace("{player}", bukkitPlayer.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), punishment);
            slimePlayer.resetViolation(this.name);
        }
    }
    public boolean isEnabled() {
        return this.enabled;
    }
    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }
}