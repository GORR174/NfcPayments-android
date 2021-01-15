package net.catstack.nfcpay.di

import com.google.gson.GsonBuilder
import net.catstack.nfcpay.ui.history.HistoryViewModel
import net.catstack.nfcpay.ui.home.HomeViewModel
import net.catstack.nfcpay.ui.auth.login.LoginViewModel
import net.catstack.nfcpay.ui.payment.PaymentViewModel
import net.catstack.nfcpay.ui.auth.register.RegisterViewModel
import net.catstack.nfcpay.ui.history.paymentinfo.returnresult.PaymentReturnResultViewModel
import net.catstack.nfcpay.ui.payment.result.PaymentResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { PaymentViewModel(get()) }
    viewModel { PaymentResultViewModel(get()) }
    viewModel { PaymentReturnResultViewModel(get()) }

    single {
        GsonBuilder()
            .create()
    }
}

val appModules = listOf(appModule, repositoryModule, networkModule)