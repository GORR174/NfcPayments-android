package net.catstack.nfcpay.ui.history.paymentinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.create_payment_fragment.*
import kotlinx.android.synthetic.main.payment_info_fragment.*
import kotlinx.android.synthetic.main.payment_info_fragment.backButton
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.domain.HistoryItemModel
import net.catstack.nfcpay.ui.NfcActivity
import net.catstack.nfcpay.ui.payment.create.CreatePaymentFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class PaymentInfoFragment : BaseFragment(true, R.color.background) {
    // TODO: Add PaymentInfoViewModel to DI
    private val viewModel: PaymentInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_info_fragment, container, false)
    }

    lateinit var historyItem: HistoryItemModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity() as MainActivity).hideBottomNavigation()

        historyItem = PaymentInfoFragmentArgs.fromBundle(requireArguments()).historyItemModel

        val firstCost = DecimalFormat("###,###", DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }).format(historyItem.cost)
        val secondCost =
            (((historyItem.cost * 100) - historyItem.cost.toLong() * 100)).toLong().toString()
                .padEnd(2, '0')
        costText.text = resources.getString(R.string.home_balance, firstCost, secondCost)

        cardText.text = historyItem.title
        dateText.text = historyItem.paymentDateAndTime
        paymentTypeText.text = resources.getString(R.string.history_with_nfc)

        val title = historyItem.title.toLowerCase()
        when {
            title.startsWith("mir") -> {
                cardTypeImage.setImageResource(R.drawable.ic_mir)
            }
            title.startsWith("visa") -> {
                cardTypeImage.setImageResource(R.drawable.ic_visa)
            }
            title.startsWith("mastercard") -> {
                cardTypeImage.setImageResource(R.drawable.ic_mastercard)
            }
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        returnPaymentButton.setOnClickListener {
            val intent = Intent(requireContext(), NfcActivity::class.java)
            intent.putExtra("isReturn", true)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (data == null)
                return

            val isSuccessful = data.getBooleanExtra("isSuccessful", false)
            if (isSuccessful) {
                val cardNumber = data.getLongExtra("cardNumber", 0)
                findNavController().navigate(
                    PaymentInfoFragmentDirections.actionPaymentInfoFragmentToPaymentReturnResultFragment(
                        cardNumber,
                        historyItem.paymentId
                    )
                )
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).showBottomNavigation()
        super.onDestroy()
    }
}