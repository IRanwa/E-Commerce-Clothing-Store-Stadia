package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Models.LoginDTO;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;

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
public class Login extends Fragment {

    @BindView(R.id.txtEmail) TextView txtEmail;
    @BindView(R.id.txtPass) TextView txtPass;
    private View view;
    private Unbinder unbinder;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnSignIn)
    public void btnSignIn(){
        String email = txtEmail.getText().toString();
        String pass = txtEmail.getText().toString();

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPass(pass);
        Call<LoginDTO> responseBodyCall = APIBuilder.createBuilder().createAuthenticationToken(loginDTO);
        responseBodyCall.enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                LoginDTO loginDTO = response.body();

                if(loginDTO!=null) {
                    SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getActivity());
                    sharedPref.setUserEmail(email);
                    sharedPref.setUserPass(pass);
                    sharedPref.setUserName(loginDTO.getfName() + " " + loginDTO.getlName());
                    sharedPref.setUserToken(loginDTO.getJwttoken());
                }else{
                    Snackbar snackBar = Snackbar.make(getView(), "User Login Un-Successful!", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                    snackBar.show();
                }
            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {
                Snackbar.make(view,"User Login Un-Successful!",Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
