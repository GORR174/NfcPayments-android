package net.catstack.nfcpay.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_news_item_view.view.*
import net.catstack.nfcpay.R
import net.catstack.nfcpay.domain.HomeNewsModel

class HomeNewsRecyclerAdapter(
    private val news: List<HomeNewsModel>,
    private val onItemClickListener: (HomeNewsModel) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_news_item_view, parent, false)
        return ViewHolder(itemView, parent)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val currentNews = news[position]

        viewHolder.itemView.setOnClickListener {
            onItemClickListener(currentNews)
        }

        viewHolder.newsName.text = currentNews.name
        viewHolder.continueName.text = currentNews.continueName
        viewHolder.continueName.paintFlags = viewHolder.continueName.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        Glide.with(viewHolder.parent.context)
            .load(currentNews.imageUrl)
            .into(viewHolder.image)
    }

    class ViewHolder(itemView: View, val parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        val newsName = itemView.newsName!!
        val continueName = itemView.newsContinueName!!
        val image = itemView.newsImage!!
    }
}