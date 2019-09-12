package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import com.braintreepayments.cardform.view.CardForm;
import com.example.imeshranawaka.stadia.EnumClasses.PaymentMethod;
import com.example.imeshranawaka.stadia.Models.OrdersDTO;
import com.example.imeshranawaka.stadia.Stadia;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.PlaceOrderAdapter;
import com.example.imeshranawaka.stadia.Models.AddressDTO;
import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.UserDTO;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;

import java.util.ArrayList;
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
public class PlaceOrderView extends Fragment {

    @BindView(R.id.productsList) RecyclerView productsRecycleView;
    @BindView(R.id.cardContainer) LinearLayout cardContainer;
    @BindView(R.id.saveCardContainer) LinearLayout saveCardContainer;
    @BindView(R.id.newCardContainer) LinearLayout newCardContainer;
    @BindView(R.id.txtSaveCard)
    TextView txtSaveCard;

    @BindView(R.id.card_form) CardForm cardForm;

    @BindView(R.id.shippingAdd) Spinner shippingAdd;
    @BindView(R.id.billingAdd) Spinner billingAdd;
    @BindView(R.id.paymentMethod) RadioGroup paymentMethod;

    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;

    private Unbinder unbinder;
    private int defaultId;
    private boolean cardSave = false;
    private List<AddressDTO> addressList;
    private List<OrderProductsDTO> emptyOrderProdList;

    public PlaceOrderView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_place_order_view, container, false);
        unbinder = ButterKnife.bind(this,view);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_DISABLED)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Save").setup((AppCompatActivity) getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        defaultId  = paymentMethod.getCheckedRadioButtonId();
        paymentMethod.setOnCheckedChangeListener(new paymentMethodChange());
        getAddressList();
        getOrderProductList();
    }

    private void getOrderProductList() {
        UserDTO user = new UserDTO();
        user.setEmail(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());

        Call<List<OrderProductsDTO>> callback = APIBuilder.createAuthBuilder(getContext()).getCart(user);
        callback.enqueue(new Callback<List<OrderProductsDTO>>() {
            @Override
            public void onResponse(Call<List<OrderProductsDTO>> call, Response<List<OrderProductsDTO>> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    List<OrderProductsDTO> orderProdList = response.body();
                    List<OrderProductsDTO> availableOrderProdList = new ArrayList<>();
                    emptyOrderProdList = new ArrayList<>();
                    double totalPrice = 0;
                    for(OrderProductsDTO orderProd : orderProdList){
                        if(orderProd.getQuantity()>0){
                            availableOrderProdList.add(orderProd);
                            totalPrice += orderProd.getQuantity() * orderProd.getProductSizes().getProduct().getPrice();
                        }else{
                            emptyOrderProdList.add(orderProd);
                        }
                    }
                    txtTotalPrice.setText("US$".concat(String.valueOf(totalPrice)));

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    productsRecycleView.setLayoutManager(layoutManager);

                    PlaceOrderAdapter adapter = new PlaceOrderAdapter(getContext(), availableOrderProdList, getActivity(),"PlaceOrder");
                    productsRecycleView.setAdapter(adapter);
                    productsRecycleView.setNestedScrollingEnabled(false);


                }
            }

            @Override
            public void onFailure(Call<List<OrderProductsDTO>> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(getView(), "Retrieve Address Un-Successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }

    private void getAddressList() {
        Call<List<AddressDTO>> callback = APIBuilder.createAuthBuilder(getContext()).getAddressList(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());
        callback.enqueue(new Callback<List<AddressDTO>>() {
            @Override
            public void onResponse(Call<List<AddressDTO>> call, Response<List<AddressDTO>> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    addressList = response.body();
                    List<String> addresses = new ArrayList<>();
                    for(AddressDTO address : addressList){
                        addresses.add(address.getAddress().concat(", ").concat(address.getCity())
                                .concat(", ").concat(address.getProvince()).concat(", ").concat(address.getZipCode()).concat(", ").concat(address.getCountry()));
                    }

                    ArrayAdapter<String> shippingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, addresses);
                    shippingAdapter.setDropDownViewResource(R.layout.spinner_item_view);
                    shippingAdd.setAdapter(shippingAdapter);

                    ArrayAdapter<String> billingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, addresses);
                    billingAdapter.setDropDownViewResource(R.layout.spinner_item_view);
                    billingAdd.setAdapter(billingAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<AddressDTO>> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(getView(), "Retrieve Address Un-Successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }

    private class paymentMethodChange implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(i-defaultId==1){
                cardContainer.setVisibility(View.VISIBLE);
                if(cardSave){
                    newCardContainer.setVisibility(View.GONE);
                    saveCardContainer.setVisibility(View.VISIBLE);
                }else{
                    newCardContainer.setVisibility(View.VISIBLE);
                    saveCardContainer.setVisibility(View.GONE);
                }
            }else{
                cardContainer.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.btnCardSave)
    public void btnCardSave_Click(){
        if(cardForm.isValid()){
            String cardNo = cardForm.getCardNumber();
            String hiddenNo = cardNo.substring(0,4);
            String[] splitNo = cardNo.split("");
            for(int x=0;x<splitNo.length-5;x++){
                if(x%4==0){
                    hiddenNo += "-X";
                }else{
                    hiddenNo += "X";
                }
            }
            txtSaveCard.setText(hiddenNo);

            newCardContainer.setVisibility(View.GONE);
            saveCardContainer.setVisibility(View.VISIBLE);
            cardSave = true;
        }else{
            Snackbar snackBar = Snackbar.make(getView(), "Card Information Incorrect!", Snackbar.LENGTH_LONG);
            snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
            snackBar.show();
        }
    }

    @OnClick(R.id.btnConfirmOrder)
    public void btnConfirmOrder_Click(){

        OrdersDTO order = new OrdersDTO();
        boolean ststua = true;
        int paymentId = paymentMethod.getCheckedRadioButtonId() - defaultId;
        if(paymentId==0){
            order.setPaymentMethod(PaymentMethod.Cash.toString());
        }else if(paymentId==1){
            if(!cardForm.isValid()){
                ststua = false;
            }else {
                order.setPaymentMethod(PaymentMethod.Card.toString());
            }
        }else if(paymentId==2){
            order.setPaymentMethod(PaymentMethod.Paypal.toString());
        }

        if(ststua) {
            UserDTO user = new UserDTO();
            user.setEmail(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());
            order.setUser(user);

            order.setBillingAddress(addressList.get(Integer.parseInt(String.valueOf(billingAdd.getSelectedItemId()))));
            order.setShippingAddress(addressList.get(Integer.parseInt(String.valueOf(shippingAdd.getSelectedItemId()))));
            order.setOrderProducts(emptyOrderProdList);


            Call<Boolean> callback = APIBuilder.createAuthBuilder(getContext()).placeOrder(order);
            callback.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.code()==401){
                        APIBuilder.Logout(getContext(),getActivity());
                    }else{
                        if(response.body()){
                            Snackbar.make(getView(), "Place order successfully!", Snackbar.LENGTH_LONG).show();

                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.replace(R.id.subFragment, new ProductsView(), "ProductsView");
                            transaction.commit();

                        }else{
                            Snackbar snackBar = Snackbar.make(getView(), "Place order un-successful!", Snackbar.LENGTH_LONG);
                            snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                            snackBar.show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Snackbar snackBar = Snackbar.make(getView(), "Place order un-successful!", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                    snackBar.show();
                }
            });
        }else if(!ststua && paymentId==1){
            Snackbar snackBar = Snackbar.make(getView(), "Card information incorrect!", Snackbar.LENGTH_LONG);
            snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
            snackBar.show();
        }
    }
}
