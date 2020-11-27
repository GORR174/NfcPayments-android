package net.catstack.nfcpay.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R

abstract class BaseFragment(val isToolbarShouldBeHidden: Boolean = false, val statusBarColor: Int = R.color.white) : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireActivity() as MainActivity

        if (isToolbarShouldBeHidden) {
            activity.supportActionBar?.hide()
        } else {
            activity.supportActionBar?.show()
        }

        activity.window.statusBarColor = resources.getColor(statusBarColor, null)
    }
}