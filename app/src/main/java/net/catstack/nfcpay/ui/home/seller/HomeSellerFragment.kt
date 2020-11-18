package net.catstack.nfcpay.ui.home.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeSellerFragment : Fragment() {
    // TODO: Add HomeSellerViewModel to DI
    private val viewModel: HomeSellerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_seller_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).showBottomNavigation()
    }
}