package com.redhat.shopping.demo.application.camel.beans;

import com.redhat.shopping.demo.application.pojos.jpa.Products;

public class RequestTransformation {
public Products transforAsJpaBean(Object body){
	
	Products products = new Products();
	return products;
	
}
}
