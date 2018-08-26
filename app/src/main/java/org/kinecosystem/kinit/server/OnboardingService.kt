package org.kinecosystem.kinit.server

import android.content.Context
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import org.kinecosystem.kinit.BuildConfig
import org.kinecosystem.kinit.analytics.Analytics
import org.kinecosystem.kinit.analytics.Events
import org.kinecosystem.kinit.analytics.Events.Business.UserRegistered
import org.kinecosystem.kinit.blockchain.Wallet
import org.kinecosystem.kinit.repository.UserRepository
import org.kinecosystem.kinit.server.api.OnboardingApi
import org.kinecosystem.kinit.server.api.OnboardingApi.StatusResponse
import org.kinecosystem.kinit.server.api.PhoneAuthenticationApi
import org.kinecosystem.kinit.util.DeviceUtils
import org.kinecosystem.kinit.util.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnboardingService(context: Context, private val appLaunchApi: OnboardingApi,
                        private val phoneAuthenticationApi: PhoneAuthenticationApi,
                        val userRepo: UserRepository,
                        val analytics: Analytics,
                        val taskService: TaskService,
                        val wallet: Wallet) {

    private val applicationContext: Context = context.applicationContext
    private var blackList = listOf<String>()
    var isInBlackList: Boolean = true

    fun appLaunch() {
        if (!NetworkUtils.isConnected(applicationContext))
            return
        if (userRepo.isRegistered) {
            callAppLaunch(BuildConfig.VERSION_NAME)
        } else {
            processRegistration()
        }
    }

    fun registerOnDemand() {
        callRegister(BuildConfig.VERSION_NAME)
    }

    fun sendAuthentication(token: String, callback: OperationCompletionCallback) {

        if (!NetworkUtils.isConnected(applicationContext)) {
            callback.onError(ERROR_NO_INTERNET)
            return
        }

        phoneAuthenticationApi.updatePhoneAuthToken(userRepo.userId(),
                PhoneAuthenticationApi.AuthInfo(token)).enqueue(
                object : Callback<StatusResponse> {
                    override fun onResponse(call: Call<StatusResponse>,
                                            response: Response<StatusResponse>) {
                        if (response.isSuccessful) {
                            taskService.retrieveNextTask()
                            callback.onSuccess()
                        } else {
                            callback.onError(ERROR_APP_SERVER_FAILED_RESPONSE)
                        }
                    }

                    override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                        callback.onError(ERROR_APP_SERVER_FAILED_RESPONSE)
                    }
                })
    }

    private fun updateToken() {
        if (!userRepo.fcmTokenSent) {
            FirebaseInstanceId.getInstance().token?.let {
                updateToken(it)
            }
        }
    }

    fun updateToken(token: String) {
        val call = appLaunchApi.updateToken(userRepo.userId(), OnboardingApi.TokenInfo(token))
        call.enqueue(
                object : Callback<StatusResponse> {
                    override fun onResponse(call: Call<StatusResponse>?,
                                            response: Response<StatusResponse>?) {
                        Log.d("OnboardingService", "### updateToken success : $response token:$token")
                        // successfully sent the token update to server - so it is not longer needed
                        if (response != null && response.isSuccessful) {
                            // managed to send so can delete now
                            userRepo.fcmTokenSent = true
                        }
                    }

                    override fun onFailure(call: Call<StatusResponse>?, t: Throwable?) {
                        Log.e("OnboardingService", "### updateToken onFailure called with throwable $t")
                    }
                })
    }

    fun isValidNumber(phoneNumber:String) : Boolean {
        for(prefix in blackList){
            if(phoneNumber.startsWith(prefix)){
                return false
            }
        }
        return true
    }

    fun sendAuthTokenAck(token: String) {
        val call = appLaunchApi.authTokenAck(userRepo.userId(), OnboardingApi.TokenInfo(token))
        call.enqueue(
                object : Callback<StatusResponse> {
                    override fun onResponse(call: Call<StatusResponse>?,
                                            response: Response<StatusResponse>?) {
                        Log.d("OnboardingService", "### authTokenAck success : $response token:$token")
                        if (response == null || !response.isSuccessful) {
                            analytics.logEvent(
                                    Events.BILog.AuthTokenAckFailed("ack success but response $response"))
                        }
                    }

                    override fun onFailure(call: Call<StatusResponse>?, t: Throwable?) {
                        Log.e("OnboardingService", "### authTokenAck onFailure called with throwable $t")
                        analytics.logEvent(Events.BILog.AuthTokenAckFailed("Received onFailure with t=$t"))
                    }
                })
    }

    private fun processRegistration() {
        appLaunchApi.blacklistAreaCodes().enqueue(object : Callback<OnboardingApi.BlackListAreaCode> {
            override fun onFailure(call: Call<OnboardingApi.BlackListAreaCode>, t: Throwable) {
                blackList = listOf()
                isInBlackList = false
                callRegister(BuildConfig.VERSION_NAME)
            }

            override fun onResponse(call: Call<OnboardingApi.BlackListAreaCode>, response: Response<OnboardingApi.BlackListAreaCode>) {
                if (response.isSuccessful) {
                    response.body()?.list?.let {
                        blackList = it
                        if (!blackList.contains(DeviceUtils.getLocalDialPrefix(applicationContext))) {
                            isInBlackList = false
                            callRegister(BuildConfig.VERSION_NAME)
                        }
                    }
                }
            }
        })
    }

    private fun callRegister(appVersion: String) {
        val userId: String = userRepo.userId()
        val timeZoneWithoutGMT = DeviceUtils.timeZone()
        DeviceUtils.timeZoneDebugging(analytics)
        val displayMetrics = applicationContext.resources.displayMetrics
        val call = appLaunchApi.register(OnboardingApi.RegistrationInfo(userId,
                deviceModel = DeviceUtils.deviceName(),
                timeZone = timeZoneWithoutGMT,
                deviceId = DeviceUtils.deviceId(applicationContext),
                appVersion = appVersion,
                screenWidth = displayMetrics.widthPixels,
                screenHeight = displayMetrics.heightPixels,
                density = displayMetrics.densityDpi))
        call.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>?,
                                    response: Response<StatusResponse>?) {

                if (response != null && response.isSuccessful) {
                    updateConfig(response)
                    updateToken()
                    analytics.logEvent(UserRegistered())
                    userRepo.isRegistered = true
                    wallet.initKinWallet()
                    if (!userRepo.isPhoneVerificationEnabled) {
                        taskService.retrieveNextTask()
                    }
                } else {
                    Log.d("OnboardingService", "### register onResponse NOT SUCCESSFULL OR null: $response")
                    analytics.logEvent(Events.BILog.UserRegistrationFailed("response: $response"))
                }
            }

            override fun onFailure(call: Call<StatusResponse>?, t: Throwable?) {
                Log.d("OnboardingService", "### register onFailure called with throwable $t")
                analytics.logEvent(Events.BILog.UserRegistrationFailed("onFailure with throwable $t"))
            }
        })
    }

    private fun updateConfig(response: Response<StatusResponse>) {
        val config = response.body()?.config
        userRepo.tos = config?.tos ?: ""
        userRepo.isPhoneVerificationEnabled = config?.phone_verification_enabled ?: false
        userRepo.isP2pEnabled = config?.p2p_enabled ?: false
        userRepo.p2pMaxKin = config?.p2p_max_kin ?: 0
        userRepo.p2pMinKin = config?.p2p_min_kin ?: 0
        userRepo.p2pMinTasks = config?.p2p_min_tasks ?: 0
    }

    private fun callAppLaunch(appVersion: String) {
        updateToken()
        val call = appLaunchApi.appLaunch(userRepo.userId(), OnboardingApi.AppVersion(appVersion))
        call.enqueue(
                object : Callback<StatusResponse> {
                    override fun onResponse(call: Call<StatusResponse>?,
                                            response: Response<StatusResponse>?) {
                        if (response != null && response.isSuccessful) {
                            updateConfig(response)
                            wallet.initKinWallet()
                            Log.d("OnboardingService",
                                    "appLaunch onResponse: $response" + " config " + response?.body()?.config)
                        }
                    }

                    override fun onFailure(call: Call<StatusResponse>?, t: Throwable?) {
                        Log.d("OnboardingService", "appLaunch onFailure called with throwable $t")
                    }
                })
    }
}