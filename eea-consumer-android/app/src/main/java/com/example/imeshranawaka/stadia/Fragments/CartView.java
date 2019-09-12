package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.CartAdapter;
import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.UserDTO;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;

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
public class CartView extends Fragment {
    @BindView(R.id.orderProductsList)
    RecyclerView productsRecycleView;
    CartAdapter adapter;
    private Unbinder unbinder;
    public CartView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_view, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setupShoppingCart();
    }

    private void setupShoppingCart() {
        UserDTO user = new UserDTO();
        user.setEmail(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());

        Call<List<OrderProductsDTO>> callback = APIBuilder.createAuthBuilder(getContext()).getCart(user);
        callback.enqueue(new Callback<List<OrderProductsDTO>>() {
            @Override
            public void onResponse(Call<List<OrderProductsDTO>> call, Response<List<OrderProductsDTO>> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else {
                    List<OrderProductsDTO> orderProds = response.body();
                    if(orderProds!=null && orderProds.size()>0) {

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        productsRecycleView.setLayoutManager(layoutManager);

                        adapter = new CartAdapter(getContext(), orderProds, getActivity(),getView());
                        productsRecycleView.setAdapter(adapter);
                    }else{
                        //productsRecycleView.setVisibility(View.GONE);
                        Snackbar snackBar = Snackbar.make(getView(), "No Items Found In The Cart!", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderProductsDTO>> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.checkboxAll)
    public void checkboxAll_onClick(View v){
        CheckBox checkbox = v.findViewById(R.id.checkboxAll);
        boolean status = false;
        if(checkbox.isChecked()){
            status = true;
        }
        adapter.checkboxall_onClick(status);
    }

    @OnClick(R.id.btnDeleteSelected)
    public void btnDeleteSelected_onClick(View v){
        adapter.btnDeleteSelected_onClick(v);
    }

    @OnClick(R.id.btnCheckout)
    public void btnCheckout_onClick(View v){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        PlaceOrderView placeOrder = new PlaceOrderView();

        transaction.replace(R.id.subFragment, placeOrder,"PlaceOrderView");
        transaction.addToBackStack("CartView");
        transaction.commit();
    }
}
