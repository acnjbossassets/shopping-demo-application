package com.redhat.shopping.demo.application.camel.routes;

import java.net.URLEncoder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gae.auth.GAuthUpgradeBinding;


public class GauthRouteBuilder extends RouteBuilder {

	
	private static final int ONE_HOUR = 3600;
	
	@Override
	public void configure() throws Exception {
		getContext().setTracing(true);
        String encodedCallback = URLEncoder.encode("http://localhost:8080/camelServelt/oauth/handler", "UTF-8");
        String encodedScope = URLEncoder.encode("https://www.google.com/m8/feeds/", "UTF-8");
        from("jetty:http://0.0.0.0:8080/authorize")
            .to("gauth:authorize?callback=" + encodedCallback + "&scope=" + encodedScope);
        from("jetty:http://0.0.0.0:8080/camelServelt/oauth/handler")
        	.log("Inside Upgradation Process")
        	.to("gauth:upgrade")
        	.log("Upgraded tokens")
        	.process(new Processor() {
				
				public void process(Exchange exchange) throws Exception {

			        String accessToken = exchange.getIn().getHeader(GAuthUpgradeBinding.GAUTH_ACCESS_TOKEN, String.class);
			        String accessTokenSecret = exchange.getIn().getHeader(GAuthUpgradeBinding.GAUTH_ACCESS_TOKEN_SECRET, String.class);
			        log.info("Access Token :::"+accessToken);
			        log.info("Access Token Secret :::"+accessTokenSecret);
			        exchange.getOut().setHeader("accessToken",accessToken);
			        exchange.getOut().setHeader("accessTokenSecret",accessTokenSecret);
			        exchange.getOut().setHeader(Exchange.HTTP_QUERY, constant("accessToken="+accessToken+"&accessTokenSecret="+accessTokenSecret));
				}
			})
			.to("http://localhost:8181/shoppingApplication/oauth/login?bridgeEndpoint=true&amp;throwExceptionOnFailure=false&accessToken="+simple("${headers.accessToken}")+"&accessTokenSecret="+simple("${headers.accessTokenSecret}"))
			;
	}


	public static int getOneHour() {
		return ONE_HOUR;
	}
}