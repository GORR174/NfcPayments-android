package net.catstack.nfcpay.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.loadingProgressBar
import kotlinx.android.synthetic.main.toolbar_company_layout.*
import kotlinx.android.synthetic.main.toolbar_company_layout.view.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.adapters.HistoryRecyclerAdapter
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.domain.HistoryItemModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment(true, R.color.background) {

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolBar.companyName.text = viewModel.profileModel.company.companyName
        logoutIcon.visibility = View.GONE

        viewModel.loadHistory()

        viewModel.historyResult.observe(viewLifecycleOwner) {
            if (it !is Result.Loading) {
                loadingProgressBar.visibility = View.GONE
            }
            when (it) {
                Result.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    loadHistoryToAdapter(it.data)
                }
                is Result.ServerError -> Toast.makeText(
                    requireContext(),
                    it.serverError.message,
                    Toast.LENGTH_SHORT
                ).show()
                Result.InternetError -> onInternetError()
            }
        }
    }

    private fun loadHistoryToAdapter(historyItems: List<HistoryItemModel>) {
        historyRecyclerView.adapter = HistoryRecyclerAdapter(historyItems) {
            findNavController().navigate(
                HistoryFragmentDirections.actionNavigationHistoryToPaymentInfoFragment(it)
            )
        }
    }
}