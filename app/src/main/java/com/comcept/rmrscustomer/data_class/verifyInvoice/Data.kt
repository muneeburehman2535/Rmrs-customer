package com.comcept.rmrscustomer.data_class.verifyInvoice

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("MenuOrdered") var MenuOrdered: ArrayList<MenuOrdered> = arrayListOf(),
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("ServiceChargesRate") var ServiceChargesRate: Float? = null,
    @SerializedName("ServiceCharges") var ServiceCharges: Float? = null,
    @SerializedName("InvoiceID") var InvoiceID: String? = null,
    @SerializedName("OrderID") var OrderID: String? = null,
    @SerializedName("RestaurantID") var RestaurantID: String? = null,
    @SerializedName("RestaurantName") var RestaurantName: String? = null,
    @SerializedName("CustomerID") var CustomerID: String? = null,
    @SerializedName("SubTotal") var SubTotal: Float? = null,
    @SerializedName("TotalAmount") var TotalAmount: Float? = null,
    @SerializedName("SalesTaxRate") var SalesTaxRate: Float? = null,
    @SerializedName("SalesTax") var SalesTax: Float? = null,
    @SerializedName("PaymentMethod") var PaymentMethod: String? = null,
    @SerializedName("PaymentConfirmed") var PaymentConfirmed: Boolean? = null,
    @SerializedName("InvoiceCreated") var InvoiceCreated: String? = null,
    @SerializedName("OrderType") var OrderType: String? = null,
    @SerializedName("CreditCardNo") var CreditCardNo: String? = null,
    @SerializedName("ReferenceNo") var ReferenceNo: String? = null,
    @SerializedName("Discount") var Discount: Discount? = Discount(),
    @SerializedName("UserPoints") var UserPoints: Float? = null,
    @SerializedName("DeliveryCharges") var DeliveryCharges: Float? = null,
    @SerializedName("NTNNumber") var NTNNumber: String? = null,
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("IsIntegrated") var IsIntegrated: Boolean? = null
) {
}