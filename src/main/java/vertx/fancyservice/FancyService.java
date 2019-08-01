package vertx.fancyservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vertx.Logger;
import vertx.fancyservice.dataobject.FancyDO;

public class FancyService {
	
	public FancyService() {
        // initiate your class
	}
    
    public List<FancyDO> getFancy(String user){
        try{
            Thread.sleep(1000);
            List<FancyDO> fancy = new ArrayList<FancyDO>();
            for (int i = 0; i < 3; i++) fancy.add(new FancyDO("yeah #"+i));
            Logger.log("getFancy("+user+")");
            return fancy;
        } catch (InterruptedException e){
            throw new RuntimeException("This is a safe message");
        }
    }

}