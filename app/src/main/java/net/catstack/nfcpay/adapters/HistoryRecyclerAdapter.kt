package net.catstack.nfcpay.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item_view.view.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.domain.HistoryItemModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class HistoryRecyclerAdapter(
    private val historyItems: List<HistoryItemModel>,
    private val onItemClickListener: (HistoryItemModel) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentDate: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_item_view, parent, false)
        return ViewHolder(itemView, parent)
    }

    override fun getItemCount() = historyItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val currentItem = historyItems[position]

        viewHolder.historyItemInfo.setOnClickListener {
            onItemClickListener(currentItem)
        }

        viewHolder.dateTextView.text = currentItem.historyDate
        viewHolder.cardTitle.text = currentItem.title
        viewHolder.cardHelpText.text = viewHolder.parent.context.getString(R.string.history_with_nfc)

        val cost = currentItem.cost
        val firstCost = DecimalFormat("###,###", DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }).format(cost)
        val secondCost =
            (((cost * 100) - cost.toLong() * 100)).toLong().toString()
                .padEnd(2, '0')

        viewHolder.incomeText.text = "+${firstCost + if(secondCost != "00") ",$secondCost" else ""} ₽"

        val title = currentItem.title.toLowerCase()
        when {
            title.startsWith("mir") -> {
                viewHolder.cardTypeImage.setImageResource(R.drawable.ic_mir)
            }
            title.startsWith("visa") -> {
                viewHolder.cardTypeImage.setImageResource(R.drawable.ic_visa)
            }
            title.startsWith("mastercard") -> {
                viewHolder.cardTypeImage.setImageResource(R.drawable.ic_mastercard)
            }
        }

        if (currentItem.historyDate == currentDate) {
            viewHolder.dateTextView.visibility = View.GONE
        } else {
            viewHolder.dateTextView.visibility = View.VISIBLE
            currentDate = currentItem.historyDate
        }
    }

    class ViewHolder(itemView: View, val parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        val dateTextView = itemView.dateText!!
        val cardTypeImage = itemView.cardTypeImage!!
        val cardTitle = itemView.cardTitle!!
        val cardHelpText = itemView.cardHelpText!!
        val incomeText = itemView.incomeText!!
        val historyItemInfo = itemView.historyItemInfo!!
    }
}