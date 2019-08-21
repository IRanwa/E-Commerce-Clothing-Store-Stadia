package com.example.imeshranawaka.stadia.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    @BindView(R.id.txtEmail) TextView txtEmail;
    @BindView(R.id.txtPass) TextView txtPass;
    private Unbinder unbinder;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnSignIn)
    public void btnSignIn(){
        String email = txtEmail.getText().toString();
        String pass = txtEmail.getText().toString();

    }

}
