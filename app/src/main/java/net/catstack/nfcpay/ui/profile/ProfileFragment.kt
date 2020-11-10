package net.catstack.nfcpay.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_profile.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment

class ProfileFragment : BaseFragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel.text.observe(viewLifecycleOwner) {
            text_notifications.text = it
        }
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}