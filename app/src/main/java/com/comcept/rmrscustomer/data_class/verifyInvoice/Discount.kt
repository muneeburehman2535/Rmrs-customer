package com.comcept.rmrscustomer.data_class.verifyInvoice

import com.google.gson.annotations.SerializedName

data class Discount(

    @SerializedName("AmountAfterDiscount") var AmountAfterDiscount: Double? = null,
    @SerializedName("AmountBeforeDiscount") var AmountBeforeDiscount: Float? = null,
    @SerializedName("DiscountName") var DiscountName: String? = null,
    @SerializedName("DiscountPercentage") var DiscountPercentage: Float? = null,
    @SerializedName("OtherDiscount") var OtherDiscount: Float? = null
)
