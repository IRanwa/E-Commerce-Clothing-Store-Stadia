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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.EnumClasses.SortBy;
import com.example.imeshranawaka.stadia.Fragments.ProductsView;
import com.example.imeshranawaka.stadia.Models.MainCategoryDTO;
import com.example.imeshranawaka.stadia.Models.MainSubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.Models.SubCategoryDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private List<SubCategoryDTO> subCategoryList;
    private Context context;
    private FragmentManager fm;
    private MainCategoryDTO mainCategoryDTO;

    public SubCategoryAdapter(List<SubCategoryDTO> subCategoryList, Context context, FragmentManager fm, MainCategoryDTO mainCategoryDTO) {
        this.subCategoryList = subCategoryList;
        this.context = context;
        this.fm = fm;
        this.mainCategoryDTO = mainCategoryDTO;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category_container, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder viewHolder, int i) {
        SubCategoryDTO subCat = subCategoryList.get(i);
        viewHolder.txtSubCat.setText(subCat.getSubCatTitle());

        byte[] decodedString = Base64.decode(subCat.getSubCatImg().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.imgSubCat.setImageBitmap(decodedByte);

        viewHolder.subCatContainer.setOnClickListener(new subCatContainerClick(subCat));
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtSubCat) TextView txtSubCat;
        @BindView(R.id.imgSubCat) ImageView imgSubCat;
        @BindView(R.id.subCatContainer) LinearLayout subCatContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private class subCatContainerClick implements View.OnClickListener {
        private SubCategoryDTO subCat;
        public subCatContainerClick(SubCategoryDTO subCat) {
            this.subCat = subCat;
        }

        @Override
        public void onClick(View view) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setSortBy(SortBy.A_To_Z);
            MainSubCategoryDTO mainSubCat = new MainSubCategoryDTO();
            mainSubCat.setMainCategory(mainCategoryDTO);
            mainSubCat.setSubCategory(subCat);
            productDTO.setMainSubCategory(mainSubCat);

            Bundle bundle = new Bundle();
            bundle.putInt("pageNo",0);
            bundle.putSerializable("productDTO",productDTO);

            ProductsView productView = new ProductsView();
            productView.setArguments(bundle);

            FragmentTransaction transaction = fm.beginTransaction();
            int backStackEntry = fm.getBackStackEntryCount();
            List<Fragment> fragments = fm.getFragments();
            if (backStackEntry > 0) {
                for (int i = 0; i < backStackEntry; i++) {
                    fm.popBackStackImmediate();
                    if(fragments.size()<i) {
                        Fragment frag = fragments.get(0);
                        transaction.remove(frag);
                        if(frag.getTag()!=null && !frag.getTag().equals("ProductView")) {
                            break;
                        }
                    }
                    fragments = fm.getFragments();
                }
            }
            transaction.replace(R.id.subFragment, productView, "ProductView");
            transaction.commit();
        }
    }
}
