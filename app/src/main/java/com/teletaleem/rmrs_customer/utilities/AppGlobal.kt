package com.teletaleem.rmrs_customer.utilities

class AppGlobal {
    companion object{
        var BUILD = "STAGE"

        var HOME_BASE_URL = if ("PRODUCTION" == BUILD) "live-url" else "stage-url"
        var HOME_BASE_URL_IMAGE = if ("PRODUCTION" == BUILD) "live-url" else "stage-url"
        const val LOCATION_IQ_URL = "https://us1.locationiq.com/"
    }
}