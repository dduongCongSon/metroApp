package org.com.metro.repositories.apis.order

import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


data class TransactionData(
    val transactionId: Int?,
    val userId: Int,
    val paymentMethodId: Int,
    val paymentMethodName: String?,
    val transactionStatus: String,
    val amount: Double,
    val createAt: String
)

data class OrderData(
    val orderId: Int,
    val userId: Int,
    val ticketId: String?,
    val status: String,
    val amount: Double,
    val createdAt: String,
    val transaction: TransactionData?
)

data class UserOrdersResponse(
    val status: Int,
    val message: String,
    val data: List<OrderData>?
)


data class FareMatrixIdObject(
    val id: Int
)

data class CreateOrderRequest(
    val fareMatrixId: FareMatrixIdObject,
    val paymentMethodId: Int
)

data class CreatedOrderData(
    val orderId: Int,
    val ticketId: String,
    val status: String
    // Bạn có thể thêm các trường khác từ response nếu cần
)

data class CreateOrderResponse(
    val status: Int,
    val message: String,
    val data: CreatedOrderData?
)



data class TicketDetails(
    val id: Int,
    val name: String,
    val ticketCode: String,
    val validFrom: String,
    val validUntil: String,
    val status: String
)


data class OrderWithTicketDetails(
    val orderId: Int,
    val userId: Int,
    val status: String,
    val amount: Double,
    val ticket: TicketDetails? // Đối tượng ticket lồng nhau
)

// Lớp này đại diện cho toàn bộ response từ API /api/orders/user/details
data class UserOrdersDetailsResponse(
    val status: Int,
    val message: String,
    val data: List<OrderWithTicketDetails>?
)


data class SingleTicketDetails(
    val id: Int,
    val fareMatrixId: Int,
    val ticketTypeId: Int,
    val name: String,
    val ticketCode: String,
    val actualPrice: Double,
    val validFrom: String,
    val validUntil: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)

// Lớp này đại diện cho toàn bộ response từ API
data class TicketDetailsResponse(
    val status: Int,
    val message: String,

    val data: SingleTicketDetails?
)
interface OrderSingleApi {

    @POST("api/orders/create/single")
    suspend fun createSingleOrder(@Body request: CreateOrderRequest): CreateOrderResponse

    @GET("api/orders/user")
    suspend fun getUserOrders(): UserOrdersResponse

    @GET("api/orders/user/details")
    suspend fun getUserOrdersWithDetails(): UserOrdersDetailsResponse

    @GET("/api/ts/tickets/code/{ticketCode}")
    suspend fun getTicketByCode(@Path("ticketCode") ticketCode: String): TicketDetailsResponse

//    @GET("/api/ts/tickets/qr")
//    suspend fun getTicketQRCode(@Query("ticketCode") ticketCode: String): Response<ResponseBody>

}