package com.sg.shopzy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> productList;

    // Constructor
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product == null) return;

        // Set product details
        holder.productName.setText(product.getProductName() != null ? product.getProductName() : "N/A");
        holder.productPrice.setText("₹" + (product.getPrice() != null ? product.getPrice() : "0"));
        holder.productDescription.setText(product.getDescription() != null ? product.getDescription() : "No description");

        // Load product image using Glide (with error handling)
        String imageUrl = product.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeorder_image) // Placeholder while loading
                    .error(R.drawable.error_image) // Error image if loading fails
                    .optionalCenterCrop() // Ensures proper scaling and cropping
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.error_image);
        }

        // Handle Buy Now Button Click
        holder.buyButton.setOnClickListener(v -> navigateToCheckout(product));
    }

    @Override
    public int getItemCount() {
        return (productList != null) ? productList.size() : 0;
    }

    // ViewHolder class for product items
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        Button buyButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
            buyButton = itemView.findViewById(R.id.buyButton);
        }
    }

    // Navigate to CheckoutActivity with product details
    private void navigateToCheckout(Product product) {
        if (context == null || product == null) {
            Toast.makeText(context, "Product detail missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (product.getId() == 0 || product.getProductName() == null || product.getPrice() == null) {
            Toast.makeText(context, "Some product details are missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra("PRODUCT_ID", product.getId());
        intent.putExtra("PRODUCT_NAME", product.getProductName());
        intent.putExtra("PRODUCT_PRICE", product.getPrice());
        intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription() != null ? product.getDescription() : "No description");
        intent.putExtra("PRODUCT_IMAGE", product.getImage() != null ? product.getImage() : "");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Avoids crashes in some cases
        context.startActivity(intent);
    }
}
