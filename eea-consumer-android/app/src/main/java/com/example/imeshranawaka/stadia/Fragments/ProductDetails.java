package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.Adapters.SlidingImageAdapter;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.Models.ProductSizesDTO;
import com.example.imeshranawaka.stadia.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetails extends Fragment {

    private Unbinder unbinder;
    private ProductDTO product;

    @BindView(R.id.imgDisplayPager) ViewPager imgDisplayPager;
    @BindView(R.id.detailsContainer) ConstraintLayout detailsContainer;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.txtPrice) TextView txtPrice;
    @BindView(R.id.txtDesc) TextView txtDesc;
    @BindView(R.id.txtRevName) TextView txtRevName;
    @BindView(R.id.txtRevDesc) TextView txtRevDesc;
//    @BindView(R.id.txtQuestion) TextView txtQuestion;
//    @BindView(R.id.txtAnswer) TextView txtAnswer;
//    @BindView(R.id.txtQueText) TextView txtQueText;
    @BindView(R.id.txtRevText) TextView txtRevText;
    @BindView(R.id.quantity) Spinner quantity;
    @BindView(R.id.prodRating) RatingBar prodRating;

    @BindView(R.id.btnOrderNow) Button btnOrderNow;
    @BindView(R.id.btnAddToCart) Button btnAddToCart;
    @BindView(R.id.btnCartInView) ImageView btnCartInView;
    @BindView(R.id.txtStockMessage) TextView txtStockMessage;

    @BindView(R.id.sizeContainer) LinearLayout sizeContainer;
    @BindView(R.id.size) Spinner sizeSelector;

    public ProductDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if(bundle!=null){
            product = (ProductDTO) bundle.getSerializable("product");
            setupProductPage();
        }
    }

    private void setupProductPage() {
        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.height = width;

        imgDisplayPager.setLayoutParams(layoutParams);


        imgDisplayPager.setAdapter(new SlidingImageAdapter(this.getContext(),product.getProductImages()));
        imgDisplayPager.setMinimumHeight(width);
        txtPrice.setText("US$ ".concat(String.valueOf(product.getPrice())));
        txtTitle.setText(product.getTitle());

        List<ProductSizesDTO> prodSizes = product.getProductSizes();
        List<String> sizes = new ArrayList<>();
        for(ProductSizesDTO prodSize : prodSizes){
            sizes.add(prodSize.getSizes().getSize());
        }

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, sizes);
        sizeAdapter.setDropDownViewResource(R.layout.spinner_item_view);
        sizeSelector.setAdapter(sizeAdapter);

        sizeSelector.setOnItemSelectedListener(new sizeSelectedListener());
        txtDesc.setText(product.getDescription());

//        if(reviewsList.size()==0){
//            txtRevName.setText("No Reviews Found!");
//            txtRevDesc.setVisibility(View.GONE);
//            txtRevText.setVisibility(View.GONE);
//        }else{
//            Reviews rev = reviewsList.get(reviewsList.size()-1);
//            List<User> user = User.find(User.class, "email=?", rev.getUserEmail());
//            txtRevName.setText(user.get(0).getfName().concat(" ").concat(user.get(0).getlName()));
//            txtRevDesc.setText(rev.getRevDesc());
//            float rating=0;
//            for(Reviews tempRev : reviewsList){
//                rating+=tempRev.getRating();
//            }
//            prodRating.setRating(rating/reviewsList.size());
//        }

    }

    private class sizeSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            ProductSizesDTO prodSize = product.getProductSizes().get(i);
            int qty = prodSize.getQuantity();
            if(qty!=0) {
                List<Integer> qtyList = new ArrayList<>();
                for(int x=1;x<=qty;x++){
                    qtyList.add(x);
                }

                ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, qtyList);
                qtyAdapter.setDropDownViewResource(R.layout.spinner_item_view);
                quantity.setAdapter(qtyAdapter);
            }else{
                quantity.setVisibility(View.GONE);
                btnAddToCart.setVisibility(View.GONE);
                btnOrderNow.setVisibility(View.GONE);
                btnCartInView.setVisibility(View.GONE);
                txtStockMessage.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
