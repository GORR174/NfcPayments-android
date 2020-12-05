package net.catstack.nfcpay.ui.auth.register

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.register_fragment.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.server.Result
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
            if (hasFocus) {
                if (phoneInputField.text.toString().isBlank()) {
                    phoneInputField.setText("+7")
                    phoneInputField.setSelection(phoneInputField.text.length)
                }
            }
        }

        viewModel.registerResult.observe(viewLifecycleOwner) {
            if (it !is Result.Loading) {
                loadingProgressBar.visibility = View.GONE
            }

            when (it) {
                Result.Loading -> loadingProgressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Вы успешно отправили заявку. Мы с вами свяжемся",
                        Toast.LENGTH_LONG
                    ).show()

                    findNavController().popBackStack()
                }
                is Result.ServerError -> Toast.makeText(
                    requireContext(),
                    it.serverError.message,
                    Toast.LENGTH_SHORT
                ).show()
                Result.InternetError -> Toast.makeText(
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