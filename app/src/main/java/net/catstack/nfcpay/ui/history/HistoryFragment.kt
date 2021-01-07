package net.catstack.nfcpay.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.toolbar_company_layout.*
import kotlinx.android.synthetic.main.toolbar_company_layout.view.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.adapters.HistoryRecyclerAdapter
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.utils.getHistoryDateAndTimeFormat
import net.catstack.nfcpay.common.utils.getHistoryDateFormat
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
        notificationIcon.visibility = View.GONE

//        loadTestHistory()
        viewModel.loadHistory()

        viewModel.historyResult.observe(viewLifecycleOwner) {
            when (it) {
                Result.Loading -> {}
                is Result.Success -> {
                    historyRecyclerView.adapter = HistoryRecyclerAdapter(it.data) {
                        println(it.title)
                    }
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

    private fun loadTestHistory() {
        val history = mutableListOf(
            HistoryItemModel(
                "Visa ***2768",
                23f,
                "",
                getHistoryDateFormat("2021-01-07T19:32:46Z"),
                getHistoryDateAndTimeFormat("2021-01-07T19:32:46Z")
            ),
            HistoryItemModel(
                "Mir ***2917",
                583f,
                "",
                getHistoryDateFormat("2021-01-07T19:24:46Z"),
                getHistoryDateAndTimeFormat("2021-01-07T19:24:46Z")
            ),
            HistoryItemModel(
                "Mir ***2917",
                5853f,
                "",
                getHistoryDateFormat("2021-01-07T18:44:46Z"),
                getHistoryDateAndTimeFormat("2021-01-07T18:44:46Z")
            ),
        )

        historyRecyclerView.adapter = HistoryRecyclerAdapter(history) {
            println(it.title)
        }
    }
}