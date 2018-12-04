package org.kinecosystem.kinit.viewmodel.spend

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import org.kinecosystem.kinit.KinitApplication
import org.kinecosystem.kinit.analytics.Analytics
import org.kinecosystem.kinit.model.spend.EcoApplication
import org.kinecosystem.kinit.model.spend.getHeaderImageCount
import org.kinecosystem.kinit.model.spend.getHeaderImageUrl
import org.kinecosystem.kinit.model.spend.isKinTransferSupported
import org.kinecosystem.kinit.navigation.Navigator
import org.kinecosystem.kinit.repository.EcoApplicationsRepository
import org.kinecosystem.kinit.server.NetworkServices
import org.kinecosystem.kinit.util.Scheduler
import javax.inject.Inject

class AppViewModel(private val navigator: Navigator, private val app: EcoApplication) {

    @Inject
    lateinit var scheduler: Scheduler
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var ecoApplicationsRepository: EcoApplicationsRepository
    @Inject
    lateinit var networkServices: NetworkServices


    val appIconUrl: String = app.data.iconUrl
    val appName: String = app.name
    val shortDescription = app.data.descriptionShort
    val categoryTitle = app.data.categoryTitle
    val aboutApp = app.data.description
    val kinUsage = app.data.kinUsage
    val canTransferKin = app.isKinTransferSupported()

    //val isP2p: Boolean
    val couponCode = ObservableField("")
    val couponPurchaseCompleted = ObservableBoolean(false)
    val canBuy: ObservableBoolean = ObservableBoolean(false)
    val cantBuyWarning = ObservableBoolean(false)
    val cantBuyWarningText = ObservableField<String>("")

    init {
        KinitApplication.coreComponent.inject(this)
    }

    fun getHeaderImageCount() = app.getHeaderImageCount()
    fun getHeaderImageUrl(position: Int) = app.getHeaderImageUrl(position)

    fun onCloseButtonClicked(view: View?) {
        navigator.navigateTo(Navigator.Destination.MAIN_SCREEN)
    }

    fun onActionBtnClicked(view: View?) {
        if (canTransferKin) {
            //TODO
        } else {
            navigator.navigateToUrl(app.data.appUrl)
        }
    }


}