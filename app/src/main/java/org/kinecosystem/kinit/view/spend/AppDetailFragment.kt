package org.kinecosystem.kinit.view.spend

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.kinecosystem.kinit.R
import org.kinecosystem.kinit.databinding.AppDetailsLayoutBinding
import org.kinecosystem.kinit.model.spend.EcoApplication
import org.kinecosystem.kinit.model.spend.Offer
import org.kinecosystem.kinit.navigation.Navigator
import org.kinecosystem.kinit.util.BindingUtils
import org.kinecosystem.kinit.view.BaseFragment
import org.kinecosystem.kinit.view.spend.PurchaseOfferFragment.ARG_OFFER
import org.kinecosystem.kinit.viewmodel.spend.AppViewModel
import org.kinecosystem.kinit.viewmodel.spend.AppViewModel_MembersInjector
import org.kinecosystem.kinit.viewmodel.spend.PurchaseOfferViewModel

class AppDetailFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AppDetailsLayoutBinding>(inflater, R.layout.app_details_layout, container, false)
       arguments?.let { args ->
           if(args.containsKey(ARG_APP)){
               val app = args.getParcelable<EcoApplication>(ARG_APP)
               activity?.let { activity ->
                   val model = AppViewModel( Navigator(activity), app)
                   binding.model = model
                   binding.viewPager.adapter = AppPagerAdapter(activity.supportFragmentManager, model)
                   //TODO
                   //binding.viewPager.addOnPageChangeListener(model)
               }


           }
       }
        return binding.root
    }

    companion object {

        val ARG_APP = "arg_app"
        val TAG = AppDetailFragment::class.java.simpleName

        fun newInstance(app: EcoApplication): AppDetailFragment {
            val fragment = AppDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_APP, app)
            fragment.arguments = args
            return fragment
        }
    }

    private class AppPagerAdapter(fragmentManager: FragmentManager, val model: AppViewModel) : FragmentPagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return model.getHeaderImageCount()
        }

        override fun getItem(position: Int): Fragment? {
            return AppImageFragment.newInstance(model.getHeaderImageUrl(position))
        }
    }
}

internal class AppImageFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view:ImageView = inflater.inflate(R.layout.image_fragment_layout, container, false) as ImageView
        val imageUrl = arguments?.getString(ARG_PARAM_IMAGE_URL, "")
        BindingUtils.loadImage(view, imageUrl)
        return view
    }

    companion object {
        const val ARG_PARAM_IMAGE_URL = "ARG_PARAM_IMAGE_URL"
        fun newInstance(imageUrl: String): AppImageFragment {
            val fragment = AppImageFragment()
            val args = Bundle()
            args.putString(ARG_PARAM_IMAGE_URL, imageUrl)
            fragment.arguments = args
            return fragment
        }
    }

}

