package vertx.web;

import java.util.Date;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vertx.Logger;
import vertx.exceptions.CustomException;

public class GlobalHandler {
	
    private GlobalHandler(){}

    public static void healthcheck(RoutingContext ctx){
        ctx.response().end(""+new Date());
    }

    public static void error(RoutingContext ctx){
    	
        int status;
        String msg;

        //read thrown exception from context
        Throwable failure = ctx.failure();

        if(CustomException.class.isAssignableFrom(failure.getClass())){
            msg = failure.getMessage();
            status = HttpResponseStatus.BAD_REQUEST.code();
        }
        else {
            Logger.log(failure);
            msg = "Internal Server Error";
            status = HttpResponseStatus.INTERNAL_SERVER_ERROR.code();
        }

        //log the error, and send a json encoded response.
        JsonObject res = new JsonObject().put("status", status).put("message", msg);
        ctx.response().setStatusCode(status).end(res.encode());
    }
    
}