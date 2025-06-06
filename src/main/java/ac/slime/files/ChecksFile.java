package ac.slime.files;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ChecksFile {
    private final JavaPlugin plugin;
    private FileConfiguration checksConfig;
    private File checksFile;

    public ChecksFile(JavaPlugin plugin) {
        this.plugin = plugin;
        this.load();
    }
    public void load() {
        this.checksFile = new File(this.plugin.getDataFolder(), "checks.yml");
        if (!this.checksFile.exists()) {
            this.plugin.saveResource("checks.yml", false);
        }
        this.checksConfig = YamlConfiguration.loadConfiguration((File)this.checksFile);
    }
    public FileConfiguration getChecksConfig() {
        return this.checksConfig;
    }
    public void save() {
        try {
            this.checksConfig.save(this.checksFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reload() {
        this.load();
    }
}