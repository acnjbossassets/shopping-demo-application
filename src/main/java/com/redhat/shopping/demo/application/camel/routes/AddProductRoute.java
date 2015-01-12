package com.redhat.shopping.demo.application.camel.routes;

import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;

public class AddProductRoute extends RouteBuilder {
	public void configure() {
		errorHandler(new NoErrorHandlerBuilder());
		from("vm:addProducts")
		.split().xpath("/products/product")
		.setHeader("productName", simple("${body.name}"))
		.setHeader("productcode", xpath("/product/productcode"))
		.log("${header.productCode}")
		.log("${header.productName}")
		.to("sql:insert  into `products`(`productCode`,`productName`,`productLine`,`productScale`,`productVendor`,`productDescription`,`quantityInStock`,`buyPrice`,`MSRP`) values ('myNewProduct','1969 Harley Davidson Ultimate Chopper','Motorcycles','1:10','Min Lin Diecast','This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.',7933,48.81,95.7)")
		;
	}
}
