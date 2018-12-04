package org.kinecosystem.kinit.viewmodel.spend

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import org.kinecosystem.kinit.KinitApplication
import org.kinecosystem.kinit.analytics.Analytics
import org.kinecosystem.kinit.analytics.Events
import org.kinecosystem.kinit.model.spend.Offer
import org.kinecosystem.kinit.navigation.Navigator
import org.kinecosystem.kinit.repository.OffersRepository
import org.kinecosystem.kinit.repository.UserRepository
import org.kinecosystem.kinit.server.ERROR_APP_SERVER_FAILED_RESPONSE
import org.kinecosystem.kinit.server.ERROR_EMPTY_RESPONSE
import org.kinecosystem.kinit.server.NetworkServices
import org.kinecosystem.kinit.server.OperationCompletionCallback
import org.kinecosystem.kinit.view.TabViewModel
import javax.inject.Inject

class EcoAppsTransferViewModel(private val navigator: Navigator) :
        TabViewModel {

    @Inject
    lateinit var offersRepository: OffersRepository
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var networkServices: NetworkServices

    var balance: ObservableField<String>

    val hasErrors = ObservableBoolean(false)
    val hasNetwork = ObservableBoolean(true)
    val showData = ObservableBoolean(true)
    val isLoading = ObservableBoolean(false)

    init {
        KinitApplication.coreComponent.inject(this)
        balance = networkServices.walletService.balance
        hasNetwork.set(networkServices.isNetworkConnected())
        bindData()
    }

    fun offers(): List<Offer> {
        return offersRepository.offers.get()
    }

    fun onDataLoaded() {
        isLoading.set(false)
    }

    private fun bindData() {
        if (networkServices.isNetworkConnected()) {
            hasNetwork.set(true)
            showData.set(true)
            balance.set(networkServices.walletService.balance.get().toString())
        } else {
            hasNetwork.set(false)
            showData.set(false)
        }
    }

    private fun checkForUpdates() {
        if (hasNetwork.get()) {
            networkServices.offerService.retrieveOffers(object : OperationCompletionCallback {
                override fun onError(errorCode: Int) {
                    isLoading.set(false)
                    showData.set(false)
                    hasErrors.set(true)
                    val reason = when (errorCode) {
                        ERROR_APP_SERVER_FAILED_RESPONSE -> Analytics.SERVER_ERROR_RESPONSE
                        ERROR_EMPTY_RESPONSE -> Analytics.SERVER_EMPTY_RESPONSE
                        else -> Analytics.SERVER_ERROR_RESPONSE
                    }
                    Events.Analytics.ViewErrorPage(Analytics.VIEW_ERROR_TYPE_GENERIC, reason)
                }

                override fun onSuccess() {
                    isLoading.set(false)
                    hasErrors.set(false)
                    showData.set(true)
                    bindData()
                }
            })
        }
    }

    override fun onScreenVisibleToUser() {
        if (offersRepository.isEmpty()) {
            isLoading.set(true)
        }
        bindData()
        checkForUpdates()

        val event: Events.Event =
                if (!hasNetwork.get()) {
                    Events.Analytics.ViewErrorPage(Analytics.VIEW_ERROR_TYPE_INTERNET_CONNECTION, "no internet")
                } else if (offersRepository.offers.get().isEmpty()) {
                    Events.Analytics.ViewEmptyStatePage(Analytics.MENU_ITEM_NAME_EARN, "")
                } else {
                    Events.Analytics.ViewSpendPage(offersRepository.offersCount())
                }
        analytics.logEvent(event)
    }

    fun onItemClicked(offer: Offer, position: Int) {
        navigator.navigateTo(offer)
        analytics.logEvent(Events.Analytics.ClickOfferItemOnSpendPage(offer.provider?.name,
                offer.price,
                offersRepository.offersCount(),
                offer.domain,
                offer.id,
                offer.title,
                position,
                offer.type))
    }

}

