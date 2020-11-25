package net.catstack.nfcpay.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_payment.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentFragment : BaseFragment() {

    private val viewModel: PaymentViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel.userToken.observe(viewLifecycleOwner) {
            tokenText.text = it
        }

        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getProfile()

        logoutButton.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(
                PaymentFragmentDirections.actionGlobalLoginFragment()
            )
        }
    }
}