package net.catstack.nfcpay.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.toolbar_company_layout.*
import kotlinx.android.synthetic.main.toolbar_company_layout.view.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.adapters.HistoryRecyclerAdapter
import net.catstack.nfcpay.common.BaseFragment
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

        loadHistory()
    }

    private fun loadHistory() {
        val history = mutableListOf(
            HistoryItemModel(
                "Mir ***2917",
                5853f,
                "",
                "25 ноября, пн"
            ),
            HistoryItemModel(
                "Visa ***1567",
                268.76f,
                "",
                "25 ноября, пн"
            ),
            HistoryItemModel(
                "Mir ***1927",
                3265.9f,
                "",
                "23 ноября, пн"
            ),
            HistoryItemModel(
                "MasterCard ***3645",
                672f,
                "",
                "23 ноября, пн"
            ),
            HistoryItemModel(
                "Visa ***1257",
                553f,
                "",
                "22 ноября, пн"
            ),
            HistoryItemModel(
                "Mir ***5721",
                182f,
                "",
                "21 ноября, пн"
            ),
        )

        historyRecyclerView.adapter = HistoryRecyclerAdapter(history) {
            println(it.title)
        }
    }
}