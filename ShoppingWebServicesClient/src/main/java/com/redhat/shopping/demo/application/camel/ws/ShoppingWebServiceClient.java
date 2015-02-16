package com.redhat.shopping.demo.application.camel.ws;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import com.redhat.shopping.demo.application.camel.ws.AddNewProducts;
import com.redhat.shopping.demo.application.camel.ws.AddNewProductsService;


public class ShoppingWebServiceClient {

	public void addProducts(String data)
	{			
			AddNewProductsService service = new AddNewProductsService();
			AddNewProducts newproducts = service.getAddNewProductsPort();
			newproducts.addProduct(writer.getBuffer().toString());
			
	}
	
}
