package org.kinecosystem.kinit.server

import android.util.Log
import org.kinecosystem.kinit.repository.UserRepository
import org.kinecosystem.kinit.server.api.ClientValidationApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val GET_NONCE_FAILED = 0
const val SERVER_ERROR = 2

class ClientValidationService(private val userRepo: UserRepository, val server: ClientValidationApi){

    fun getNonce(callback: OperationResultCallback<String>?) {
        server.getNonce(userRepo.userId()).enqueue(object : Callback<ClientValidationApi.ValidationNonceResponse> {
            override fun onFailure(call: Call<ClientValidationApi.ValidationNonceResponse>, t: Throwable) {
                Log.d("ClientValidationService", "server.getNonce failed with error: " + t.message)
                callback?.onError(SERVER_ERROR)
            }

            override fun onResponse(call: Call<ClientValidationApi.ValidationNonceResponse>, response: Response<ClientValidationApi.ValidationNonceResponse>) {
                if (response.isSuccessful && !response.body()?.nonce.isNullOrBlank()) {
                    response.body()?.nonce?.let { callback?.onResult(it) }
                } else {
                    callback?.onError(GET_NONCE_FAILED)
                    Log.e("ClientValidationService", "getNonce return `not-successful` response")
                }
            }

        })
    }
}