package vertx.timer;

import io.vertx.core.Handler;
import vertx.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodicTimer1 implements Handler<Long> {

    @Override
    public void handle(Long event) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.log(event+" PeriodicTimer1 executed");
    }

}