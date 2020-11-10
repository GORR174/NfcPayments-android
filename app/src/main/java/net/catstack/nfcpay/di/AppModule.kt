package net.catstack.nfcpay.di

import net.catstack.nfcpay.ui.history.HistoryViewModel
import net.catstack.nfcpay.ui.home.HomeViewModel
import net.catstack.nfcpay.ui.login.LoginViewModel
import net.catstack.nfcpay.ui.profile.ProfileViewModel
import net.catstack.nfcpay.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { HomeViewModel() }
    viewModel { HistoryViewModel() }
    viewModel { ProfileViewModel() }
}

val appModules = listOf(appModule)