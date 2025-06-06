package ac.slime.compensation;

public class Tps {
    private static final int TICK_COUNT = 600;
    private static final long[] TICKS = new long[600];
    private static int currentTick = 0;

    public static void tick() {
        Tps.TICKS[Tps.currentTick % 600] = System.currentTimeMillis();
        ++currentTick;
    }
    public static double getTPS() {
        return Tps.getTPS(100);
    }
    public static double getTPS(int ticks) {
        if (currentTick < ticks) {
            return 20.0;
        }
        int target = (currentTick - ticks) % 600;
        long elapsed = System.currentTimeMillis() - TICKS[target];
        return Math.min(20.0, (double)ticks * 1000.0 / (double)elapsed);
    }
    public static double getMSPT() {
        return 1000.0 / Tps.getTPS();
    }
}