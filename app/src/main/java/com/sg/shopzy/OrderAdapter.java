package com.sg.shopzy;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

	private Context context;
	private List<Order> orderList;

	public OrderAdapter(Context context, List<Order> orderList) {
		this.context = context;
		this.orderList = orderList;
	}

	@NonNull
	@Override
	public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.activity_orderdetail, parent, false);
		return new OrderViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
		Order order = orderList.get(position);

		// ✅ Set Data
		holder.orderId.setText("Order ID: #" + order.getOrderId());
		holder.productName.setText(order.getProductName());
		holder.productPrice.setText("Price: ₹" + order.getTotal());
		holder.gst.setText("GST: ₹" + order.getGst());
		holder.grandTotal.setText("Grand Total: ₹" + (order.getTotal() + order.getGst()));

		// ✅ Load Product Image
		Glide.with(context)
				.load(order.getProductImage())
				.placeholder(R.drawable.placeorder_image)
				.into(holder.productImage);
	}

	@Override
	public int getItemCount() {
		return orderList.size();
	}

	public static class OrderViewHolder extends RecyclerView.ViewHolder {

		TextView orderId, productName, productPrice, gst, grandTotal;
		ImageView productImage;

		public OrderViewHolder(@NonNull View itemView) {
			super(itemView);
			orderId = itemView.findViewById(R.id.orderId);
			productName = itemView.findViewById(R.id.productName);
			productPrice = itemView.findViewById(R.id.productPrice);
			gst = itemView.findViewById(R.id.gst);
			grandTotal = itemView.findViewById(R.id.grandTotal);
			productImage = itemView.findViewById(R.id.productImage);
		}
	}
}
