package com.example.imeshranawaka.stadia.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imeshranawaka.stadia.EnumClasses.OrderStatus;
import com.example.imeshranawaka.stadia.Fragments.OrderDetailsView;
import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.OrdersDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Context mContext;
    private List<OrdersDTO> mDataset;
    private ArrayList<View> viewList;
    private FragmentActivity activity;

    public OrdersAdapter(Context mContext, List<OrdersDTO> mDataset, FragmentActivity activity) {
        this.mContext = mContext;
        this.mDataset = mDataset;
        this.viewList = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_container, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrdersDTO order = mDataset.get(i);
        viewHolder.txtOrderNo.setText("Order No : "+order.getId());

        switch(order.getStatus()){
            case "Completed":
            case "Cancelled":
            case "Delivered":
                if(order.getDeliverDate()==null){
                    viewHolder.txtOrderDeliverDate.setVisibility(View.GONE);
                }else {
                    viewHolder.txtOrderDeliverDate.setText("Order Delivered Date : ".concat(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getDeliverDate())));
                }
            case "Pending":
                viewHolder.txtOrderPurchaseDate.setText("Order Purchased Date : ".concat(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getPurchasedDate())));
                viewHolder.txtOrderDeliverDate.setVisibility(View.GONE);
        }

        if(order.getStatus().equalsIgnoreCase(OrderStatus.Completed.toString())){
            viewHolder.txtOrderCompleteDate.setText("Order Complete Date : ".concat(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getOrderCompleteDate())));
        }else if(order.getStatus().equalsIgnoreCase(OrderStatus.Cancelled.toString())){
            viewHolder.txtOrderCompleteDate.setText("Order Cancelled Date : ".concat(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getOrderCompleteDate())));
        }else{
            viewHolder.txtOrderCompleteDate.setVisibility(View.GONE);
        }
        viewHolder.txtStatus.setText("Order Status : "+order.getStatus());

        List<OrderProductsDTO> orderProductList = order.getOrderProducts();

        double price=0;
        for(int count=0;count<orderProductList.size();count++){
            OrderProductsDTO orderProduct = orderProductList.get(count);
            ProductDTO product = orderProduct.getProductSizes().getProduct();
            price += product.getPrice() * orderProduct.getQuantity();

            if(count==0){
                Display display = activity.getWindowManager(). getDefaultDisplay();
                Point size = new Point();
                display. getSize(size);
                int width = size.y;

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.height = width/6;
                layoutParams.width = width/6;

                viewHolder.dislayImage.setLayoutParams(layoutParams);

                byte[] decodedString = Base64.decode(product.getProductImages().get(0).getPath().split(",")[1], Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                viewHolder.dislayImage.setImageBitmap(decodedByte);

                viewHolder.txtProductTitle.setText(product.getTitle());
            }
        }
        viewHolder.txtTotalPrice.setText("Total Price : US$."+price);
        viewHolder.orderContainer.setOnClickListener(new order_onClick(order.getId()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.orderContainer) LinearLayout orderContainer;
        @BindView(R.id.txtOrderNo) TextView txtOrderNo;
        @BindView(R.id.txtOrderPurchaseDate) TextView txtOrderPurchaseDate;
        @BindView(R.id.txtOrderDeliverDate) TextView txtOrderDeliverDate;
        @BindView(R.id.txtOrderCompleteDate) TextView txtOrderCompleteDate;
        @BindView(R.id.txtStatus) TextView txtStatus;
        @BindView(R.id.displayImage) ImageView dislayImage;
        @BindView(R.id.txtProductTitle) TextView txtProductTitle;
        @BindView(R.id.txtTotalPrice) TextView txtTotalPrice;
        public ViewHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
            viewList.add(v);
        }
    }

    private class order_onClick implements View.OnClickListener {
        private long id ;
        public order_onClick(Long id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putLong("OrderNo",id);
            OrderDetailsView orderdetails = new OrderDetailsView();
            orderdetails.setArguments(bundle);

            FragmentManager fm = activity.getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction().replace(R.id.subFragment,orderdetails , "OrderDetailsView");
            transaction.addToBackStack("OrdersView");
            transaction.commit();
        }
    }
}

