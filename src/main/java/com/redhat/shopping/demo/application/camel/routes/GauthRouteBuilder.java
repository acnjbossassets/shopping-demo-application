package com.redhat.shopping.demo.application.camel.routes;

import java.net.URLEncoder;

import org.apache.camel.builder.RouteBuilder;

public class GauthRouteBuilder extends RouteBuilder {

    private String application;

    /**
     * Sets the name of the GAE application.
     *
     * @param application a GAE application name.
     */
    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public void configure() throws Exception {

        // Callback URL for sending back an authorized access token.
        String encodedCallback = URLEncoder.encode(String.format("https://%s.appspot.com/camel/handler", application), "UTF-8");
        // Google should issue an access token that is scoped to calendar feeds.
        String encodedScope = URLEncoder.encode("http://www.google.com/calendar/feeds/", "UTF-8");

        // Route for obtaining an unauthorized request token from Google Accounts. The
        // response redirects the browser to an authorization page provided by Google.
        from("ghttp:///authorize")
            .to("gauth:authorize?callback=" + encodedCallback + "&scope=" + encodedScope);

        
        // Handles callbacks from Google Accounts which contain an authorized request token.
        // The authorized request token is upgraded to an access token which is stored in
        // the response message header. The TutorialTokenProcessor is application-specific
        // and stores the access token (plus access token secret) is cookies. It further
        // redirects the user to the application's main location (/oauth/calendar).
        from("ghttp:///handler")
            .to("gauth:upgrade");
            //.process(new TutorialTokenProcessor());
    }

}