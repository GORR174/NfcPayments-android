package net.catstack.nfcpay.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.login_fragment.*
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.server.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(true) {
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity = requireActivity() as MainActivity

        activity.hideBottomNavigation()

        emailInputField.setText(viewModel.savedEmail ?: "")
        passwordInputField.setText(viewModel.savedPassword ?: "")

        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it !is Result.Loading) {
                loadingProgressBar.visibility = View.GONE
            }
            when (it) {
                Result.Loading -> loadingProgressBar.visibility = View.VISIBLE
                is Result.Success -> findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationHome())
                is Result.ServerError -> Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.login_wrong_logpass),
                    Toast.LENGTH_SHORT
                ).show()
                Result.InternetError -> onInternetError()
            }
        }

        loginButton.setOnClickListener {
            viewModel.login(emailInputField.text.toString(), passwordInputField.text.toString())
        }

        forgotPasswordButton.setOnClickListener {
            Toast.makeText(requireContext(), "Бывает", Toast.LENGTH_SHORT).show()
        }

        registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }
}