package com.example.imeshranawaka.stadia.APIs;

import com.example.imeshranawaka.stadia.Models.LoginDTO;
import com.example.imeshranawaka.stadia.Models.MainCategoryDTO;
import com.example.imeshranawaka.stadia.Models.MainSubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.OrdersDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.Models.SubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.UserDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIClient {

   @POST("/authenticate/")
    Call<LoginDTO> createAuthenticationToken(@Body LoginDTO authenticationRequest);

   @POST("/validateToken")
   Call<Boolean> validateToken(@Body LoginDTO loginDTO);

   @GET("/GetMainCatByType/{type}")
    Call<List<MainCategoryDTO>> GetMainCatByType(@Path("type") String type);

    @GET("/MainSubCategory/{id}")
    Call<List<SubCategoryDTO>> getMainSubCategory(@Path("id") int id);

    @POST("/Product/{pageNo}")
    Call<List<ProductDTO>> getProductList(@Path("pageNo") int pageNo,@Body ProductDTO productDTO);

    @POST("/AddToCart")
    Call<String> addToCart(@Body OrderProductsDTO orderProducts);

    @POST("/ViewCart")
    Call<List<OrderProductsDTO>> getCart(@Body UserDTO user);

    @DELETE("/DeleteCartItem/{orderId}/{prodSizeId}")
    Call<Boolean> deleteCartItem(@Path("orderId") long orderId,@Path("prodSizeId") long prodSizeId);

    @POST("/DeleteCartItems")
    Call<Boolean> deleteCartItems(@Body OrdersDTO order);


}
