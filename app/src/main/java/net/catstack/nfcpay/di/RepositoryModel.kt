package net.catstack.nfcpay.di

import android.content.Context
import net.catstack.nfcpay.data.AccountRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { AccountRepository(get(), get(named("account"))) }

    single(named("account")) { androidContext().getSharedPreferences("account", Context.MODE_PRIVATE) }
}