package net.catstack.nfcpay

import android.app.Application
import net.catstack.nfcpay.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NfcPaymentApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NfcPaymentApplication)
            modules(appModules)
        }
    }
}