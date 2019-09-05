package com.example.imeshranawaka.stadia.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.Fragments.ProductDetails;
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
    private FragmentManager fm;

    public ProductListAdapter(List<ProductDTO> productList, Context context, FragmentManager fm) {
        this.productList = productList;
        this.context = context;
        this.fm = fm;
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

        viewHolder.prodListImg.setOnClickListener(new viewProduct_Click(prod));
        viewHolder.txtTitle.setOnClickListener(new viewProduct_Click(prod));
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

    private class viewProduct_Click implements View.OnClickListener {
        private ProductDTO prod;

        public viewProduct_Click(ProductDTO prod) {
            this.prod = prod;
        }

        @Override
        public void onClick(View view) {
            ProductDetails prodDetails = new ProductDetails();
            Bundle bundle = new Bundle();
            bundle.putSerializable("product",prod);
            prodDetails.setArguments(bundle);

            FragmentTransaction transaction = fm.beginTransaction();
//            int backStackEntry = fm.getBackStackEntryCount();
//            List<Fragment> fragments = fm.getFragments();
//            if (backStackEntry > 0) {
//                for (int i = 0; i < backStackEntry; i++) {
//                    fm.popBackStackImmediate();
//                    if(fragments.size()<i) {
//                        Fragment frag = fragments.get(0);
//                        transaction.remove(frag);
//                        System.out.println("frag name : "+frag.getTag());
//                        if(frag.getTag()!=null && frag.getTag().equals("ProductView")) {
//                            break;
//                        }
//                    }
//                    fragments = fm.getFragments();
//                }
//            }
            transaction.replace(R.id.subFragment, prodDetails, "ProdDetails");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}

