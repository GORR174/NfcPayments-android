package net.catstack.nfcpay.ui.history.paymentinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.catstack.nfcpay.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentInfoFragment : Fragment() {
    // TODO: Add PaymentInfoViewModel to DI
    private val viewModel: PaymentInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}