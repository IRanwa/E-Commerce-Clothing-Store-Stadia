package com.example.imeshranawaka.stadia.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Models.LoginDTO;
import com.example.imeshranawaka.stadia.R;

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

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("imesh");
        loginDTO.setPass("imesh");
        Call<LoginDTO> responseBodyCall = APIBuilder.createBuilder().createAuthenticationToken(loginDTO);
        responseBodyCall.enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                LoginDTO loginDTO = response.body();
                //System.out.println(loginDTO.getJwttoken());
            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {

            }
        });
    }

}
