package com.redhat.shopping.demo.application.camel.routes;

import java.net.URLEncoder;

import org.apache.camel.builder.RouteBuilder;

public class GauthRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		getContext().setTracing(true);
        String encodedCallback = URLEncoder.encode("https://localhost:8443/handler", "UTF-8");
        String encodedScope = URLEncoder.encode("http://www.google.com/calendar/feeds/", "UTF-8");
        from("jetty:http://0.0.0.0:8080/authorize")
            .to("gauth:authorize?callback=" + encodedCallback + "&scope=" + encodedScope);
        from("jetty:https://0.0.0.0:8443/handler")
        	.to("gauth:upgrade")
            .log("Google Access Token =  ${header.CamelGauthAccessToken}")
            .log("Google Access Token Secret =  ${header.CamelGauthAccessTokenSecret}");
	}
}