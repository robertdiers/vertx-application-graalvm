package vertx.userservice.dataobject;

public class UserDO {
	
	private String username;

    public UserDO() {
    }

    public UserDO(String name){
        this.username = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
