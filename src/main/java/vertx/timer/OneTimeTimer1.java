package vertx.timer;

import io.vertx.core.Handler;
import vertx.Logger;

public class OneTimeTimer1 implements Handler<Long> {

    @Override
    public void handle(Long event) {
        TimerStorage.chancelTimer(TimerStorage.TIMER_PERIODIC_TIME_1);
        Logger.log(event+" OneTimeTimer1 executed");
    }

}