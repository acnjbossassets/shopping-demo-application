package com.redhat.shopping.demo.application.beans;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class UrlProcessor {
public static void main(String[] args) throws Exception {
	URL url = new URL("http://localhost:9090/route/shoppingApplication/products");
	InputStream is = url.openStream();
	StringWriter writer = new StringWriter();
	IOUtils.copy(is, writer);
	String jsonString = writer.toString();
	
}
}
