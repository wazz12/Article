package com.dbs.articles.feature.articleList

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dbs.articles.R
import com.dbs.articles.feature.ArticleClickListener
import com.dbs.articles.model.Article
import com.dbs.articles.utils.convertDate
import com.dbs.articles.utils.inflate
import com.dbs.articles.utils.setAvatarImage
import kotlinx.android.synthetic.main.article_list_item.view.*
import java.sql.Date

class ArticleListAdapter(private val articleMutableList: MutableList<Article>) :
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    private lateinit var articleClickListener: ArticleClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.article_list_item))

    override fun getItemCount() = articleMutableList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(articleMutableList[position])
        holder.bindClickListener(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(article: Article) {
            with(itemView) {
                article_title_text_view.text = article.title
                article_date_text_view.text = convertDate(Date(article.last_update))
                article_short_description_text_view.text = article.short_description
                setAvatarImage(context, article.avatar, avatar_image_view)
            }
        }

        fun bindClickListener(position: Int) {
            itemView.setOnClickListener {
                articleClickListener.onArticleClick(position)
            }
        }
    }

    fun getItem(position: Int): Article {
        return articleMutableList[position]
    }

    fun setArticleClickListener(articleClickListener: ArticleClickListener) {
        this.articleClickListener = articleClickListener
    }
}