package com.sterlite.dccmappfordealersterlite.data.network;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import com.sterlite.dccmappfordealersterlite.model.dto.BssResponse.BSSDataResponse;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.CartModificationWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.OrderEntryWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.address.AddressWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.auth.AuthResponseDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.changeStatus.ChangeStatusRequestData;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.OrderHistoryListWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.OrderWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.inventory.InventoryDetailWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.inventory.NumberSelectionInvWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.pos.VMSPointOfServiceDataWSDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.recharge.RechargeDetailResponseData;
import com.sterlite.dccmappfordealersterlite.model.dto.recharge.RechargeRequestData;
import com.sterlite.dccmappfordealersterlite.model.dto.searchproducts.ProductSearchPageWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.transferbalance.TransferBalanceRequestData;
import com.sterlite.dccmappfordealersterlite.model.dto.transferbalance.TransferBalanceResponseData;
import com.sterlite.dccmappfordealersterlite.model.dto.user.UserResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsResponseDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.validatecustomer.ValidateCustomerWsDTO;

public interface DCCMWebServices {

    @Headers({"Content-Type: application/json"})
    @POST("/customer/registerCustomer")
    void doSignUp(@Body UserSignUpWsDTO request,
                  Callback<UserSignUpWsResponseDTO> cb);

    @Headers({"Content-Type: application/json"})
    @GET("/products/searchCategoryProduct")
    void getPlans(@Query("categoryCode") String categoryCode, Callback<ProductSearchPageWsDTO> cb);

    @Headers({"Content-Type: application/json"})
    @POST("/customer/validateCustomer")
    void doLogin(@Body ValidateCustomerWsDTO categoryCode, Callback<UserResponseWsDTO> cb);

    @Headers({"Content-Type: application/json"})
    @GET("/inventorydetail/getinventory")
    void getInventory(Callback<InventoryDetailWsDTO> cb);

    @Headers({"Content-Type: application/json"})
    @POST("/users/{customerUID}/carts/00000175/entries")
    void getCarts(@Path("customerUID") String customerUID, @Query("access_token") String access_token , @Body OrderEntryWsDTO entry, Callback<CartModificationWsDTO> cb);

//    https://b2ctelco.local:9002/telcocommercewebservices/v2/b2ctelco/leads/les12091800018/carts/addProduct
//    https://10.121.25.106:9002/telcocommercewebservices/v2/b2ctelco/leads/les20091800001/carts/addProduct
//    https://10.121.25.106:9002/telcocommercewebservices/v2/b2ctelco/leads//carts/addProduct
    @Headers({"Content-Type: application/json"})
    @POST("/leads/{customerUID}/carts/addProduct")
    void addToCart(@Path("customerUID") String customerUID, @Body AddToCartWsDTO cartWsDTO, Callback<AddToCartResponseWsDTO> cb);

//   /users/les07091800058/carts/00000175/entries?access_token=7ed8c3d9-5642-49dc-a27f-87a9a552db93

    @Headers({"Content-Type: application/json"})
    @POST("/users/{customerUID}/carts/{cartID}/entries")
    void addToCartWithCartID(@Path("customerUID") String customerUID, @Path("cartID") String cartID ,@Query("access_token")String access_token ,@Query("code") String code,@Body OrderEntryWsDTO orderEntryWsDTO ,Callback<CartModificationWsDTO> cb);


    @Headers({"Content-Type: application/json"})
    @POST("/inventorydetail/{customerUID}/carts/{cartid}/addinventorytocart")
    void addInventoryToCart(@Path("customerUID") String customerUID,@Path("cartid") String cartid,@Body NumberSelectionInvWsDTO numberSelectionInvWsDTO, Callback<BSSDataResponse> cb);

    @Headers({"Content-Type: application/json"})
    @POST("/ManageRecharge/recharge")
    void doRecharge(@Body RechargeRequestData rechargeRequestData, Callback<RechargeDetailResponseData> cb);

    @Headers({"Content-Type: application/json"})
    @POST("/ManageCustomer/transferBalance")
    void balanceTransfer(@Body TransferBalanceRequestData transferBalanceRequestData, Callback<TransferBalanceResponseData> cb);

// https://10.121.25.106:9002/telcocommercewebservices/v2/b2ctelco/deliveryMobile/setDeliveryAddressMobile/les20091800009/00000035
    @Headers({"Content-Type: application/json"})
    @POST("/deliveryMobile/setDeliveryAddressMobile/{userId}/{cartid}")
    void addDeliveryAddress(@Path("userId") String customerId,@Path("cartid") String cartid, @Body AddressWsDTO addressWsDTO, Callback<BSSDataResponse> cb);

    @Headers({"Content-Type: application/json"})
    @POST("/deliveryMobile/setDeliveryModeMobile/{userId}")
    void setDeliveryModeAddress(@Path("userId") String customerId,@Query("deliveryMode") String deliveryMode, @Body String extra, Callback<BSSDataResponse> cb);

    @Headers({"Content-Type: application/json"})
    @POST("/users/{userId}/orders")
    void placeOrder(@Path("userId") String customerId, @Query("cartId") String cartId,@Query("access_token")String access_token,@Body String temp ,Callback<OrderWsDTO> cb);


    @Headers({"Content-Type: application/json"})
    @GET("/users/{userId}/orders")
    void  getOrdersForUser(@Path("userId") String userId, @Query("access_token") String access_token, Callback<OrderHistoryListWsDTO> cb);

    @Headers({"Content-Type: application/json"})
    @POST("/oauth/token")
    void  getAuthToken(@Query("client_id") String client_id, @Query("client_secret") String client_secret, @Query("grant_type") String grant_type, @Body String b, Callback<AuthResponseDTO> cb);

    @Headers({"Content-Type: application/json"})
    @GET("/customer/validatePincode")
    void  validatePincode(@Query("pincode") String pincode, @Query("isocode") String isocode, Callback<VMSPointOfServiceDataWSDTO> cb);

    @Headers({"Content-Type: application/json"})
    @GET("/users/{userId}/orders/{orderId}")
    void  getDnNumber(@Path("userId") String userId, @Path("orderId") String orderId, @Query("access_token") String access_token, Callback<OrderWsDTO> cb);


    @Headers({"Content-Type: application/json"})
    @POST("/ManageCustomer/changeServiceInstnceStatus")
    void  changeServiceInstanceStaus(@Body ChangeStatusRequestData changeStatusRequestData, Callback<BSSDataResponse> cb);

    @Headers({"Content-Type: application/json"})
    @DELETE("/users/{UserId}/carts/{CartId}")
    void deleteCart(@Path("UserId") String UserId, @Path("CartId") String CartId, @Query("access_token") String access_token, Callback<String> response);

    @Headers({"Content-Type: application/json"})
    @GET("/ManageBillingAccount/downloadBillForApp")
    void downloadBill(@Query("strBillNumber") String billNumber ,ApiHelper.OnApiCallback<byte[]> response);


    /*

Every method of an interface represents one possible API call. It must have a HTTP annotation (GET, POST, etc.) to specify the request type and the relative URL. The return value wraps the response in a Call object with the type of the expected result.

@GET("users")
Call<List<User>> getUsers()


You can use replacement blocks and query parameters to adjust the URL. A replacement block is added to the relative URL with {}. With the help of the @Path annotation on the method parameter, the value of that parameter is bound to the specific replacement block.

@GET("users/{name}/commits")
Call<List<Commit>> getCommitsByName(@Path("name") String name)


Query parameters are added with the @Query annotation on a method parameter. They are automatically added at the end of the URL.

@GET("users")
Call<User> getUserById(@Query("id") Integer id)


The @Body annotation on a method parameter tells Retrofit to use the object as the request body for the call.

@POST("users")
Call<User> postUser(@Body User user)

*/
}