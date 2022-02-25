package com.comcept.rmrscustomer.data_class.verifyInvoice

import com.google.gson.annotations.SerializedName

data class Discount(

    @SerializedName("AmountAfterDiscount") var AmountAfterDiscount: Double? = null,
    @SerializedName("AmountBeforeDiscount") var AmountBeforeDiscount: Int? = null,
    @SerializedName("DiscountName") var DiscountName: String? = null,
    @SerializedName("DiscountPercentage") var DiscountPercentage: Int? = null,
    @SerializedName("OtherDiscount") var OtherDiscount: Int? = null
)
