package org.kinecosystem.kinit.view.spend

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kinecosystem.kinit.KinitApplication
import org.kinecosystem.kinit.R
import org.kinecosystem.kinit.databinding.OffersLayoutBinding
import org.kinecosystem.kinit.navigation.Navigator
import org.kinecosystem.kinit.repository.OffersRepository
import org.kinecosystem.kinit.view.BaseFragment
import org.kinecosystem.kinit.view.adapter.OfferListAdapter
import org.kinecosystem.kinit.viewmodel.spend.SpendViewModel
import javax.inject.Inject

class OffersFragment : BaseFragment() {
    @Inject
    lateinit var offersRepository: OffersRepository
    private var model: SpendViewModel? = null
    private val alertDialog: AlertDialog? = null
    lateinit var binding: OffersLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KinitApplication.coreComponent.inject(this)
        context?.let {
            model = SpendViewModel(Navigator(it))
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.offers_layout, container, false)
        binding.model = model
        binding.offersList.layoutManager = LinearLayoutManager(context)
        binding.offersList.adapter = OfferListAdapter(context!!, model!!)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //model.onResume();
        Log.d("####", "###onResume")
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (model != null) {
//            // model.setPurchaseOfferActions(this);
//        }
        Log.d("####", "###onAttach")
    }

    override fun onDetach() {
        super.onDetach()
//        if (model != null) {
//            // model.setPurchaseOfferActions(null);
//        }
        Log.d("####", "###onDetach")

        alertDialog?.dismiss()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser) {
            model?.onScreenVisibleToUser()
        }
    }

    companion object {
        val TAG = OffersFragment::class.java.simpleName

        fun newInstance(): OffersFragment {
            return OffersFragment()
        }
    }


}
