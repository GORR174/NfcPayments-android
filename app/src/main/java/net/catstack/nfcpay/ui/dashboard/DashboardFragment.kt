package net.catstack.nfcpay.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_dashboard.*
import net.catstack.nfcpay.R

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            text_dashboard.text = it
        }
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
}