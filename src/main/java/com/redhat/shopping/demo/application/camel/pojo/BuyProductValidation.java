package com.redhat.shopping.demo.application.camel.pojo;

import org.apache.camel.Exchange;

public class BuyProductValidation {
public void validateCreditProductAvailability(Exchange exchange){
	//Business Logic to verify account details of the customer
	//As of now we set the validation true. This class is only for demonstration
	
	exchange.getIn().setHeader("validateCreditProductAvailability", true);
}
}
