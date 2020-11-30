package net.catstack.nfcpay.ui.auth.register

import android.os.Bundle
import android.os.LocaleList
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.register_fragment.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.server.ResponseStatus
import net.catstack.nfcpay.ui.auth.login.LoginFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        phoneInputField.addTextChangedListener(PhoneNumberFormattingTextWatcher("RU"))
        phoneInputField.addTextChangedListener {
            if (it?.length ?: 0 < 2 || !phoneInputField.text.toString().startsWith("+7")) {
                phoneInputField.setText("+7")
                phoneInputField.setSelection(phoneInputField.text.length)
            }
        }
        phoneInputField.setOnFocusChangeListener { v, hasFocus ->
            println("ASD DA DA DA")
            if (hasFocus) {
                if (phoneInputField.text.toString().isBlank()) {
                    phoneInputField.setText("+7")
                    phoneInputField.setSelection(phoneInputField.text.length)
                }
            }
        }

        viewModel.responseStatus.observe(viewLifecycleOwner) {
            if (it is ResponseStatus.Loading) {
                loadingProgressBar.visibility = View.VISIBLE
            } else {
                loadingProgressBar.visibility = View.GONE
            }

            when (it) {
                is ResponseStatus.Successful -> Toast.makeText(
                    requireContext(),
                    "SUCCESSFUL",
                    Toast.LENGTH_SHORT
                ).show().also { findNavController().popBackStack() }
                is ResponseStatus.ServerError -> Toast.makeText(
                    requireContext(),
                    it.error.message,
                    Toast.LENGTH_SHORT
                ).show()
                is ResponseStatus.InternetError -> Toast.makeText(
                    requireContext(),
                    "Ошибка с интернет соединением",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        registerButton.setOnClickListener {
            viewModel.register(
                nameInputField.text.toString(),
                phoneInputField.text.toString(),
                emailInputField.text.toString(),
                innInputField.text.toString()
            )
        }
    }
}