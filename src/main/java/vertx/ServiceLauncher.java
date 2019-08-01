package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class ServiceLauncher extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) {
		
		int WORKER_POOL_SIZE = 100;

        DeploymentOptions opts = new DeploymentOptions().setWorkerPoolSize(WORKER_POOL_SIZE);
        String verticle = ServerVerticle.class.getName();
        vertx.deployVerticle(verticle, opts, res -> {
            if(res.failed()) {
                Logger.log("Failed to deploy ServerVerticle");
                startPromise.fail(res.cause());
            } else {
                Logger.log("Successfully deployed ServerVerticle");
                startPromise.complete();
            }
        });
        
        //it is possible to deploy more verticals in the same JVM, 
        //but are we talking about micro services yet?
	    
	}

	//required for GraalVM
	public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new ServiceLauncher());
    }
	
}