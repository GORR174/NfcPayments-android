package net.catstack.nfcpay.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginDecoration(
    private val margin: Int,
    private val spaceHeight: Int,
    private val elementsSize: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = spaceHeight
            right = spaceHeight

            val position = parent.getChildAdapterPosition(view)
            if (position == 0) {
                left = margin
            } else if (position == elementsSize - 1) {
                right = margin
            }
        }
    }
}