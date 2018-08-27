package org.kinecosystem.kinit.view.backup

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.backup_qrcode.*
import org.kinecosystem.kinit.R
import org.kinecosystem.kinit.databinding.BackupQrcodeBinding
import org.kinecosystem.kinit.util.GeneralUtils
import org.kinecosystem.kinit.view.BaseFragment


class BackupQRCodeFragment : BaseFragment() {
    companion object {
        fun newInstance(): Fragment = BackupQRCodeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next.setOnClickListener {
            activity?.let {
                GeneralUtils.closeKeyboard(activity, next)
                (it as BackupActions).onNext()
            }
        }

        backBtn.setOnClickListener({
            activity?.let {
                (it as BackupActions).onBack()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (activity !is BackupActions) {
            Log.e("BackupQRCodeFragment", "Activity must implement BackupActions")
            activity?.finish()
        }
        val binding = DataBindingUtil.inflate<BackupQrcodeBinding>(inflater, R.layout.backup_qrcode, container, false)
        binding.model = (activity as BackupActions).getBackUpModel()
        return binding.root
    }
}