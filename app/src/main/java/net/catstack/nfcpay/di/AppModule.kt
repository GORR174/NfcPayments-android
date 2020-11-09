package net.catstack.nfcpay.di

import net.catstack.nfcpay.ui.dashboard.DashboardViewModel
import net.catstack.nfcpay.ui.home.HomeViewModel
import net.catstack.nfcpay.ui.login.LoginViewModel
import net.catstack.nfcpay.ui.notifications.NotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { HomeViewModel() }
    viewModel { DashboardViewModel() }
    viewModel { NotificationsViewModel() }
}

val appModules = listOf(appModule)