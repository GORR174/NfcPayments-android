package net.catstack.nfcpay.di

import android.content.Context
import net.catstack.nfcpay.data.AccountRepository
import net.catstack.nfcpay.data.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { AccountRepository(get(), get(named("account"))) }
    single { SettingsRepository(get(), get(named("settings"))) }

    single(named("account")) { androidContext().getSharedPreferences("account", Context.MODE_PRIVATE) }
    single(named("settings")) { androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE) }
}