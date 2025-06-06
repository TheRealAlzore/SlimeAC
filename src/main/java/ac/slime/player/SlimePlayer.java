package ac.slime.player;

import ac.slime.check.Check;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SlimePlayer {

    private final Player bukkitPlayer;
    private Location lastLocation;
    private final Map<String, Integer> violations = new HashMap<>();
    private final Set<Check> checks = new HashSet<>();

    public SlimePlayer(Player player) {
        this.bukkitPlayer = player;
        this.lastLocation = player.getLocation().clone();
    }
    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }
    public Location getLastLocation() {
        return lastLocation;
    }
    public void setLastLocation(Location location) {
        this.lastLocation = location.clone();
    }
    public int addViolation(String checkName) {
        int newVl = violations.getOrDefault(checkName, 0) + 1;
        violations.put(checkName, newVl);
        return newVl;
    }
    public void resetViolation(String checkName) {
        violations.remove(checkName);
    }
    public void addCheck(Check check) {
        checks.add(check);
    }
    public Set<Check> getChecks() {
        return checks;
    }
}