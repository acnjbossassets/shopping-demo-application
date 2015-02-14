package com.redhat.shopping.demo.application.camel.routes;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gae.auth.GAuthUpgradeBinding;

public class GauthRouteBuilder extends RouteBuilder {

	
	private static final int ONE_HOUR = 3600;
	
	
	@Override
	public void configure() throws Exception {
		getContext().setTracing(true);
        String encodedCallback = URLEncoder.encode("http://localhost:8181/shoppingApplication/oauth/handler", "UTF-8");
        String encodedScope = URLEncoder.encode("https://www.googleapis.com/auth/plus.me", "UTF-8");
        from("jetty:http://0.0.0.0:8080/authorize")
            .to("gauth:authorize?callback=" + encodedCallback + "&scope=" + encodedScope);
        from("jetty:http://localhost:8181/shoppingRest/")
        	.to("gauth:upgrade")
        	.process(new Processor() {
				
				public void process(Exchange exchange) throws Exception {

			        String accessToken = exchange.getIn().getHeader(GAuthUpgradeBinding.GAUTH_ACCESS_TOKEN, String.class);
			        String accessTokenSecret = exchange.getIn().getHeader(GAuthUpgradeBinding.GAUTH_ACCESS_TOKEN_SECRET, String.class);
			    
			        if (accessToken != null) {
			            HttpServletResponse servletResponse = exchange.getIn().getHeader(
			                    Exchange.HTTP_SERVLET_RESPONSE, HttpServletResponse.class);
			            
			            Cookie accessTokenCookie = new Cookie("ACCESS-TOKEN", accessToken);
			            Cookie accessTokenSecretCookie = new Cookie("ACCESS-TOKEN-SECRET", accessTokenSecret); 
			            
			            accessTokenCookie.setPath("/oauth/");
			            accessTokenCookie.setMaxAge(ONE_HOUR);
			            
			            accessTokenSecretCookie.setPath("/oauth/");
			            accessTokenSecretCookie.setMaxAge(ONE_HOUR);
			            
			            servletResponse.addCookie(accessTokenCookie);
			            servletResponse.addCookie(accessTokenSecretCookie);
			        }else{
			        	System.out.println("Unable to fetch cookies");
			        }
			        
			        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 302);
			        exchange.getOut().setHeader("Location", "/oauth/calendar");
				}
			});
	}
}