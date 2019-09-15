package com.example.imeshranawaka.stadia.Fragments;


import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.Stadia;

import java.util.List;

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
        return view;
    }

    private void enableDrawer(){
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onStart() {
        super.onStart();
        enableDrawer();
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.subFragment, new ProductsView(), "ProductsView").
                commit();
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
                    if(frag.getTag()!=null && !frag.getTag().equals("ProductsView")) {
                        break;
                    }
                }
                fragments = fm.getFragments();
            }
        }
        transaction.replace(R.id.subFragment, new ProductsView(), "ProductsView");
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
                    System.err.println("frag tag : "+frag.getTag());
                    if(frag.getTag()!=null && !frag.getTag().equals("CategoryView")) {
                        break;
                    }
                }
                fragments = fm.getFragments();
            }
        }
        transaction.replace(R.id.subFragment, new CategoryView(), "CategoryView");
        transaction.commit();
    }

    @OnClick(R.id.cartNavBtn)
    public void cartNabBtnClick(){
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
                    System.err.println("frag tag : "+frag.getTag());
                    if(frag.getTag()!=null && !frag.getTag().equals("CartView")) {
                        break;
                    }
                }
                fragments = fm.getFragments();
            }
        }
        transaction.replace(R.id.subFragment, new CartView(), "CartView");
        transaction.commit();
    }

    @OnClick(R.id.profileNavBtn)
    public void profileNavBtnClick(){
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
                    System.err.println("frag tag : "+frag.getTag());
                    if(frag.getTag()!=null && !frag.getTag().equals("ProfileView")) {
                        break;
                    }
                }
                fragments = fm.getFragments();
            }
        }
        transaction.replace(R.id.subFragment, new ProfileView(), "ProfileView");
        transaction.commit();
    }




}
