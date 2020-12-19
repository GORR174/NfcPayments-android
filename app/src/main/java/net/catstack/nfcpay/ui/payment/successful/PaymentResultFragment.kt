package net.catstack.nfcpay.ui.payment.successful

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.payment_result_fragment.*
import kotlinx.android.synthetic.main.payment_result_fragment.loadingProgressBar
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.server.Result
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class PaymentResultFragment : BaseFragment(true) {
    private val viewModel: PaymentResultViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val idempotenceKey = Random.nextLong()

        val args = PaymentResultFragmentArgs.fromBundle(requireArguments())

        viewModel.pay(idempotenceKey, args.cardNumber, args.cost, args.email)

        viewModel.paymentResult.observe(viewLifecycleOwner) {
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
            findNavController().navigate(PaymentResultFragmentDirections.actionPaymentResultFragmentToNavigationHome())
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