package net.catstack.nfcpay.di

import com.google.gson.GsonBuilder
import net.catstack.nfcpay.ui.history.HistoryViewModel
import net.catstack.nfcpay.ui.home.HomeViewModel
import net.catstack.nfcpay.ui.login.LoginViewModel
import net.catstack.nfcpay.ui.payment.PaymentViewModel
import net.catstack.nfcpay.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { HistoryViewModel() }
    viewModel { PaymentViewModel(get()) }

    single {
        GsonBuilder()
            .create()
    }
}

val appModules = listOf(appModule, repositoryModule, networkModule)