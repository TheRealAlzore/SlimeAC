package ac.slime.compatibility;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class Folia {
    private static boolean folia;

    public static boolean isFolia() {
        return folia;
    }

    public static void runTaskAsync(Plugin plugin, Runnable run) {
        if (!folia) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, run);
            return;
        }
        Executors.defaultThreadFactory().newThread(run).start();
    }
    public static void runTaskTimerAsync(Plugin plugin, Consumer<Object> run, long delay, long period) {
        if (!folia) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> run.accept(null), delay, period);
            return;
        }
        try {
            Method getSchedulerMethod = Server.class.getDeclaredMethod("getGlobalRegionScheduler", new Class[0]);
            getSchedulerMethod.setAccessible(true);
            Object globalRegionScheduler = getSchedulerMethod.invoke(Bukkit.getServer(), new Object[0]);
            Class<?> schedulerClass = globalRegionScheduler.getClass();
            Method executeMethod = schedulerClass.getDeclaredMethod("runAtFixedRate", Plugin.class, Consumer.class, Long.TYPE, Long.TYPE);
            executeMethod.setAccessible(true);
            executeMethod.invoke(globalRegionScheduler, plugin, run, delay, period);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void runTask(Plugin plugin, Runnable run) {
        if (!folia) {
            Bukkit.getScheduler().runTask(plugin, () -> run.run());
            return;
        }
        try {
            Method getSchedulerMethod = Server.class.getDeclaredMethod("getGlobalRegionScheduler", new Class[0]);
            getSchedulerMethod.setAccessible(true);
            Object globalRegionScheduler = getSchedulerMethod.invoke(Bukkit.getServer(), new Object[0]);
            Class<?> schedulerClass = globalRegionScheduler.getClass();
            Method executeMethod = schedulerClass.getDeclaredMethod("run", Plugin.class, Consumer.class);
            executeMethod.setAccessible(true);
            executeMethod.invoke(globalRegionScheduler, plugin, run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
        } catch (ClassNotFoundException e) {
            folia = false;
        }
    }
}