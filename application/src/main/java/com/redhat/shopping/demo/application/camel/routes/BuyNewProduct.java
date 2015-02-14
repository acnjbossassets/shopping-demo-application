package com.redhat.shopping.demo.application.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class BuyNewProduct extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("vm:buyProductsByCode")
		.log("Buy Request Started")
		.process(new Processor() {
			
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setHeader("awaitingResponse",
						true);				
			}
		})
		.log("firing sql query to verify product availability")
		.to("sql:{{sql.Products.checkAvailability}}")
		.choice()
		.when(simple("${body}>0"))
		.setHeader("productAvailable", constant(true))
		.to("activemq:buyProductsByCode")
		.transform(constant("Thank you for buying the products"))
		.otherwise()
		.setHeader("productAvailable", constant(false))
		.transform(constant("The product is out of stock"));
		
	}
	
	
	
	

}
