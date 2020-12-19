package net.catstack.nfcpay.di

import android.content.Context
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.AuthRepository
import net.catstack.nfcpay.data.network.PaymentRepository
import net.catstack.nfcpay.data.network.ProfileRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { AccountRepository(get(), get(named("account"))) }

    single { AuthRepository(get(), get()) }
    single { ProfileRepository(get(), get()) }
    single { PaymentRepository(get(), get()) }

    single(named("account")) { androidContext().getSharedPreferences("account", Context.MODE_PRIVATE) }
}