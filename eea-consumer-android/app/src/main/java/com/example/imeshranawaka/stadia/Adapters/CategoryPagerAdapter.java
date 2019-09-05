package com.example.imeshranawaka.stadia.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.imeshranawaka.stadia.EnumClasses.Gender;
import com.example.imeshranawaka.stadia.Fragments.CategoryTabView;


public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    private Context myContext;
    int totalTabs;

    public CategoryPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        switch(i){
            case 0:
                bundle.putString("Type", Gender.M.toString());
                break;
            case 1:
                bundle.putString("Type", Gender.F.toString());
                break;
        }
        CategoryTabView tabView = new CategoryTabView();
        tabView.setArguments(bundle);
        return tabView;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
