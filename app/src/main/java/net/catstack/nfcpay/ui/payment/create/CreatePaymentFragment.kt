package net.catstack.nfcpay.ui.payment.create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.create_payment_fragment.*
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.ui.NfcActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment

class CreatePaymentFragment : BaseFragment(true, R.color.background) {

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
            onTextChanged()
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        nextButton.isEnabled = false

        val sum = CreatePaymentFragmentArgs.fromBundle(requireArguments()).sum
        if (sum != null) {
            sumInputField.setText(sum)
        }

        nextButton.setOnClickListener {
            val intent = Intent(requireContext(), NfcActivity::class.java)
            intent.putExtra("sum", sumInputField.text.toString().toInt())
            startActivityForResult(intent, 1)
        }

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

    private fun onTextChanged() {
        if (sumInputField.text.isNotBlank()) {
            sumInputFieldInvisibleText.text = sumInputField.text
        } else {
            sumInputFieldInvisibleText.text = "0"
        }

        nextButton.isEnabled =
            sumInputField.text.isNotBlank() && sumInputField.text.toString() != "0"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (data == null)
                return

            val isSuccessful = data.getBooleanExtra("isSuccessful", false)
            if (isSuccessful) {
                val cardName = data.getStringExtra("cardName") ?: "Card"
                val cardNumber = data.getLongExtra("cardNumber", 0)
                val email = if (emailInputField.text.isNotBlank()) emailInputField.text.toString() else null
                findNavController().navigate(
                    CreatePaymentFragmentDirections.actionCreatePaymentFragmentToPaymentResultFragment(
                        cardName,
                        cardNumber,
                        sumInputField.text.toString().toFloat(),
                        email
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