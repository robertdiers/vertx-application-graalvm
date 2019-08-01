package vertx;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static void log(String message) {
        //you should use the framework of your choice here
        System.out.println(sdf.format(new Date()) + " " + message);
    }

    public static void log(Throwable t) {
        t.printStackTrace();
        log(t.getMessage());
    }

}