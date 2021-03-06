package org.kinecosystem.kinit.server.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PhoneAuthenticationApi {

    data class AuthInfo(@SerializedName("token") val token: String, @SerializedName("validation-token") val validationToken: String?)

    @POST("/user/firebase/update-id-token")
    fun updatePhoneAuthToken(@Header(
            USER_HEADER_KEY) userId: String, @Body authInfo: AuthInfo): Call<OnboardingApi.HintsResponse>
}