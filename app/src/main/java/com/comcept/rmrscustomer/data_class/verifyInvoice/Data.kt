package com.comcept.rmrscustomer.data_class.verifyInvoice

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("MenuOrdered") var MenuOrdered: ArrayList<MenuOrdered> = arrayListOf(),
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("ServiceChargesRate") var ServiceChargesRate: Int? = null,
    @SerializedName("ServiceCharges") var ServiceCharges: Int? = null,
    @SerializedName("InvoiceID") var InvoiceID: String? = null,
    @SerializedName("OrderID") var OrderID: String? = null,
    @SerializedName("RestaurantID") var RestaurantID: String? = null,
    @SerializedName("RestaurantName") var RestaurantName: String? = null,
    @SerializedName("CustomerID") var CustomerID: String? = null,
    @SerializedName("SubTotal") var SubTotal: Int? = null,
    @SerializedName("TotalAmount") var TotalAmount: Int? = null,
    @SerializedName("SalesTaxRate") var SalesTaxRate: Int? = null,
    @SerializedName("SalesTax") var SalesTax: Int? = null,
    @SerializedName("PaymentMethod") var PaymentMethod: String? = null,
    @SerializedName("PaymentConfirmed") var PaymentConfirmed: Boolean? = null,
    @SerializedName("InvoiceCreated") var InvoiceCreated: String? = null,
    @SerializedName("OrderType") var OrderType: String? = null,
    @SerializedName("CreditCardNo") var CreditCardNo: String? = null,
    @SerializedName("ReferenceNo") var ReferenceNo: String? = null,
    @SerializedName("Discount") var Discount: Discount? = Discount(),
    @SerializedName("UserPoints") var UserPoints: Int? = null,
    @SerializedName("NTNNumber") var NTNNumber: String? = null,
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("IsIntegrated") var IsIntegrated: Boolean? = null
) {
}