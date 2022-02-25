package com.comcept.rmrscustomer.data_class.verifyInvoice

import com.comcept.rmrscustomer.data_class.restaurantdetail.Variant
import com.google.gson.annotations.SerializedName

data class MenuOrdered(@SerializedName("Deal"        ) var Deal        : Boolean? = null,
                       @SerializedName("Description" ) var Description : String?  = null,
                       @SerializedName("Instruction" ) var Instruction : String?  = null,
                       @SerializedName("MenuID"      ) var MenuID      : String?  = null,
                       @SerializedName("MenuName"    ) var MenuName    : String?  = null,
                       @SerializedName("MenuPrice"   ) var MenuPrice   : String?  = null,
                       @SerializedName("Quantity"    ) var Quantity    : Int?     = null,
                       @SerializedName("Variant"     ) var Variant     : Variant? = null,
                       @SerializedName("isReady"     ) var isReady     : Boolean? = null,
                       @SerializedName("isVariant"   ) var isVariant   : Boolean? = null)
