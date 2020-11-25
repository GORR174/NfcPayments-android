package net.catstack.nfcpay.views

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.MaterialToolbar
import net.catstack.nfcpay.R

class Toolbar(context: Context, attributes: AttributeSet) : MaterialToolbar(context, attributes) {

    init {
        inflate(context, R.layout.toolbar_layout, this)
    }
}