package com.example.imeshranawaka.stadia.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.MainCategoryAdapter;
import com.example.imeshranawaka.stadia.Adapters.SubCategoryAdapter;
import com.example.imeshranawaka.stadia.Models.MainCategoryDTO;
import com.example.imeshranawaka.stadia.Models.MainSubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.SubCategoryDTO;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenu extends Fragment {
    private Unbinder unbinder;
    public MainMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        unbinder = ButterKnife.bind(this, view);

        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.subFragment, new ProductsView(), "ProductsView").
                commit();
        return view;
    }

    @OnClick(R.id.homeNavBtn)
    public void homeNavBtnClick(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        int backStackEntry = fm.getBackStackEntryCount();
        List<Fragment> fragments = fm.getFragments();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                fm.popBackStackImmediate();
                if(fragments.size()<i) {
                    Fragment frag = fragments.get(0);
                    transaction.remove(frag);
                    if(frag.getTag()!=null && !frag.getTag().equals("ProductView")) {
                        break;
                    }
                }
                fragments = fm.getFragments();
            }
        }
        transaction.replace(R.id.subFragment, new CategoryView(), "ProductView");
        transaction.commit();
    }

    @OnClick(R.id.categoryNavBtn)
    public void categoryNavBtnClick(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        int backStackEntry = fm.getBackStackEntryCount();
        List<Fragment> fragments = fm.getFragments();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                fm.popBackStackImmediate();
                if(fragments.size()<i) {
                    Fragment frag = fragments.get(0);
                    transaction.remove(frag);
                    if(frag.getTag()!=null && !frag.getTag().equals("CategoryView")) {
                        break;
                    }
                }
                fragments = fm.getFragments();
            }
        }
        transaction.replace(R.id.subFragment, new CategoryView(), "CategoryView").addToBackStack("ProductView");
        transaction.commit();
    }




}
