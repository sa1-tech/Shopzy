package com.sg.shopzy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

// 🚀 Adapter for displaying a list of categories in a RecyclerView
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context context;
    private final List<Category> categoryList;
    private final OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category, listener);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryName;
        private final ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }

        void bind(Category category, OnCategoryClickListener listener) {
            categoryName.setText(category.getName());

            // Check if the image URL is valid before loading
            if (!TextUtils.isEmpty(category.getImage())) {
                Log.d("GLIDE_DEBUG", "Loading image: " + category.getImage());

                Glide.with(itemView.getContext())
                        .load(category.getImage())
                        .placeholder(R.drawable.placeorder_image)
                        .error(R.drawable.error_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter() // Ensure full image is visible without cutting
                        .into(categoryImage);
            } else {
                Log.e("GLIDE_ERROR", "Invalid Image URL: " + category.getImage());
                categoryImage.setImageResource(R.drawable.error_image);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
