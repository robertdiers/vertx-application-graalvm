package vertx;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import vertx.fancyservice.FancyService;
import vertx.testservice.TestService;
import vertx.timer.TimerStorage;
import vertx.userservice.UserService;
import vertx.web.*;

public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) throws Exception {

        int PORT = 8080;

        Router mainRouter = Router.router(vertx);
        mainRouter.route().consumes("application/json");
        mainRouter.route().produces("application/json");

        Set<String> allowHeaders = getAllowedHeaders();
        Set<HttpMethod> allowMethods = getAllowedMethods();
        mainRouter.route().handler(BodyHandler.create());
        mainRouter.route().handler(CorsHandler.create("*")
                .allowedHeaders(allowHeaders)
                .allowedMethods(allowMethods));

        //user controller
        UserService userservice = new UserService();
        UserController usercontroller = new UserController(vertx, userservice);
        Router userrouter = usercontroller.getRouter();
        mainRouter.mountSubRouter(API.USER, userrouter);

        //fancy controller
        FancyService fancyservice = new FancyService();
        FancyController fancycontroller = new FancyController(vertx, fancyservice);
        Router fancyrouter = fancycontroller.getRouter();
        mainRouter.mountSubRouter(API.FANCY, fancyrouter);

        //test controller
        TestService testservice = new TestService();
        TestController testcontroller = new TestController(vertx, testservice);
        Router testrouter = testcontroller.getRouter();
        mainRouter.mountSubRouter(API.TEST, testrouter);

        //default ones
        mainRouter.get(API.HEALTHCHECK).handler(GlobalHandler::healthcheck);
        mainRouter.route().failureHandler(GlobalHandler::error);

        //create the http server and pass it the router
        vertx.createHttpServer()
            .requestHandler(mainRouter)
            .listen(PORT, res -> {
                if(res.succeeded()){
                    Logger.log("Server listening on port " + PORT);
                    promise.complete();
                }
                else{
                    Logger.log("Failed to launch server");
                    promise.fail(res.cause());
                }
            });

        //schedule the timers
        TimerStorage.scheduleTimers(vertx);
    }

    private Set<String> getAllowedHeaders(){
        Set<String> allowHeaders = new HashSet<>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        return allowHeaders;
    }

    private Set<HttpMethod> getAllowedMethods(){
        Set<HttpMethod> allowMethods = new HashSet<>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.PATCH);
        return allowMethods;
    }

}