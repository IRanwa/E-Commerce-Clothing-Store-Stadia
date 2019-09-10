package com.example.imeshranawaka.stadia.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileView extends Fragment {
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.txtEmail) TextView txtEmail;
    private Unbinder unbinder;
    public ProfileView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        txtUserName.setText(sharedPref.getUserName());
        txtEmail.setText(sharedPref.getUserEmail());
    }

    @OnClick(R.id.btnAccInfo)
    public void btnAccInfo_onClick(){
        AccountView accInfo = new AccountView();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.subFragment,accInfo,"AccountView");
        transaction.addToBackStack("MyAccount");

        transaction.commit();
    }

    @OnClick(R.id.btnAddBook)
    public void btnAddBook_onClick(){
        AddressBookView addressBookView = new AddressBookView();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.subFragment,addressBookView,"AddressBookView");
        transaction.addToBackStack("ProfileView");
        transaction.commit();
    }

}
