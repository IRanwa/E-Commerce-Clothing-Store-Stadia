package com.example.imeshranawaka.stadia.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.Fragments.CategoryTabView;
import com.example.imeshranawaka.stadia.Models.MainCategoryDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {

    private List<MainCategoryDTO> mainCategoryList;
    private Context context;
    private CategoryTabView categoryTabView;

    public MainCategoryAdapter(Context context, List<MainCategoryDTO> mainCategoryList, CategoryTabView categoryTabView) {
        this.mainCategoryList = mainCategoryList;
        this.context = context;
        this.categoryTabView = categoryTabView;
    }

    @NonNull
    @Override
    public MainCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_category_container, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoryAdapter.ViewHolder viewHolder, int i) {
        MainCategoryDTO mainCat = mainCategoryList.get(i);
        viewHolder.txtMainCat.setText(mainCat.getMainCatTitle());

        byte[] decodedString = Base64.decode(mainCat.getMainCatImg().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.imgMainCat.setImageBitmap(decodedByte);

        viewHolder.mainCatContainer.setOnClickListener(new mainCatContainerClick(mainCat));

    }

    @Override
    public int getItemCount() {
        return mainCategoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtMainCat) TextView txtMainCat;
        @BindView(R.id.imgMainCat) ImageView imgMainCat;
        @BindView(R.id.mainCatContainer) LinearLayout mainCatContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private class mainCatContainerClick implements View.OnClickListener {
        private MainCategoryDTO mainCat;
        public mainCatContainerClick(MainCategoryDTO mainCat) {
            this.mainCat = mainCat;
        }

        @Override
        public void onClick(View view) {
            categoryTabView.setSubCategory(mainCat);
        }
    }
}
