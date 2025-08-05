package com.sg.shopzy;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {

	@SerializedName("status")
	private String status;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
	private OrderData data;

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public OrderData getData() {
		return data;
	}

	public static class OrderData {
		@SerializedName("order_id")
		private int orderId;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("quantity")
		private int quantity;

		@SerializedName("gst")
		private double gst;

		@SerializedName("total")
		private double total;

		public int getOrderId() {
			return orderId;
		}

		public String getProductName() {
			return productName;
		}

		public int getQuantity() {
			return quantity;
		}

		public double getGst() {
			return gst;
		}

		public double getTotal() {
			return total;
		}
	}
}
