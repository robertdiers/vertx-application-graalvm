package vertx.exceptions;

public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CustomException(String msg, int status){
        super(msg);
    }
	
}