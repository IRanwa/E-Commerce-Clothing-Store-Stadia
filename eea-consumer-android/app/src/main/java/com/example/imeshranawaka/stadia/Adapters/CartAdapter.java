package com.example.imeshranawaka.stadia.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.OrdersDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.Models.ProductSizesDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private List<OrderProductsDTO> mDataSet;
    private List<CheckBox> checkboxList;
    private List<btnDelete_onClick> btnDeleteList;
    private Context mContext;
    private FragmentActivity mActivity;
    private static ArrayList<View> viewsList;
    private View view;

    public CartAdapter(Context context, List<OrderProductsDTO> productsList, FragmentActivity activity, View view) {
        mDataSet = productsList;
        mContext = context;
        viewsList = new ArrayList<>();
        checkboxList = new ArrayList<>();
        btnDeleteList = new ArrayList<>();
        mActivity = activity;
        this.view = view;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cart_products_container,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder viewHolder, int i) {
        OrderProductsDTO orderProduct = mDataSet.get(i);
        ProductSizesDTO prodSize = orderProduct.getProductSizes();
        ProductDTO product = prodSize.getProduct();

        Display display = mActivity.getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.height = width/4;
        layoutParams.width = width/4;

        viewHolder.prodImg.setLayoutParams(layoutParams);

        byte[] decodedString = Base64.decode(product.getProductImages().get(0).getPath().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.prodImg.setImageBitmap(decodedByte);

        viewHolder.prodTitle.setText(product.getTitle());
        viewHolder.prodPrice.setText("US$"+String.valueOf(product.getPrice()));
        viewHolder.prodSize.setText("Size : "+prodSize.getSizes().getSize());

        int totalQty = prodSize.getQuantity();
        ArrayList<Integer> qtyList = new ArrayList<>();
        for(int count=1;count<=totalQty;count++){
            qtyList.add(count);
        }
        ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_view,qtyList);
        qtyAdapter.setDropDownViewResource(R.layout.spinner_item_view);
        viewHolder.prodQty.setAdapter(qtyAdapter);

        int orderProdQty = orderProduct.getQuantity();
        if(orderProdQty>totalQty){
            viewHolder.prodQty.setSelection(totalQty-1);
        }else{
            viewHolder.prodQty.setSelection(orderProdQty-1);
        }

        viewHolder.prodQty.setOnItemSelectedListener(new QtyChangeListener(orderProduct));
        checkboxList.add(viewHolder.checkbox);
        viewHolder.checkbox.setOnClickListener(new checkbox_onClick());

        btnDelete_onClick deleteAction = new btnDelete_onClick(orderProduct, i);
        btnDeleteList.add(deleteAction);
        viewHolder.btnDelete.setOnClickListener(deleteAction);

        viewHolder.btnUpdate.setOnClickListener(new btnUpdate_onClick(orderProduct));
    }

    private class QtyChangeListener implements AdapterView.OnItemSelectedListener {
        private OrderProductsDTO orderProduct;
        public QtyChangeListener(OrderProductsDTO orderProduct) {
            this.orderProduct = orderProduct;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int itemQty = Integer.parseInt(parent.getSelectedItem().toString());
//            orderProduct.setQuantity(itemQty);
//            orderProduct.save();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void checkboxall_onClick(boolean status){
        for(CheckBox checkbox : checkboxList){
            checkbox.setChecked(status);
        }
    }

    private class checkbox_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            boolean status = true;
            for(CheckBox checkbox : checkboxList){
                if(!checkbox.isChecked()){
                    status = false;
                    break;
                }
            }
            if(!status){
                ((CheckBox)mActivity.findViewById(R.id.checkboxAll)).setChecked(status);
            }else{
                ((CheckBox)mActivity.findViewById(R.id.checkboxAll)).setChecked(status);
            }
        }
    }

    private class btnUpdate_onClick implements View.OnClickListener {
        private OrderProductsDTO orderProduct;
        public btnUpdate_onClick(OrderProductsDTO orderProduct) {
            this.orderProduct = orderProduct;
        }

        @Override
        public void onClick(View v) {
//            ProductDetails prodDetails = new ProductDetails();
//            Bundle bundle = new Bundle();
//            bundle.putLong("prodNo",orderProduct.getProdId());
//            prodDetails.setArguments(bundle);
//
//            FragmentManager fm = mActivity.getSupportFragmentManager();
//            FragmentTransaction transaction = fm.beginTransaction();
//
//            /*int backStackEntry = fm.getBackStackEntryCount();
//            List<Fragment> fragments = fm.getFragments();
//            if (backStackEntry > 0) {
//                for (int i = 0; i < backStackEntry; i++) {
//                    if(fragments.size()>i) {
//                        Fragment frag = fragments.get(0);
//                        if(frag.getTag()!=null && !frag.getTag().equals("MainMenu")) {
//                            transaction.remove(frag);
//                        }else{
//                            break;
//                        }
//                        fm.popBackStackImmediate();
//                    }
//                    fragments = fm.getFragments();
//                }
//            }*/
//
//            int backStackEntry = fm.getBackStackEntryCount();
//            List<Fragment> fragments = fm.getFragments();
//            if (backStackEntry > 0) {
//                for (int i = 0; i < backStackEntry; i++) {
//                    fm.popBackStackImmediate();
//                    if(fragments.size()<i) {
//                        Fragment frag = fragments.get(0);
//                        transaction.remove(frag);
//                        if(frag.getTag()!=null && !frag.getTag().toString().equals("MainMenu")) {
//                            break;
//                        }
//                    }
//                    fragments = fm.getFragments();
//                }
//            }
//
//            transaction.replace(R.id.mainFragment,
//                    prodDetails,"ProductDetails");
//            transaction.addToBackStack("MainMenu");
//            transaction.commit();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.prodImg) ImageView prodImg;
        @BindView(R.id.txtProductTitle) TextView prodTitle;
        @BindView(R.id.txtPrice) TextView prodPrice;
        @BindView(R.id.txtSize) TextView prodSize;
        @BindView(R.id.noOfQuantity) Spinner prodQty;
        @BindView(R.id.checkBox) CheckBox checkbox;
        @BindView(R.id.btnDelete) ImageView btnDelete;
        @BindView(R.id.btnUpdate) ImageView btnUpdate;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }


    }

    private class btnDelete_onClick implements View.OnClickListener {
        private OrderProductsDTO orderProduct;
        private int position;
        public btnDelete_onClick(OrderProductsDTO orderProduct, int position) {
            this.orderProduct = orderProduct;
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            deleteItem(position,orderProduct);
        }
    }

    public void btnDeleteSelected_onClick(View v){
        int checkboxSize = checkboxList.size();
        List<OrderProductsDTO> orderProdList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for(int count=0;count<checkboxSize;count++){
            if(checkboxList.get(count).isChecked()){
                OrderProductsDTO orderProd = mDataSet.get(count);
                ProductSizesDTO prodSize = orderProd.getProductSizes();
                ProductDTO product = prodSize.getProduct();
                product.setCreatedDate(null);
                product.setModifyDate(null);
                prodSize.setProduct(product);

                OrderProductsDTO newOrderProd = new OrderProductsDTO();
                newOrderProd.setProductSizes(prodSize);
                orderProdList.add(newOrderProd);
                countList.add(count);
            }
        }

        if(orderProdList.size()>0){
            OrdersDTO order = mDataSet.get(0).getOrders();
            OrdersDTO removeOrder = new OrdersDTO();
            removeOrder.setId(order.getId());
            removeOrder.setOrderProducts(orderProdList);

            Call<Boolean> callback = APIBuilder.createAuthBuilder(mContext).deleteCartItems(removeOrder);
            callback.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.code()==401){
                        APIBuilder.Logout(mContext,mActivity);
                    }else {
                        Boolean status = response.body();
                        System.out.println(status);
                        if(status!=null && status){

                            int count = 0;
                            for(int x=0;x<checkboxSize;x++){
                                if(x==countList.get(count)){
                                    notifyItemRemoved(x-count);
                                    mDataSet.remove(x-count);
                                    checkboxList.remove(x-count);
                                    btnDeleteList.remove(x-count);
                                    count++;
                                }


                            }
                        }else{
                            Snackbar snackBar = Snackbar.make(view, "Cart Item Delete Un-Successful!", Snackbar.LENGTH_LONG);
                            snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                            snackBar.show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Snackbar snackBar = Snackbar.make(view, "Cart Item Delete Un-Successful!", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                    snackBar.show();
                }
            });

        }


    }

    private void deleteItem(int position,OrderProductsDTO orderProduct){
        Call<Boolean> callback = APIBuilder.createAuthBuilder(mContext).deleteCartItem(orderProduct.getOrders().getId(), orderProduct.getProductSizes().getId());
        callback.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code()==401){
                    APIBuilder.Logout(mContext,mActivity);
                }else {
                    Boolean status = response.body();
                    if(status){
                        notifyItemRemoved(position);
                        mDataSet.remove(position);
                        checkboxList.remove(position);
                        btnDeleteList.remove(position);
                    }else{
                        Snackbar snackBar = Snackbar.make(view, "Cart Item Delete Un-Successful!", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                        snackBar.show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(view, "Cart Item Delete Un-Successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }


}
