package ac.slime;

import ac.slime.check.Check;
import ac.slime.check.CheckDispatcher;
import ac.slime.compatibility.Folia;
import ac.slime.events.JoinEvent;
import ac.slime.events.LeaveEvent;
import ac.slime.files.ChecksFile;
import ac.slime.files.ConfigFile;
import ac.slime.handlers.MovementHandler;
import ac.slime.modules.Register;
import ac.slime.player.SlimePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SlimePlugin extends JavaPlugin {

    private static SlimePlugin instance;
    private ConfigFile configFile;
    private ChecksFile checksFile;
    private final Map<Player, SlimePlayer> playerMap = new HashMap<>();
    private Register checkRegister;
    private CheckDispatcher checkDispatcher;

    @Override
    public void onEnable() {
        instance = this;
        Folia.isFolia();
        this.configFile = new ConfigFile(this);
        this.checksFile = new ChecksFile(this);
        this.checkRegister = new Register(this);
        this.checkDispatcher = new CheckDispatcher();
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
        getServer().getPluginManager().registerEvents(new MovementHandler(), this);
    }
    @Override
    public void onDisable() {
        playerMap.clear();
    }
    public static SlimePlugin getInstance() {
        return instance;
    }
    public ConfigFile getConfigFile() {
        return configFile;
    }
    public ChecksFile getChecksFile() {
        return checksFile;
    }
    public SlimePlayer getSlimePlayer(Player player) {
        return playerMap.computeIfAbsent(player, p -> {
            SlimePlayer slimePlayer = new SlimePlayer(p);
            List<Check> checks = checkRegister.createChecks(slimePlayer, checksFile.getChecksConfig());
            for (Check check : checks) {
                slimePlayer.addCheck(check);
            }
            return slimePlayer;
        });
    }
    public void removeSlimePlayer(Player player) {
        playerMap.remove(player);
    }
    public Register getCheckRegister() {
        return checkRegister;
    }
    public CheckDispatcher getCheckDispatcher() {
        return checkDispatcher;
    }
}