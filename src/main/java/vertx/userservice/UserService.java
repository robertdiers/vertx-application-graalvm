package vertx.userservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vertx.Logger;
import vertx.userservice.dataobject.UserDO;

public class UserService {
	
	public UserService() {
        // initiate your class
    }

    public UserDO getUser(String name){
        try{
            Logger.log("getUser");
            return new UserDO(name);
        } catch (Exception e){
            throw new RuntimeException("This is a safe message");
        }
    }
    
    public List<UserDO> getAllUser(){
        try{
            List<UserDO> users = new ArrayList<UserDO>();
            for (int i = 0; i < 10; i++) users.add(new UserDO("user"+i));
            Logger.log("getAllUser");
            return users;
        } catch (Exception e){
            throw new RuntimeException("This is a safe message");
        }
    }

}
