package vertx.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import vertx.userservice.UserService;

public class UserController extends AbstractController {
	
    private UserService service;

    public UserController(Vertx vertx, UserService service){
        super(vertx);
        this.service = service;
    }

    public Router getRouter(){
        Router router = getCleanRouter();        
        router.get(API.USER_GET).handler(this::get);   
        router.get(API.USER_GETALL).handler(this::getAll);
        return router;
    }
    
    private void get(RoutingContext ctx){
        String name = ctx.request().getParam("name");
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.getUser(name)); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }

    private void getAll(RoutingContext ctx){
        //use an worker as service / JDBC access will be synchronously
        vertx.executeBlocking(
                promise -> { promise.complete(service.getAllUser()); },
                false,
                res -> { handleAsyncResponse(res, ctx); }
        );
    }

}