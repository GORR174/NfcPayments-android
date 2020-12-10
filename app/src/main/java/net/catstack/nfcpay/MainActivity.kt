package net.catstack.nfcpay

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.domain.network.request.DeviceInfo
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val accountRepository: AccountRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        accountRepository.deviceInfo = DeviceInfo(deviceId, "DEVICE_TYPE_ANDROID")

        setSupportActionBar(toolBar)
        toolBar.backButton.setOnClickListener { navController.popBackStack() }
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val menuIDs = setOf(R.id.navigation_home, R.id.navigation_history, R.id.navigation_payment)
        val appBarConfiguration = AppBarConfiguration(menuIDs)

        navView.menu.findItem(R.id.navigation_payment_button).setOnMenuItemClickListener {
            navController.navigate(MobileNavigationDirections.actionGlobalCreatePaymentFragment())
            return@setOnMenuItemClickListener true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            toolBar.titleTextView.text = destination.label
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            if (controller.previousBackStackEntry != null && destination.id !in menuIDs) {
                toolBar.backButton.visibility = View.VISIBLE
            } else {
                toolBar.backButton.visibility = View.GONE
            }
        }

        navController.navigate(MobileNavigationDirections.actionGlobalLoginFragment())
    }

    fun hideBottomNavigation() {
        if (navView.visibility != View.GONE) {
            navView.visibility = View.GONE
        }
    }

    fun showBottomNavigation() {
        if (navView.visibility != View.VISIBLE) {
            navView.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        accountRepository.clearToken()
    }
}