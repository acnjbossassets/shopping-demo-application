package com.redhat.shopping.demo.application.camel.routes;

import java.net.URL;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.commons.io.IOUtils;

public class AddProductRoute extends RouteBuilder {
	private String productDirectorySource;
	
	public void configure() {
		errorHandler(new NoErrorHandlerBuilder());
		JaxbDataFormat dataFormat = new JaxbDataFormat("com.redhat.shopping.demo.application.pojos.jpa");
		dataFormat.setPartClass("com.redhat.shopping.demo.application.pojos.jpa.Products");
		dataFormat.setPrettyPrint(true);
		
		from("cxf:bean:productAddition")
		.process(new Processor() {
			
			public void process(Exchange exchange) throws Exception {
				String urlPath = (String)exchange.getIn().getBody(List.class).get(0);
				if(urlPath!=null){
					exchange.getIn().setBody(IOUtils.toByteArray((new URL(urlPath)).openStream()));
				}else{
				exchange.getIn().setBody("File Path Is Null");
				}
				
				
			}
		})
		.split().xpath("/products-list/products").parallelProcessing()
		.log("${body}")
		.to("activemq:queue:insertProductsFromQueue")
		.process(new Processor(){
			public void process(Exchange exchange) throws Exception {
				exchange.getOut().setBody("Your Request Is Being Processed".getBytes());
			}
		})
		;
		
		
	}

	public String getProductDirectorySource() {
		return productDirectorySource;
	}

	public void setProductDirectorySource(String productDirectorySource) {
		this.productDirectorySource = productDirectorySource;
	}
}
