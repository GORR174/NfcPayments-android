package net.catstack.nfcpay.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import net.catstack.nfcpay.MainActivity

abstract class BaseFragment(val isToolbarShouldBeHidden: Boolean = false) : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireActivity() as MainActivity

        if (isToolbarShouldBeHidden) {
            activity.supportActionBar?.hide()
        } else {
            activity.supportActionBar?.show()
        }
    }
}