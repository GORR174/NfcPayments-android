package net.catstack.nfcpay.adapters

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.payment_patterns_item_view.view.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.domain.PaymentPatternModel

class PaymentPatternsRecyclerAdapter(
    private val patterns: List<PaymentPatternModel>,
    private val onItemClickListener: (PaymentPatternModel) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.payment_patterns_item_view, parent, false)
        return ViewHolder(itemView, parent)
    }

    override fun getItemCount() = patterns.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val pattern = patterns[position]

        viewHolder.itemView.setOnClickListener {
            viewHolder.patternImage.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(100)
                .withEndAction {
                    viewHolder.patternImage.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .withEndAction {
                            onItemClickListener(pattern)
                        }
                }
        }

        viewHolder.patternName.text = pattern.name
    }

    class ViewHolder(itemView: View, val parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        val patternName = itemView.paymentPatternName!!
        val patternImage = itemView.paymentPatternImageView!!
    }
}