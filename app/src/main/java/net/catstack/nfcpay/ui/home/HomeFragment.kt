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
import net.catstack.nfcpay.common.HorizontalMarginDecoration
import net.catstack.nfcpay.common.server.ResponseStatus
import net.catstack.nfcpay.domain.HomeNewsModel
import net.catstack.nfcpay.domain.PaymentPatternModel
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

        viewModel.loadProfile()

        viewModel.responseStatus.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseStatus.Successful -> {
                    val company = it.response.company
                    toolBar.companyName.text = company.companyName
                    val balance = company.billBalance

                    val firstBalance = DecimalFormat("###,###", DecimalFormatSymbols().apply {
                        groupingSeparator = ' '
                    }).format(balance)
                    val secondBalance =
                        (((balance * 100) - balance.toLong() * 100)).toLong().toString()
                            .padEnd(2, '0')
                    // TODO: 03.12.2020 use string resource
                    moneyBalance.text = "$firstBalance,$secondBalance ₽"
                }
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
                is ResponseStatus.Default -> {}
                is ResponseStatus.Loading -> {}
            }
        }

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

        val news = listOf(
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Бесконтактная оплата проще", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Бесконтактная оплата проще", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
            HomeNewsModel("https://www.catstack.net/img/alphapay/news1_image.png", "Тест", "Подробнее"),
        )

        newsRecyclerView.adapter = HomeNewsRecyclerAdapter(news) {
            println(it.name)
        }
    }
}