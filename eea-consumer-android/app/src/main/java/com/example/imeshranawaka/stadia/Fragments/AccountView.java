package com.example.imeshranawaka.stadia.Fragments;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.EnumClasses.Gender;
import com.example.imeshranawaka.stadia.Models.LoginDTO;
import com.example.imeshranawaka.stadia.Models.UserDTO;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
public class AccountView extends Fragment {
    @BindView(R.id.genderSelection)
    Spinner genderSelection;
    @BindView(R.id.btnCalendar)
    Button btnCalendar;
    @BindView(R.id.btnUpdate) ImageView btnUpdate;

    @BindView(R.id.txtFName)
    EditText txtFName;
    @BindView(R.id.txtLName) EditText txtLName;
    @BindView(R.id.txtUserCon) EditText txtContact;
    @BindView(R.id.txtDOB) EditText txtDOB;
    private Unbinder unbinder;
    private boolean updateState = false;
    private Date selectedDate = null;
    public AccountView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        UserDTO user = new UserDTO();
        user.setEmail(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());
        Call<UserDTO> callback = APIBuilder.createAuthBuilder(getContext()).GetUserDetails(user);
        callback.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    UserDTO userDTO = response.body();
                    setUserDetails(userDTO);
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Snackbar snackBar = Snackbar.make(getView(), "User Details Retrieve Un-Successful!", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                snackBar.show();
            }
        });
    }

    private void setUserDetails(UserDTO userDTO){
        genderSelection.setEnabled(false);

        txtFName.setText(userDTO.getLogin().getfName());
        txtLName.setText(userDTO.getLogin().getlName());
        if(userDTO.getContactNo()!=null) {
            txtContact.setText(userDTO.getContactNo());
        }
        if(userDTO.getDob()!=null) {
            txtDOB.setText(userDTO.getDob().toLocaleString());
        }
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.spinner_item_view);
        genderAdapter.setDropDownViewResource(R.layout.spinner_item_view);
        genderSelection.setAdapter(genderAdapter);

        if(userDTO.getGender()!=null){
            if(userDTO.getGender().equals(Gender.M)){
                genderSelection.setSelection(1);
            }else{
                genderSelection.setSelection(2);
            }
        }
    }

    @OnClick(R.id.btnUpdate)
    public void btnUpdate_onClick(){
        if(updateState) {
            String fName = txtFName.getText().toString();
            String lName = txtLName.getText().toString();
            String contactNo = txtContact.getText().toString();
            String dob = txtDOB.getText().toString();
            String gender = genderSelection.getSelectedItem().toString();

            if(fName.isEmpty() || lName.isEmpty()){
                Toast.makeText(getContext(),"Please fill first and last name!", Toast.LENGTH_SHORT).show();
            }else{
                String email = SharedPreferenceUtility.getInstance(getContext()).getUserEmail();
                UserDTO user = new UserDTO();
                if(!contactNo.equalsIgnoreCase("")) {
                    user.setContactNo(contactNo);
                }
                if(!dob.equalsIgnoreCase("")) {

                    user.setDob(selectedDate);
//                    try {
//                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz");
//                        Date date = sdf.parse(dob);
//                        user.setDob(selectedDate);
//                    }catch(Exception ex){}
                }
                if(!gender.equalsIgnoreCase("")){
                    if(gender.equalsIgnoreCase("male")){
                        user.setGender(Gender.M);
                    }else{
                        user.setGender(Gender.F);
                    }
                }

                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setfName(fName);
                loginDTO.setlName(lName);
                user.setLogin(loginDTO);
                user.setEmail(email);



                Call<UserDTO> callback = APIBuilder.createAuthBuilder(getContext()).UpdateUser(email, user);
                callback.enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        if(response.code()==401){
                            APIBuilder.Logout(getContext(),getActivity());
                        }else{
                            UserDTO user = response.body();
                            if(user!=null){
                                setUserDetails(user);

                                txtFName.setEnabled(false);
                                txtLName.setEnabled(false);
                                txtContact.setEnabled(false);
                                btnCalendar.setEnabled(false);
                                genderSelection.setEnabled(false);
                                btnUpdate.setImageResource(R.drawable.update_icon);
                                updateState = false;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {

                    }
                });
//                SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
//                String loginEmail = sharedPref.getUserEmail();
//                User user = User.find(User.class,"email=?",loginEmail).get(0);
//
//                user.setfName(fName);
//                user.setlName(lName);
//                user.setContactNo(contactNo);
//                user.setDob(dob);
//                user.setUserGender(gender);
//                user.save();
//                fragment_actions.getIntance(this).btnBack_onClick();
//
//                NavigationView nav_view = getActivity().findViewById(R.id.nav_view);
//                TextView name = nav_view.getHeaderView(0).findViewById(R.id.txtNavName);
//                TextView email = nav_view.getHeaderView(0).findViewById(R.id.txtHeadEmail);
//
//                name.setText(user.getfName()+" "+user.getlName());
//                email.setText(loginEmail);

            }

        }else{
            txtFName.setEnabled(true);
            txtLName.setEnabled(true);
            txtContact.setEnabled(true);
            btnCalendar.setEnabled(true);
            genderSelection.setEnabled(true);
            btnUpdate.setImageResource(R.drawable.done_icon);
            updateState = true;
        }
    }


    @OnClick(R.id.btnCalendar)
    public void btnCalendar_onClick(){
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day= cal.get(Calendar.DATE);

        new DatePickerDialog(getContext(), new dataPickerListener(),year,month,day).show();
    }

    private class dataPickerListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(cal.getTime());
            selectedDate = cal.getTime();
            txtDOB.setText(date);
        }
    }
}
