package com.francoisa;


import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;

public class App {

    public static void main(String[] args) throws IOException {
    	HttpServer httpServer = new HttpServer();
        
    	NetworkListener networkListener = new NetworkListener("sample-listener", "localhost", 18888);
    	httpServer.addListener(networkListener);
    	        
    	httpServer.getServerConfiguration().addHttpHandler(new TimeHandler(), "/time");

    	try {
    		httpServer.start();
    	    System.out.println("Press any key to stop the server...");
    	    System.in.read();
    	} 
    	catch (Exception e) {
    	    System.err.println(e);
    	}
    }
}