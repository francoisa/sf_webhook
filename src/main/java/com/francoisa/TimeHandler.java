package com.francoisa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public class TimeHandler extends HttpHandler {
	final ExecutorService complexAppExecutorService =
			GrizzlyExecutorService.createInstance(
			    ThreadPoolConfig.defaultConfig()
			    .copy()
			    .setCorePoolSize(5)
			    .setMaxPoolSize(5));
	
	 final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
     
	 @Override
	 public void service(final Request request, final Response response) throws Exception {
	         
		 response.suspend(); // Instruct Grizzly to not flush response, once we exit the service(...) method
	         
		 complexAppExecutorService.execute(new Runnable() {
			 public void run() {
				 try {
					 response.setContentType("text/plain");
					 final Date now = new Date();
					 final String formattedTime;
					 synchronized (formatter) {
						 formattedTime = formatter.format(now);
					 }
					 response.getWriter().write(formattedTime);
				 } 
				 catch (Exception e) {
					 response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
				 } 
				 finally {
					 response.resume();  // Finishing HTTP request processing and flushing the response to the client
				 }
			 }
		 });
	 }
}

    
