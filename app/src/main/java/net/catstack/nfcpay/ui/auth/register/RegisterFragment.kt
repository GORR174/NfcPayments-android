package net.catstack.nfcpay.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        viewModel.responseStatus.observe(viewLifecycleOwner) {
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
                is ResponseStatus.Default -> println("DEFAULT")
                is ResponseStatus.Loading -> println("LOADING")
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