package net.catstack.nfcpay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_company_layout.view.*
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.adapters.PaymentPatternsRecyclerAdapter
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.HorizontalMarginDecoration
import net.catstack.nfcpay.domain.PaymentPatternModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(true, R.color.background) {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).showBottomNavigation()

        toolBar.companyName.text = "ИП “Барсук Банкуров“"
        moneyBalance.text = "25 200,90 ₽"

        val patterns = listOf(
            PaymentPatternModel(R.color.background, "Создать шаблон"),
            PaymentPatternModel(R.color.background, "Кофе"),
            PaymentPatternModel(R.color.background, "Кекс"),
            PaymentPatternModel(R.color.background, "Кекс"),
            PaymentPatternModel(R.color.background, "Кекс"),
            PaymentPatternModel(R.color.background, "Кекс"),
            PaymentPatternModel(R.color.background, "Кекс"),
            PaymentPatternModel(R.color.background, "Кекс"),
            PaymentPatternModel(R.color.background, "Кекс"),
        )

        patternsRecyclerView.addItemDecoration(HorizontalMarginDecoration(
            resources.getDimension(R.dimen.recycler_margin).toInt(),
            resources.getDimension(R.dimen.recycler_spacing).toInt() / 2,
            patterns.size
        ))
        patternsRecyclerView.adapter = PaymentPatternsRecyclerAdapter(patterns) {
            println(it.name)
        }
    }
}