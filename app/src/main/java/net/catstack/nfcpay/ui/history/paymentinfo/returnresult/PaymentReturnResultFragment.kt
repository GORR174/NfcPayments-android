package net.catstack.nfcpay.ui.history.paymentinfo.returnresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.payment_return_result_fragment.*
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.server.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentReturnResultFragment : BaseFragment(true) {
    private val viewModel: PaymentReturnResultViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_return_result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = PaymentReturnResultFragmentArgs.fromBundle(requireArguments())

        viewModel.returnPayment(args.paymentId, args.cardNumber)

        viewModel.returnResult.observe(viewLifecycleOwner) {
            if (it !is Result.Loading) {
                loadingProgressBar.visibility = View.GONE
            }
            when (it) {
                Result.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                    successfulPaymentLayout.visibility = View.GONE
                }
                is Result.Success -> {
                    successfulPaymentLayout.visibility = View.VISIBLE
                }
                is Result.ServerError -> Toast.makeText(
                    requireContext(),
                    it.serverError.message,
                    Toast.LENGTH_SHORT
                ).show()
                Result.InternetError -> onInternetError()
            }
        }

        backButton.setOnClickListener {
            findNavController().navigate(PaymentReturnResultFragmentDirections.actionPaymentReturnResultFragmentToNavigationHistory())
        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).hideBottomNavigation()
    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).showBottomNavigation()
        super.onDestroy()
    }
}