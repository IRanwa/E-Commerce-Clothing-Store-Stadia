package com.example.imeshranawaka.stadia.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Adapters.ProductListAdapter;
import com.example.imeshranawaka.stadia.EnumClasses.SortBy;
import com.example.imeshranawaka.stadia.Models.MainSubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
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
public class ProductsView extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.productListView) RecyclerView productListView;
    @BindView(R.id.txtMainTitle) TextView txtMainTitle;
    @BindView(R.id.txtSortBy) TextView txtSortBy;
    public ProductsView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if(bundle!=null){
            int pageNo = bundle.getInt("pageNo");
            ProductDTO productDTO = (ProductDTO) bundle.getSerializable("productDTO");
            viewProductList(pageNo,productDTO);

            MainSubCategoryDTO mainSubCat = productDTO.getMainSubCategory();
            String title = "";
            if(mainSubCat.getMainCategory()!=null){
                title += mainSubCat.getMainCategory().getMainCatTitle();
                if(mainSubCat.getSubCategory()!=null){
                    title += " - "+mainSubCat.getSubCategory().getSubCatTitle();
                }
            }
            txtMainTitle.setText(title);
            txtSortBy.setVisibility(View.VISIBLE);
        }else{
            ProductDTO productDTO = new ProductDTO();
            productDTO.setSortBy(SortBy.Date_Newest);
            viewProductList(0,productDTO);
            txtMainTitle.setText("Latest Arrival");
            txtSortBy.setVisibility(View.GONE);
        }


    }

    private void viewProductList(int pageNo,ProductDTO productDTO){
        Call<List<ProductDTO>> apiClient = APIBuilder.createAuthBuilder(getContext()).getProductList(pageNo,productDTO);
        apiClient.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if(response.code()==401){
                    APIBuilder.Logout(getContext(),getActivity());
                }else{
                    List<ProductDTO> productList = response.body();

                    if(productList!=null) {
                        if(getActivity()!=null) {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                            ProductListAdapter adapter = new ProductListAdapter(productList, getContext(), getActivity().getSupportFragmentManager());
                            productListView.setLayoutManager(gridLayoutManager);
                            productListView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });

    }
}
