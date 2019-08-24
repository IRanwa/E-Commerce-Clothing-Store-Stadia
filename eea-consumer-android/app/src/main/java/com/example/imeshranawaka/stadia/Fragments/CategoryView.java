package com.example.imeshranawaka.stadia.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.MainCategoryAdapter;
import com.example.imeshranawaka.stadia.Adapters.SubCategoryAdapter;
import com.example.imeshranawaka.stadia.Models.MainCategoryDTO;
import com.example.imeshranawaka.stadia.Models.SubCategoryDTO;
import com.example.imeshranawaka.stadia.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryView extends Fragment {
    @BindView(R.id.mainCategoryList) RecyclerView mainCategoryListView;
    @BindView(R.id.subCategoryList) RecyclerView subCategoryListView;
    private Unbinder unbinder;
    public CategoryView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<List<MainCategoryDTO>> apiClient = APIBuilder.createAuthBuilder(getContext()).getAllMainCategory();
        apiClient.enqueue(new Callback<List<MainCategoryDTO>>() {
            @Override
            public void onResponse(Call<List<MainCategoryDTO>> call, Response<List<MainCategoryDTO>> response) {

                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    List<MainCategoryDTO> mainCategoryList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    MainCategoryAdapter adapter = new MainCategoryAdapter(getContext(),mainCategoryList,CategoryView.this);
                    mainCategoryListView.setLayoutManager(layoutManager);
                    mainCategoryListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<MainCategoryDTO>> call, Throwable t) {
                System.out.println("Main Category Error!");
                System.out.println(t.getMessage());
            }
        });
    }

    public void setSubCategory(MainCategoryDTO mainCategoryDTO) {
        Call<List<SubCategoryDTO>> apiClient = APIBuilder.createAuthBuilder(getContext()).getMainSubCategory(mainCategoryDTO.getId());
        apiClient.enqueue(new Callback<List<SubCategoryDTO>>() {
            @Override
            public void onResponse(Call<List<SubCategoryDTO>> call, Response<List<SubCategoryDTO>> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    List<SubCategoryDTO> subCategoryList = response.body();

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                    SubCategoryAdapter adapter = new SubCategoryAdapter(subCategoryList,getContext());
                    subCategoryListView.setLayoutManager(gridLayoutManager);
                    subCategoryListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SubCategoryDTO>> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });

    }


}
