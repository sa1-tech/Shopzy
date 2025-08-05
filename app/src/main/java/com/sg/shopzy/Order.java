package com.sg.shopzy;

import java.io.Serializable;

public class Order implements Serializable {
	private String orderId;
	private String productName;
	private String productImage;
	private int quantity;
	private double total;
	private double gst;

	// ✅ Generate Constructor
	public Order(String orderId, String productName, String productImage, int quantity, double total, double gst) {
		this.orderId = orderId;
		this.productName = productName;
		this.productImage = productImage;
		this.quantity = quantity;
		this.total = total;
		this.gst = gst;
	}

	// ✅ Generate Getters
	public String getOrderId() {
		return orderId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getTotal() {
		return total;
	}

	public double getGst() {
		return gst;
	}
}
