package net.catstack.nfcpay.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_history.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment

class HistoryFragment : BaseFragment() {

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel.text.observe(viewLifecycleOwner) {
            text_dashboard.text = it
        }
        return inflater.inflate(R.layout.fragment_history, container, false)
    }
}