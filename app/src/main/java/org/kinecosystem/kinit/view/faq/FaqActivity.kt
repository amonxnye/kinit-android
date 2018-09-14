package org.kinecosystem.kinit.view.faq

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import org.kinecosystem.kinit.KinitApplication
import org.kinecosystem.kinit.R
import org.kinecosystem.kinit.repository.UserRepository
import org.kinecosystem.kinit.util.SupportUtil
import org.kinecosystem.kinit.view.SingleFragmentActivity
import org.kinecosystem.kinit.viewmodel.FAQViewModel
import javax.inject.Inject

class FAQActivity : SingleFragmentActivity(), FAQViewModel.FAQActions {
    @Inject
    lateinit var userRepository: UserRepository

    private var webfragment: FAQWebFragment? = null

    override fun getFragment(): Fragment {
        webfragment = FAQWebFragment.getInstance()
        return webfragment as FAQWebFragment
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, FAQActivity::class.java)
    }

    init {
        KinitApplication.coreComponent.inject(this)
    }

    private var model: FAQViewModel = FAQViewModel()

    fun getModel(): FAQViewModel {
        return model
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.listener = this
    }

    override fun moveHome() {
        webfragment?.binding?.webview?.loadUrl(model.url)
    }

    override fun pageHelpfulClicked() {
        Toast.makeText(applicationContext, R.string.thank_you_for_feedback_text, Toast.LENGTH_LONG).show()
    }

    override fun contactSupport() {
        SupportUtil.openEmailSupport(this, userRepository)
    }

    override fun onBackPressed() {
        moveBack()
    }

    override fun moveBack() {
        val webview = webfragment?.binding?.webview
        if (webview?.url != model.url && webview?.canGoBack() == true)
            webview.goBack()
        else
            super.onBackPressed()
    }
}