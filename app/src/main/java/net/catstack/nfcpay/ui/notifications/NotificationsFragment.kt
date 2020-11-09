package net.catstack.nfcpay.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_notifications.*
import net.catstack.nfcpay.R

class NotificationsFragment : Fragment() {

    private val notificationsViewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            text_notifications.text = it
        }
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }
}