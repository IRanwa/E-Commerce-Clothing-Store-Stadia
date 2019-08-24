package com.example.imeshranawaka.stadia.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.Models.MainSubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.Models.SubCategoryDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<ProductDTO> productList;
    private Context context;

    public ProductListAdapter(List<ProductDTO> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_container, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder viewHolder, int i) {
        ProductDTO prod = productList.get(i);
        viewHolder.txtPrice.setText("Rs. "+prod.getPrice());
        viewHolder.txtTitle.setText(prod.getTitle());

        byte[] decodedString = Base64.decode(prod.getProductImages().get(0).getPath().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.prodListImg.setImageBitmap(decodedByte);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.txtPrice) TextView txtPrice;
        @BindView(R.id.prodListImg) ImageView prodListImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

