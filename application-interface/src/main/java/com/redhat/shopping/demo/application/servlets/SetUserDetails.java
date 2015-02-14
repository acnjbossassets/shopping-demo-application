package com.redhat.shopping.demo.application.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetUserDetails extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accessToken = request.getParameter("oauth_token");
		String accessTokenSecret = request.getParameter("oauth_verifier");
		Cookie oathToken = new Cookie("ACCESS-TOKEN", accessToken);
		Cookie oathTokenSecret = new Cookie("ACCESS-TOKEN-SECRET", accessTokenSecret);
		oathToken.setMaxAge(3600);
		oathTokenSecret.setMaxAge(3600);
		response.addCookie(oathToken);
		response.addCookie(oathTokenSecret);
		response.sendRedirect("/shoppingApplication");
	}

}
