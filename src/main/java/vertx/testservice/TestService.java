package vertx.testservice;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import vertx.Logger;
import vertx.userservice.dataobject.UserDO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestService {

	public TestService() {
        // initiate your class
	}
    
    public List<String> sync() {
        List<String> sample = getSampleData();
        List<String> res = new ArrayList<String>();
        //do it the traditional way
        for (String str : sample) {
            res.add(timeIntensiveStuff(str));
        }
        return res;
    }

    public List<String> stream() {
        List<String> sample = getSampleData();
        //use streams
        List<String> res = sample.stream()
                .map(s -> timeIntensiveStuff(s))
                .sorted()
                .collect(Collectors.toList());
        return res;
    }

    public List<String> parallelstream() {
        List<String> sample = getSampleData();
        //use parallel streams with max cores parallel
        List<String> res = sample.parallelStream()
                .map(s -> timeIntensiveStuff(s))
                .sorted()
                .collect(Collectors.toList());
        return res;
    }

    public String rxjava() {
        List<String> sample = getSampleData();
        //use rxjava (asynchronously, perfect if no response required)
        Observable.fromIterable(sample)
                .subscribeOn(Schedulers.computation())
                .map(s -> timeIntensiveStuff(s))
                .subscribe(s -> Logger.log(s));
        return "started asynchronously - please check log";
    }

    public String parallelrxjava() {
        List<String> sample = getSampleData();
        //use parallel rxjava (asynchronously, perfect if no response required)
        Observable.fromIterable(sample)
                .flatMap(val -> Observable.just(val)
                        .subscribeOn(Schedulers.computation())
                        .map(s -> timeIntensiveStuff(s))
                )
                .subscribe(s -> Logger.log(s));
        return "started asynchronously - please check log";
    }

    private List<String> getSampleData() {
        List<String> res = new ArrayList<String>();
        for (int i = 0; i < 10; i ++) res.add("#"+i);
        return res;
    }

    private String timeIntensiveStuff(String input) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return  sdf.format(new Date()) + " " + input;
    }

    public void getWebclient(Vertx vertx, RoutingContext ctx){
        WebClient client = WebClient.create(vertx);
        //Send a GET request
        client.get(8080, "localhost", "/user/get")
                .addQueryParam("name", "testname")
                .as(BodyCodec.json(UserDO.class))
                .send(res -> {
                    if (res.succeeded()) {
                        //Obtain response
                        HttpResponse<UserDO> response = res.result();
                        UserDO user = response.body();
                        Logger.log(user.toString());
                        Logger.log("Received response with status code" + response.statusCode());
                        //here you can do whatever you want to modify, extend or use the result
                        ctx.response().end(Json.encode(user));
                    } else {
                        ctx.fail(res.cause());
                    }
                });
    }

    public void getDatabase(Vertx vertx, RoutingContext ctx){

        JsonObject config = new JsonObject()
                .put("url", "jdbc:h2:mem:h2test;MODE=Oracle;DB_CLOSE_DELAY=-1")
                .put("driver_class", "org.h2.Driver")
                .put("user", "sa")
                .put("password", "")
                .put("max_pool_size", 30);

        //shared data source, every code will get a client to the same data source only
        SQLClient client = JDBCClient.createShared(vertx, config, "H2DataSource");

        client.getConnection(res -> {
            if (res.succeeded()) {

                SQLConnection connection = res.result();

                connection.query("SELECT * FROM DUAL", res2 -> {
                    if (res2.succeeded()) {
                        ResultSet rs = res2.result();

                        //do something with the result
                        ctx.response().end(Json.encode(rs));
                    } else {
                        ctx.fail(res2.cause());
                    }
                });
            } else {
                ctx.fail(res.cause());
            }
        });
    }

}