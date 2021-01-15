package net.catstack.nfcpay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_company_layout.view.*
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.adapters.HomeNewsRecyclerAdapter
import net.catstack.nfcpay.adapters.PaymentPatternsRecyclerAdapter
import net.catstack.nfcpay.common.BaseFragment
import net.catstack.nfcpay.common.decorations.HorizontalMarginDecoration
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.domain.HomeNewsModel
import net.catstack.nfcpay.domain.PaymentPatternModel
import net.catstack.nfcpay.domain.ProfileModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class HomeFragment : BaseFragment(true, R.color.background) {

    private val viewModel: HomeViewModel by viewModel()

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

        loadProfile()
        loadPatterns()
        loadNews()
    }

    private fun loadProfile() {
        loadProfileModelToView(viewModel.archivedUser)
        viewModel.loadProfile()

        viewModel.profileResult.observe(viewLifecycleOwner) {
            when (it) {
                Result.Loading -> {}
                is Result.Success -> { loadProfileModelToView(it.data) }
                is Result.ServerError -> Toast.makeText(
                    requireContext(),
                    it.serverError.message,
                    Toast.LENGTH_SHORT
                ).show()
                Result.InternetError -> onInternetError()
            }
        }
    }

    private fun loadProfileModelToView(profileModel: ProfileModel) {
        val company = profileModel.company
        toolBar.companyName.text = company.companyName
        val balance = company.billBalance

        val firstBalance = DecimalFormat("###,###", DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }).format(balance)
        val secondBalance =
            (((balance * 100) - balance.toLong() * 100)).toLong().toString()
                .padEnd(2, '0')
        moneyBalance.text = resources.getString(R.string.home_balance, firstBalance, secondBalance)
    }

    private fun loadPatterns() {
        val patterns = listOf(
            PaymentPatternModel(R.drawable.image_pattern_create, "Создать шаблон"),
            PaymentPatternModel(R.drawable.ic_pattern_latte, "Латте 200 мл"),
            PaymentPatternModel(R.drawable.ic_pattern_donut, "Пончик с глазурью"),
            PaymentPatternModel(R.drawable.ic_pattern_americano, "Американо 150 мл"),
            PaymentPatternModel(R.drawable.ic_pattern_cake, "Пирожное"),
            PaymentPatternModel(R.drawable.ic_pattern_bun, "Булочка с корицей"),
            PaymentPatternModel(R.drawable.ic_pattern_cake2, "Кекс"),
        )

        patternsRecyclerView.addItemDecoration(
            HorizontalMarginDecoration(
                resources.getDimension(R.dimen.recycler_margin).toInt(),
                resources.getDimension(R.dimen.recycler_spacing).toInt() / 2,
                patterns.size
            )
        )

        patternsRecyclerView.adapter = PaymentPatternsRecyclerAdapter(patterns) {
            println(it.name)
        }
    }

    private fun loadNews() {
        val news = mutableListOf(
            HomeNewsModel(
                "https://www.catstack.net/img/alphapay/news1_image.png",
                "Бесконтактная оплата проще",
                "Подробнее"
            ),
            HomeNewsModel(
                "https://www.catstack.net/img/alphapay/news1_image.png",
                "Бесконтактная оплата проще",
                "Подробнее"
            ),
        )

        repeat(10) {
            news.add(
                HomeNewsModel(
                    "https://www.catstack.net/img/alphapay/news1_image.png",
                    "Тест",
                    "Подробнее"
                )
            )
        }

        newsRecyclerView.adapter = HomeNewsRecyclerAdapter(news) {
            println(it.name)
        }
    }
}