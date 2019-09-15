package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.OrdersAdapter;
import com.example.imeshranawaka.stadia.Models.OrdersDTO;
import com.example.imeshranawaka.stadia.Models.UserDTO;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersView extends Fragment {
    @BindView(R.id.ordersList) RecyclerView ordersListRecycle;
    private Unbinder unbinder;
    public OrdersView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_view, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setupOrdersList();
    }

    private void setupOrdersList() {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());

        Call<List<OrdersDTO>> callback = APIBuilder.createAuthBuilder(getContext()).getOrdersList(userDTO);
        callback.enqueue(new Callback<List<OrdersDTO>>() {
            @Override
            public void onResponse(Call<List<OrdersDTO>> call, Response<List<OrdersDTO>> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    List<OrdersDTO> ordersList = response.body();
                    if(ordersList.size()>0){
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        ordersListRecycle.setLayoutManager(layoutManager);

                        OrdersAdapter ordersAdapter = new OrdersAdapter(getContext(), ordersList, getActivity());
                        ordersListRecycle.setAdapter(ordersAdapter);
                    }else{
                        ordersListRecycle.setVisibility(View.GONE);
                        Snackbar.make(getView(), "No orders found!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrdersDTO>> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(getView(), "Orders retrieve un-successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }
}
