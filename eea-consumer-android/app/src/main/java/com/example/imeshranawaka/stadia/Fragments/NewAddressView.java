package com.example.imeshranawaka.stadia.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.EnumClasses.AddressType;
import com.example.imeshranawaka.stadia.Models.AddressDTO;
import com.example.imeshranawaka.stadia.Models.UserDTO;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
public class NewAddressView extends Fragment {
    @BindView(R.id.txtFName) EditText txtFName;
    @BindView(R.id.txtLName) EditText txtLName;
    @BindView(R.id.txtContactNo) EditText txtContactNo;
    @BindView(R.id.txtAddress) EditText txtAddress;
    @BindView(R.id.countryList) Spinner countryList;
    @BindView(R.id.provinceList) Spinner provinceList;
    @BindView(R.id.cityList) Spinner cityList;
    @BindView(R.id.txtZipCode) EditText txtZipCode;

    private Unbinder unbinder;
    private ArrayList<String> cList;
    private ArrayList<String> sList;
    private ArrayList<String> ciList;

    private AddressDTO saveAddress = null;
    private boolean selectingSpinner = false;
    public NewAddressView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_address_view, container, false);
        unbinder = ButterKnife.bind(this,view);

        countryList.setOnItemSelectedListener(new countrySelected());
        provinceList.setOnItemSelectedListener(new provinceSelected());

        setupCountryList();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();
        if(bundle!=null){
            saveAddress = (AddressDTO) bundle.getSerializable("address");

            txtFName.setText(saveAddress.getfName());
            txtLName.setText(saveAddress.getlName());
            txtContactNo.setText(saveAddress.getContactNo());
            txtAddress.setText(saveAddress.getAddress());

            selectingSpinner = true;
            int x=0;
            for(;x<cList.size();x++){
                if(cList.get(x).equalsIgnoreCase(saveAddress.getCountry())){
                    countryList.setSelection(x);
                    break;
                }
            }


            txtZipCode.setText(saveAddress.getZipCode());

        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("countryList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void setupCountryList() {
        String json = loadJSONFromAsset();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray countries = obj.getJSONArray("countryList");
            cList = new ArrayList<>();
            cList.add("");
            for(int x=0;x<countries.length();x++){
                JSONObject country = countries.getJSONObject(x);
                cList.add(country.getString("name"));
            }

            ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cList);
            countryAdapter.setDropDownViewResource(R.layout.spinner_item_view);
            countryList.setAdapter(countryAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private class countrySelected implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedCountry = cList.get(i);

            String json = loadJSONFromAsset();
            try {
                JSONObject obj = new JSONObject(json);
                JSONArray countries = obj.getJSONArray("countryList");
                sList = new ArrayList<>();
                sList.add("");
                for(int x=0;x<countries.length();x++){
                    JSONObject country = countries.getJSONObject(x);
                    if(selectedCountry.equalsIgnoreCase(country.getString("name"))){
                        JSONArray states = country.getJSONArray("states");
                        for(int y=0;y<states.length();y++){
                            sList.add(states.getJSONObject(y).getString("name"));
                        }
                        break;
                    }
                }

                ArrayAdapter<String> statesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sList);
                statesAdapter.setDropDownViewResource(R.layout.spinner_item_view);
                provinceList.setAdapter(statesAdapter);

                if(selectingSpinner){
                    int x=0;
                    for(;x<sList.size();x++){
                        if(sList.get(x).equalsIgnoreCase(saveAddress.getProvince())){
                            provinceList.setSelection(x);
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class provinceSelected implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedState = sList.get(i);
            String selectedCountry = cList.get(Integer.parseInt(String.valueOf(countryList.getSelectedItemId())));

            String json = loadJSONFromAsset();
            try {
                JSONObject obj = new JSONObject(json);
                JSONArray countries = obj.getJSONArray("countryList");
                ciList = new ArrayList<>();
                ciList.add("");
                for(int x=0;x<countries.length();x++){
                    JSONObject country = countries.getJSONObject(x);
                    if(selectedCountry.equalsIgnoreCase(country.getString("name"))){
                        JSONArray states = country.getJSONArray("states");
                        for(int y=0;y<states.length();y++){
                            JSONObject state = states.getJSONObject(y);
                            if(selectedState.equalsIgnoreCase(state.getString("name"))){
                                JSONArray cities = state.getJSONArray("cities");
                                for(int z=0;z<cities.length();z++){
                                    ciList.add(cities.getJSONObject(z).getString("name"));
                                }
                                break;
                            }
                        }
                        break;
                    }
                }

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ciList);
                cityAdapter.setDropDownViewResource(R.layout.spinner_item_view);
                cityList.setAdapter(cityAdapter);

                if(selectingSpinner){
                    int x=0;
                    for(;x<ciList.size();x++){
                        if(ciList.get(x).equalsIgnoreCase(saveAddress.getCity())){
                            cityList.setSelection(x);
                            break;
                        }
                    }
                    selectingSpinner = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    @OnClick(R.id.btnSave)
    public void btnSave_onClick(){
        String fName = txtFName.getText().toString();
        String lName = txtLName.getText().toString();
        String contactNo = txtContactNo.getText().toString();
        String address = txtAddress.getText().toString();

        String country = countryList.getSelectedItem().toString();
        String province = provinceList.getSelectedItem().toString();
        String city = cityList.getSelectedItem().toString();
        String zipCode = txtZipCode.getText().toString();

        if(fName.isEmpty() || lName.isEmpty() || contactNo.isEmpty() || country.isEmpty()|| city.isEmpty() || address.isEmpty()
                || zipCode.isEmpty() || province.isEmpty()){
            Toast.makeText(getContext(),"Please fill all the details!",Toast.LENGTH_SHORT).show();
        }else{
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setAddress(address);
            addressDTO.setAddType(AddressType.Shipping.toString());
            addressDTO.setCity(city);
            addressDTO.setContactNo(contactNo);
            addressDTO.setCountry(country);
            addressDTO.setfName(fName);
            addressDTO.setlName(lName);
            addressDTO.setProvince(province);
            addressDTO.setZipCode(zipCode);

            UserDTO user = new UserDTO();
            user.setEmail(SharedPreferenceUtility.getInstance(getContext()).getUserEmail());
            addressDTO.setUser(user);
            if(saveAddress==null){
                Call<AddressDTO> callback = APIBuilder.createAuthBuilder(getContext()).newAddress(addressDTO);
                callback.enqueue(new Callback<AddressDTO>() {
                    @Override
                    public void onResponse(Call<AddressDTO> call, Response<AddressDTO> response) {
                        if(response.code()==401){
                            APIBuilder.Logout(getContext(),getActivity());
                        }else{
                            Snackbar.make(getView(), "Address saved successfully!", Snackbar.LENGTH_LONG).show();
                            getFragmentManager().popBackStack();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddressDTO> call, Throwable t) {
                        Snackbar snackBar = Snackbar.make(getView(), "Address saved un-successful!", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                        snackBar.show();
                    }
                });
            }else{
                addressDTO.setId(saveAddress.getId());
                Call<AddressDTO> callback = APIBuilder.createAuthBuilder(getContext()).updateAddress(addressDTO);
                callback.enqueue(new Callback<AddressDTO>() {
                    @Override
                    public void onResponse(Call<AddressDTO> call, Response<AddressDTO> response) {
                        if(response.code()==401){
                            APIBuilder.Logout(getContext(),getActivity());
                        }else{
                            Snackbar.make(getView(), "Address updated successfully!", Snackbar.LENGTH_LONG).show();
                            getFragmentManager().popBackStack();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddressDTO> call, Throwable t) {
                        Snackbar snackBar = Snackbar.make(getView(), "Address updated un-successful!", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                        snackBar.show();
                    }
                });
            }
        }
    }
}
