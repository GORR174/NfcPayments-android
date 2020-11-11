package net.catstack.nfcpay

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import net.catstack.nfcpay.data.AccountRepository
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val accountRepository: AccountRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_history, R.id.navigation_profile)
            .setOpenableLayout(drawerLayout)
            .build()
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navigationDrawer.menu.add("Customer").setIcon(R.drawable.ic_profile).setOnMenuItemClickListener {
            return@setOnMenuItemClickListener true
        }
        navigationDrawer.menu.add("АО «Альфа-Банк»").setIcon(R.drawable.ic_home).setOnMenuItemClickListener {
            return@setOnMenuItemClickListener true
        }
        navigationDrawer.menu.add("ООО «Моя оборона»").setIcon(R.drawable.ic_home).setOnMenuItemClickListener {
            return@setOnMenuItemClickListener true
        }

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
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}