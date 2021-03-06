package com.example.imeshranawaka.stadia.APIs;

import com.example.imeshranawaka.stadia.Models.AddressDTO;
import com.example.imeshranawaka.stadia.Models.LoginDTO;
import com.example.imeshranawaka.stadia.Models.MainCategoryDTO;
import com.example.imeshranawaka.stadia.Models.MainSubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.OrderProductsDTO;
import com.example.imeshranawaka.stadia.Models.OrdersDTO;
import com.example.imeshranawaka.stadia.Models.ProductDTO;
import com.example.imeshranawaka.stadia.Models.SubCategoryDTO;
import com.example.imeshranawaka.stadia.Models.UserDTO;

import java.util.List;

import okhttp3.Address;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @POST("/GetUser")
    Call<UserDTO> GetUserDetails(@Body UserDTO user);

    @PUT("/UpdateUser/{id}")
    Call<UserDTO> UpdateUser(@Path("id") String id, @Body UserDTO user);

    @GET("/GetAddressList/{id}")
    Call<List<AddressDTO>> getAddressList(@Path("id") String id);

    @DELETE("/DeleteAddress/{id}")
    Call<Boolean> deleteAddress(@Path("id") long id);

    @POST("/NewAddress")
    Call<AddressDTO> newAddress(@Body AddressDTO addressDTO);

    @PUT("/UpdateAddress")
    Call<AddressDTO> updateAddress(@Body AddressDTO addressDTO);

    @POST("/UpdateCartQty")
    Call<Boolean> updateCartQty(@Body OrderProductsDTO orderProductsDTO);

    @POST("/PlaceOrder")
    Call<Boolean> placeOrder(@Body OrdersDTO ordersDTO);

    @POST("/ListOrders")
    Call<List<OrdersDTO>> getOrdersList(@Body UserDTO userDTO);

    @GET("/GetOrder/{id}")
    Call<OrdersDTO> getOrder(@Path(("id")) long id);

    @PUT("/UpdateOrderStatus")
    Call<Boolean> updateOrderStatus(@Body OrdersDTO ordersDTO);

}
