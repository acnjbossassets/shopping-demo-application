package com.redhat.shopping.demo.application.beans;

public class User {

	private String imageUrl;
	private String name;

	public User(String name, String imageUrl) {
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
