package vertx.web;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.json.EncodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractController {
	
	protected Vertx vertx;    
	protected Router router;
	
	protected AbstractController(Vertx vertx) {
		this.vertx = vertx;
	}
	
	protected Router getCleanRouter(){    	
        if(router == null){
            router = Router.router(vertx);                   
        }
        return router;
    }
	
	protected void handleAsyncResponse(AsyncResult<Object> res, RoutingContext ctx){
        // Handler for the promise. If successful, encode result and send
        if(res.succeeded()) {
            try {
                ctx.response().end(Json.encode(res.result()));
            }
            catch(EncodeException e){
                ctx.fail(new RuntimeException("Failed to encode results."));
            }
        } else {
            ctx.fail(res.cause());
        }
    }

}