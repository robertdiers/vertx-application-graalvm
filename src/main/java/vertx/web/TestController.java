package vertx.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import vertx.testservice.TestService;

public class TestController extends AbstractController {

    TestService service;

    public TestController(Vertx vertx, TestService service){
        super(vertx);
        this.service = service;
    }

    public Router getRouter(){
        Router router = getCleanRouter();
        router.get(API.TEST_SYNC).handler(this::getSync);
        router.get(API.TEST_STREAM).handler(this::getStream);
        router.get(API.TEST_PARALLELSTREAM).handler(this::getParallelstream);
        router.get(API.TEST_RXJAVA).handler(this::getRxjava);
        router.get(API.TEST_PARALLELRXJAVA).handler(this::getParallelrxjava);
        router.get(API.TEST_WEBCLIENT).handler(this::getWebclient);
        router.get(API.TEST_DATABASE).handler(this::getDatabase);
        return router;
    }

    private void getSync(RoutingContext ctx){
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.sync()); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }

    private void getStream(RoutingContext ctx){
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.stream()); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }

    private void getParallelstream(RoutingContext ctx){
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.parallelstream()); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }

    private void getRxjava(RoutingContext ctx){
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.rxjava()); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }

    private void getParallelrxjava(RoutingContext ctx){
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.parallelrxjava()); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }

    private void getWebclient(RoutingContext ctx){
        //we want to have a clean controller, send required objects for async processing to service
        service.getWebclient(vertx, ctx);
    }

    private void getDatabase(RoutingContext ctx){
        //we want to have a clean controller, send required objects for async processing to service
        service.getDatabase(vertx, ctx);
    }

}