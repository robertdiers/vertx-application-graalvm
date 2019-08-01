package vertx.web;

public class API {
	
	public static final String HEALTHCHECK = "/healthcheck";    
    
	//user controller
    public static final String USER = "/user";
    public static final String USER_GET = "/get";
    public static final String USER_GETALL = "/getall";
    
    //fancy controller
    public static final String FANCY = "/fancy";
    public static final String FANCY_GET = "/get";

    //test controller
    public static final String TEST = "/test";
    public static final String TEST_SYNC = "/sync";
    public static final String TEST_STREAM = "/stream";
    public static final String TEST_PARALLELSTREAM = "/parallelstream";
    public static final String TEST_RXJAVA = "/rxjava";
    public static final String TEST_PARALLELRXJAVA = "/parallelrxjava";
    public static final String TEST_WEBCLIENT = "/webclient";
    public static final String TEST_DATABASE = "/database";

}