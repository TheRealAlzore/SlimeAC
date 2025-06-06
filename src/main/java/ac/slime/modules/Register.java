package ac.slime.modules;

import ac.slime.SlimePlugin;
import ac.slime.check.Check;
import ac.slime.player.SlimePlayer;
import ac.slime.modules.movement.flight.FlightA;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.function.BiFunction;

public final class Register {

    private final Map<Class<? extends Check>, BiFunction<SlimePlayer, FileConfiguration, Check>> checkFactories = new HashMap<>();

    public Register(SlimePlugin plugin) {
        checkFactories.put(FlightA.class, FlightA::new);
    }
    public List<Check> createChecks(SlimePlayer slimePlayer, FileConfiguration config) {
        List<Check> checks = new ArrayList<>();
        for (BiFunction<SlimePlayer, FileConfiguration, Check> factory : checkFactories.values()) {
            checks.add(factory.apply(slimePlayer, config));
        }
        return checks;
    }
    public Map<Class<? extends Check>, BiFunction<SlimePlayer, FileConfiguration, Check>> getCheckFactories() {
        return checkFactories;
    }
}