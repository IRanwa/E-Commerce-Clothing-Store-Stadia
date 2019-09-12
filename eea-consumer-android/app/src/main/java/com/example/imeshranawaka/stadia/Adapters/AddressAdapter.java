package com.example.imeshranawaka.stadia.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Fragments.NewAddressView;
import com.example.imeshranawaka.stadia.Models.AddressDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private static List<AddressDTO> mDataSet;
    private List<btnDelete_onClick> deleteBtns;
    private Context mContext;
    //private static ArrayList<View> viewsList;
    private FragmentManager fm;
    private FragmentActivity activity;

    public AddressAdapter(Context context, List<AddressDTO> addressList, FragmentManager fm, FragmentActivity activity) {
        mDataSet = addressList;
        mContext = context;
        //viewsList = new ArrayList<>();
        deleteBtns = new ArrayList<>();
        this.fm = fm;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public @BindView(R.id.txtName) TextView txtName;
        public @BindView(R.id.txtAddress) TextView txtAddress;
        public @BindView(R.id.txtContactNo) TextView txtContactNo;
        public @BindView(R.id.btnDelete) ImageView btnDelete;
        public @BindView(R.id.btnUpdate) ImageView btnUpdate;
        //public View currentView;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
//            currentView = v;
//            viewsList.add(v);
        }
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.addressbook_container,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder viewHolder, int i) {
        AddressDTO address = mDataSet.get(i);
        viewHolder.txtName.setText(address.getfName().concat(" ").concat(address.getlName()));
        viewHolder.txtAddress.setText(address.getAddress().concat(", ").concat(address.getCity())
                .concat(", ").concat(address.getProvince()).concat(", ").concat(address.getZipCode()).concat(", ").concat(address.getCountry()));
        viewHolder.txtContactNo.setText(address.getContactNo());
//        if(address.isDef()){
//            viewHolder.defBtnRadio.setChecked(true);
//        }

        btnDelete_onClick deletBtn = new btnDelete_onClick(address, i);
        deleteBtns.add(deletBtn);
        viewHolder.btnDelete.setOnClickListener(deletBtn);
        viewHolder.btnUpdate.setOnClickListener(new btnUpdate_onClick(address));

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    private class btnDelete_onClick implements View.OnClickListener {
        private AddressDTO address;
        private int position;
        public btnDelete_onClick(AddressDTO address, int position) {
            this.address = address;
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(mContext);
            dialog.setTitle("Confirmation Message");
            dialog.setContentView(R.layout.delete_message_container);

            WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
            lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lWindowParams);
            dialog.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<Boolean> callback = APIBuilder.createAuthBuilder(mContext).deleteAddress(address.getId());
                    callback.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.code()==401){
                                APIBuilder.Logout(mContext,activity);
                            }else{
                                if(response.body()){
                                    mDataSet.remove(position);
                                    notifyItemRemoved(position);
                                    deleteBtns.remove(position);

                                    for(int count=position;count<deleteBtns.size();count++){
                                        btnDelete_onClick btnOnClick = deleteBtns.get(count);
                                        btnOnClick.setPosition(count);
                                    }
                                }else{
                                    Snackbar snackBar = Snackbar.make(v, "Address delete un-successful!", Snackbar.LENGTH_LONG);
                                    snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                                    snackBar.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Snackbar snackBar = Snackbar.make(v, "Address delete un-successful!", Snackbar.LENGTH_LONG);
                            snackBar.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                            snackBar.show();
                        }
                    });
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private class btnUpdate_onClick implements View.OnClickListener {
        private AddressDTO address;
        public btnUpdate_onClick(AddressDTO address) {
            this.address = address;
        }

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = fm.beginTransaction();
            NewAddressView newAddress = new NewAddressView();
            Bundle bundle = new Bundle();
            bundle.putSerializable("address",address);
            newAddress.setArguments(bundle);
            transaction.replace(R.id.subFragment,newAddress,"NewAddressView");
            transaction.addToBackStack("MyAddressBook");
            transaction.commit();

        }
    }
}
