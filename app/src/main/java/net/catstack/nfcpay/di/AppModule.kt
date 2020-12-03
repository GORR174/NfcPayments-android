package net.catstack.nfcpay.di

import com.google.gson.GsonBuilder
import net.catstack.nfcpay.ui.history.HistoryViewModel
import net.catstack.nfcpay.ui.home.HomeViewModel
import net.catstack.nfcpay.ui.auth.login.LoginViewModel
import net.catstack.nfcpay.ui.payment.PaymentViewModel
import net.catstack.nfcpay.ui.auth.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { HistoryViewModel() }
    viewModel { PaymentViewModel(get()) }

    single {
        GsonBuilder()
            .create()
    }
}

val appModules = listOf(appModule, repositoryModule, networkModule)