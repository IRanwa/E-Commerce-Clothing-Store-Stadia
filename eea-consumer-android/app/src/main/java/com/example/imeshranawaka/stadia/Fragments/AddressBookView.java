package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.AddressAdapter;
import com.example.imeshranawaka.stadia.Models.AddressDTO;
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
public class AddressBookView extends Fragment {

    @BindView(R.id.addressList)
    RecyclerView addressListRecycle;
    private Unbinder unbinder;
    public AddressBookView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_book_view, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        setList();
    }

    private void setList() {
        Call<List<AddressDTO>> callback = APIBuilder.createAuthBuilder(getContext()).getAddressList(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());
        callback.enqueue(new Callback<List<AddressDTO>>() {
            @Override
            public void onResponse(Call<List<AddressDTO>> call, Response<List<AddressDTO>> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    List<AddressDTO> addressList = response.body();
                    if(addressList.size()!=0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        addressListRecycle.setLayoutManager(layoutManager);

                        AddressAdapter addressAdapter = new AddressAdapter(getContext(), addressList, getFragmentManager(),getActivity());
                        addressListRecycle.setAdapter(addressAdapter);
                    }else{
                        addressListRecycle.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AddressDTO>> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(getView(), "Address information retrieve un-successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }

    @OnClick(R.id.btnAddAddress)
    public void btnAddAddress_onClick(){
        FragmentManager fm = getFragmentManager();
        NewAddressView newAddress = new NewAddressView();
        FragmentTransaction transaction = fm.beginTransaction().replace(R.id.subFragment
                , newAddress,"NewAddressView");
        transaction.addToBackStack("AddressBookView");
        transaction.commit();
    }
}
