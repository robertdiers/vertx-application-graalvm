package vertx.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import vertx.fancyservice.FancyService;

public class FancyController extends AbstractController {
	
    private FancyService service;

    public FancyController(Vertx vertx, FancyService service){
        super(vertx);
        this.service = service;
    }

    public Router getRouter(){        
        Router router = getCleanRouter();        
        router.get(API.FANCY_GET).handler(this::get);
        return router;
    }
    
    private void get(RoutingContext ctx){
        String name = ctx.request().getParam("name");
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.getFancy(name)); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }   
    
}