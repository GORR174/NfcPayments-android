package net.catstack.nfcpay.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel.userToken.observe(viewLifecycleOwner) {
            tokenText.text = it
        }

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getProfile()

        logoutButton.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(
                ProfileFragmentDirections.actionGlobalLoginFragment()
            )
        }
    }
}