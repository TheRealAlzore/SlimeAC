package ac.slime.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigFile {
    private final JavaPlugin plugin;
    private FileConfiguration config;

    public ConfigFile(JavaPlugin plugin) {
        this.plugin = plugin;
        this.load();
    }
    public void load() {
        this.plugin.saveDefaultConfig();
        this.config = this.plugin.getConfig();
    }
    public FileConfiguration getConfig() {
        return this.config;
    }
}