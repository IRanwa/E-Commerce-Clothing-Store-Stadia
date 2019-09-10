package com.example.imeshranawaka.stadia.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.Models.AddressDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private static List<AddressDTO> mDataSet;
    private List<btnDelete_onClick> deleteBtns;
    private Context mContext;
    //private static ArrayList<View> viewsList;
    private FragmentManager fm;

    public AddressAdapter(Context context, List<AddressDTO> addressList, FragmentManager fm) {
        mDataSet = addressList;
        mContext = context;
        //viewsList = new ArrayList<>();
        deleteBtns = new ArrayList<>();
        this.fm = fm;
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
                    
                    //Address.deleteAll(Address.class,"id=?",address.getId().toString());
                    //viewsList.remove(position);
                    mDataSet.remove(position);
                    notifyItemRemoved(position);
                    deleteBtns.remove(position);

                    for(int count=position;count<deleteBtns.size();count++){
                        btnDelete_onClick btnOnClick = deleteBtns.get(count);
                        btnOnClick.setPosition(count);
                    }

//                    SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(mContext);
//                    List<Address> addressList = Address.find(Address.class, "user_Email=?"
//                            , sharedPref.getUserEmail());
//                    if (addressList.size()>0) {
//                        boolean status = false;
//                        for(Address tempAddress : addressList){
//                            if(tempAddress.isDef()){
//                                status = true;
//                                break;
//                            }
//                        }
//                        if(!status){
//                            address = addressList.get(0);
//                            address.setDef(true);
//                            address.save();
//                        }
//                        for(View view : viewsList){
//                            RadioButton tempBtn = view.findViewById(R.id.defBtnRadio);
//                            tempBtn.setChecked(true);
//                            break;
//                        }
//                    }
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
//            NewAddress newAddress = new NewAddress();
//            Bundle bundle = new Bundle();
//            bundle.putLong("Address",address.getId());
//            newAddress.setArguments(bundle);
//            transaction.replace(R.id.mainFragment,newAddress,"NewAddress");
//            transaction.addToBackStack("MyAddressBook");
//            transaction.commit();

        }
    }
}
