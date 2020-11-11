package net.catstack.nfcpay

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import net.catstack.nfcpay.data.AccountRepository
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val accountRepository: AccountRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_history, R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!accountRepository.isUserAuthorized()) {
            navController.navigate(MobileNavigationDirections.actionGlobalLoginFragment())
        }
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
}