package org.kinecosystem.kinit.view.spend

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kinecosystem.kinit.KinitApplication
import org.kinecosystem.kinit.R
import org.kinecosystem.kinit.databinding.EcoappsTransfersLayoutBinding
import org.kinecosystem.kinit.navigation.Navigator
import org.kinecosystem.kinit.repository.EcoApplicationsRepository
import org.kinecosystem.kinit.repository.OffersRepository
import org.kinecosystem.kinit.view.BaseFragment
import org.kinecosystem.kinit.view.customView.EcoApplicationCategoryView
import org.kinecosystem.kinit.viewmodel.spend.EcoAppsTransferViewModel
import javax.inject.Inject

class EcoAppsFragment : BaseFragment() {
    @Inject
    lateinit var offersRepository: OffersRepository

    @Inject
    lateinit var ecoApplicationsRepository: EcoApplicationsRepository

    private var model: EcoAppsTransferViewModel? = null
    private val alertDialog: AlertDialog? = null
    lateinit var binding: EcoappsTransfersLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KinitApplication.coreComponent.inject(this)
        context?.let {
            model = EcoAppsTransferViewModel(Navigator(it))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.ecoapps_transfers_layout, container, false)
        binding.model = model
        binding.scrollview.isFocusableInTouchMode = true
        binding.scrollview.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS

        activity?.let {
            for (category in ecoApplicationsRepository.appCategories) {
                binding.categoriesContainer.addView(EcoApplicationCategoryView(it, category.id))
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //model.onResume();
        Log.d("####", "###onResume EcoAppsFragment ")
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (model != null) {
//            // model.setPurchaseOfferActions(this);
//        }
        Log.d("####", "###onAttach EcoAppsFragment")
    }

    override fun onDetach() {
        super.onDetach()
//        if (model != null) {
//            // model.setPurchaseOfferActions(null);
//        }
        Log.d("####", "###onDetach EcoAppsFragment ")

        alertDialog?.dismiss()
    }

    companion object {
        val TAG = EcoAppsFragment::class.java.simpleName

        fun newInstance(): EcoAppsFragment {
            return EcoAppsFragment()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            model?.onScreenVisibleToUser()
        }
    }
}
