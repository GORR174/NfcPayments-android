package net.catstack.nfcpay.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.create_payment_fragment.*
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePaymentFragment : BaseFragment(true, R.color.background) {
    private val viewModel: CreatePaymentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_payment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNavigation()

        sumInputField.addTextChangedListener {
            if (sumInputField.text.isNotBlank()) {
                sumInputFieldInvisibleText.text = sumInputField.text
            } else {
                sumInputFieldInvisibleText.text = "0"
            }

            nextButton.isEnabled = sumInputField.text.isNotBlank() && sumInputField.text.toString() != "0"
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        nextButton.isEnabled = false

        cardMethodButton.setOnClickListener {
            cardMethodButton.setTextColor(resources.getColor(R.color.white, null))
            cardMethodButton.setBackgroundResource(R.drawable.button_method_selected)

            qrMethodButton.setTextColor(resources.getColor(R.color.primary, null))
            qrMethodButton.setBackgroundResource(R.drawable.button_method_unselected)
        }

        qrMethodButton.setOnClickListener {
            qrMethodButton.setTextColor(resources.getColor(R.color.white, null))
            qrMethodButton.setBackgroundResource(R.drawable.button_method_selected)

            cardMethodButton.setTextColor(resources.getColor(R.color.primary, null))
            cardMethodButton.setBackgroundResource(R.drawable.button_method_unselected)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).showBottomNavigation()
    }
}