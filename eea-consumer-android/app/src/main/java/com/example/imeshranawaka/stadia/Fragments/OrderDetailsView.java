package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.PlaceOrderAdapter;
import com.example.imeshranawaka.stadia.EnumClasses.OrderStatus;
import com.example.imeshranawaka.stadia.Models.AddressDTO;
import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.OrdersDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsView extends Fragment {

    @BindView(R.id.txtOrderStatus) TextView txtOrderStatus;
    @BindView(R.id.txtOrderPurchasedDate) TextView txtOrderPurchaseDate;
    @BindView(R.id.txtOrderDeliverDate) TextView txtOrderDeliverDate;
    @BindView(R.id.txtOrderCompleteDate) TextView txtOrderCompleteDate;

    @BindView(R.id.txtPDate) TextView txtPDate;
    @BindView(R.id.txtDDate) TextView txtDDate;
    @BindView(R.id.txtCDate) TextView txtCDate;

    @BindView(R.id.txtOrderNo) TextView txtOrderNo;
    @BindView(R.id.txtTotalPrice) TextView txtTotalPrice;
    @BindView(R.id.txtShippingName) TextView txtShippingName;
    @BindView(R.id.txtShippingAddress) TextView txtShippingAddress;
    @BindView(R.id.txtBillingName) TextView txtBillingName;
    @BindView(R.id.txtBillingAddress) TextView txtBillingAddress;

    @BindView(R.id.productsList)
    RecyclerView productsListRecycle;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnConfirm) Button btnConfirm;
    @BindView(R.id.btnContainer)
    LinearLayout btnContainer;

    private Unbinder unbinder;
    private OrdersDTO order;
    public OrderDetailsView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details_view, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if(bundle!=null){
            long orderNo = bundle.getLong("OrderNo");
            setupDetails(orderNo);
        }
    }

    private void setupDetails(long orderNo) {
        Call<OrdersDTO> callback = APIBuilder.createAuthBuilder(getContext()).getOrder(orderNo);
        callback.enqueue(new Callback<OrdersDTO>() {
            @Override
            public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    order = response.body();
                    if(order==null){
                        Snackbar snackBar = Snackbar.make(getView(), "Order not found!", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                        snackBar.show();
                    }else{
                        String orderStatus = order.getStatus();
                        txtOrderStatus.setText(orderStatus);

                        switch(order.getStatus()){
                            case "Completed":
                            case "Cancelled":
                            case "Delivered":
                                if(order.getDeliverDate()==null){
                                    txtOrderDeliverDate.setVisibility(View.GONE);
                                    txtDDate.setVisibility(View.GONE);
                                }else {
                                    txtOrderDeliverDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getDeliverDate()));
                                }
                            case "Pending":
                                txtOrderPurchaseDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getPurchasedDate()));
                                txtOrderDeliverDate.setVisibility(View.GONE);
                                txtDDate.setVisibility(View.GONE);
                        }

                        if(order.getStatus().equalsIgnoreCase(OrderStatus.Completed.toString())){
                            txtOrderCompleteDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getOrderCompleteDate()));
                        }else if(order.getStatus().equalsIgnoreCase(OrderStatus.Cancelled.toString())){
                            txtOrderCompleteDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getOrderCompleteDate()));
                        }else{
                            txtOrderCompleteDate.setVisibility(View.GONE);
                            txtCDate.setVisibility(View.GONE);
                        }

                        txtOrderNo.setText(String.valueOf(orderNo));
                        List<OrderProductsDTO> orderProductList = order.getOrderProducts();

                        double price=0;
                        for(int count=0;count<orderProductList.size();count++) {
                            OrderProductsDTO orderProduct = orderProductList.get(count);
                            ProductDTO product = orderProduct.getProductSizes().getProduct();
                            price += product.getPrice() * orderProduct.getQuantity();
                        }
                        txtTotalPrice.setText("US$"+price);

                        AddressDTO address = order.getShippingAddress();
                        if(address!=null) {
                            txtShippingName.setText(address.getfName() + " " + address.getlName());
                            txtShippingAddress.setText(address.getAddress().concat(", ").concat(address.getCity())
                                    .concat(", ").concat(address.getProvince()).concat(", ").concat(address.getZipCode()).concat(", ").concat(address.getCountry()));
                        }else{
                            txtShippingName.setText("Shipped Address Not Found!");
                            txtShippingName.setTextColor(Color.RED);
                            txtShippingAddress.setVisibility(View.GONE);
                        }

                        address = order.getBillingAddress();
                        if(address!=null) {
                            txtBillingName.setText(address.getfName() + " " + address.getlName());
                            txtBillingAddress.setText(address.getAddress().concat(", ").concat(address.getCity())
                                    .concat(", ").concat(address.getProvince()).concat(", ").concat(address.getZipCode()).concat(", ").concat(address.getCountry()));
                        }else{
                            txtBillingName.setText("Billing Address Not Found!");
                            txtBillingName.setTextColor(Color.RED);
                            txtBillingAddress.setVisibility(View.GONE);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        productsListRecycle.setLayoutManager(layoutManager);

                        PlaceOrderAdapter adapter = new PlaceOrderAdapter(getContext(), orderProductList, getActivity(),orderStatus);
                        productsListRecycle.setAdapter(adapter);
                        productsListRecycle.setNestedScrollingEnabled(false);

                        if(orderStatus.equalsIgnoreCase(OrderStatus.Pending.toString())){
                            btnConfirm.setVisibility(View.GONE);
                        }else if(orderStatus.equalsIgnoreCase(OrderStatus.Completed.toString()) || orderStatus.equalsIgnoreCase(OrderStatus.Cancelled.toString())){
                            btnContainer.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersDTO> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(getView(), "Order details retrieve un-successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }

    @OnClick(R.id.btnConfirm)
    public void btnConfirm(){
        updateOrder(OrderStatus.Completed.toString());
    }

    @OnClick(R.id.btnCancel)
    public void btnCancel(){
        updateOrder(OrderStatus.Cancelled.toString());
    }

    private void updateOrder(String status){
        order.setStatus(status);
        Call<Boolean> callback = APIBuilder.createAuthBuilder(getContext()).updateOrderStatus(order);
        callback.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    if(response.body()){
                        setupDetails(order.getId());
                    }else{
                        Snackbar snackBar = Snackbar.make(getView(), "Order update un-successful!", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(getView(), "Order update un-successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }
}
