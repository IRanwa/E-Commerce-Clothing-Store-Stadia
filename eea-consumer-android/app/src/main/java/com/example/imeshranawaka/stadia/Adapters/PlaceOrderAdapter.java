package com.example.imeshranawaka.stadia.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.Models.SizesDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.ViewHolder> {
    private List<OrderProductsDTO> mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;
    private FragmentActivity mActivity;
    private String status;

    public PlaceOrderAdapter(Context context, List<OrderProductsDTO> categoryList, FragmentActivity activity, String status) {
        mDataSet = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
        this.mActivity = activity;
        this.status = status;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.productImage) ImageView productImage;
        @BindView(R.id.prodTitle) TextView prodTitle;
        @BindView(R.id.prodPrice) TextView prodPrice;
        @BindView(R.id.prodSize) TextView prodSize;
        @BindView(R.id.prodQuantity) TextView prodQuantity;
        @BindView(R.id.btnReview) Button btnReview;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }
    }

    @Override
    public PlaceOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.checkout_product_container,viewGroup,false);
        PlaceOrderAdapter.ViewHolder vh = new PlaceOrderAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOrderAdapter.ViewHolder viewHolder, int position) {
        OrderProductsDTO orderProduct =  mDataSet.get(position);
        SizesDTO prodSize = orderProduct.getProductSizes().getSizes();
        ProductDTO product = orderProduct.getProductSizes().getProduct();

        viewHolder.prodTitle.setText(product.getTitle());
        viewHolder.prodPrice.setText("Price : US$"+String.valueOf(product.getPrice()));
        viewHolder.prodSize.setText("Size : "+prodSize.getSize());
        viewHolder.prodQuantity.setText("Quantitiy : ".concat(String.valueOf(orderProduct.getQuantity())));

        Display display = mActivity.getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;
        int height = size. y;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.height = width/4;
        layoutParams.width = width/4;

        viewHolder.productImage.setLayoutParams(layoutParams);

        byte[] decodedString = Base64.decode(product.getProductImages().get(0).getPath().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.productImage.setImageBitmap(decodedByte);

//        if(status.equalsIgnoreCase("Order Completed")){
//            String email = SharedPreferenceUtility.getInstance(mContext).getUserEmail();
//            List<Reviews> reviews = Reviews.find(Reviews.class, "order_Prod_Id=? and user_Email=?", String.valueOf(orderProduct.getId()), email);
//            if(reviews.size()==0) {
//                viewHolder.btnReview.setVisibility(View.VISIBLE);
//                viewHolder.btnReview.setOnClickListener(new review_onClick(orderProduct,position));
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

//    private class review_onClick implements View.OnClickListener {
//        Order_Product orderProduct;
//        int position;
//        public review_onClick(Order_Product orderProduct, int position) {
//            this.orderProduct = orderProduct;
//            this.position = position;
//        }
//
//        @Override
//        public void onClick(View v) {
//            final Dialog dialog = new Dialog(mContext);
//            dialog.setContentView(R.layout.review_item_container);
//
//            WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
//            lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            dialog.getWindow().setAttributes(lWindowParams);
//            dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String email = SharedPreferenceUtility.getInstance(mContext).getUserEmail();
//                    RatingBar rating = dialog.findViewById(R.id.rateBar);
//                    EditText revDesc = dialog.findViewById(R.id.txtReview);
//
//                    Reviews review = new Reviews(orderProduct.getId(),orderProduct.getProdId(),email,revDesc.getText().toString(),rating.getRating());
//                    review.save();
//                    Toast.makeText(mContext,
//                            "Review Added Successfully!",
//                            Toast.LENGTH_LONG).show();
//                    dialog.dismiss();
//
//                    notifyItemChanged(position);
//                }
//            });
//            dialog.show();
//        }
//    }
}

