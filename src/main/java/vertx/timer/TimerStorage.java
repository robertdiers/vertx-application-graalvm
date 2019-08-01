package vertx.timer;

import io.vertx.core.Vertx;
import vertx.Logger;

import java.util.HashMap;
import java.util.Map;

public class TimerStorage {

    public final static String TIMER_ONE_TIME_1 = "TIMER_ONE_TIME_1";
    public final static String TIMER_PERIODIC_TIME_1 = "TIMER_ONE_TIME_1";

    private static Map<String,Long> storedIDs = new HashMap<>();
    private static Vertx vertx;

    public static void scheduleTimers(Vertx vertx) {
        TimerStorage.vertx = vertx;
        //one time timer
        Long oneTimeTimer1ID = vertx.setTimer(10000, new OneTimeTimer1());
        storedIDs.put(TIMER_ONE_TIME_1, oneTimeTimer1ID);
        //periodic timer
        Long periodicTimer1ID = vertx.setPeriodic(3000, new PeriodicTimer1());
        storedIDs.put(TIMER_PERIODIC_TIME_1, periodicTimer1ID);
        Logger.log("Successfully scheduled all Timers");
    }

    public static void chancelTimer(String name) {
        Long id = storedIDs.get(name);
        if (id != null) vertx.cancelTimer(id);
    }

}