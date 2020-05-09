package com.dbs.articles.feature.articleEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dbs.articles.R
import com.dbs.articles.feature.ArticleMainActivity.Companion.ARTICLE_DETAIL
import com.dbs.articles.feature.ArticleMainActivity.Companion.TITLE
import com.dbs.articles.feature.BaseFragment
import com.dbs.articles.feature.appToolbarActionTitle
import com.dbs.articles.feature.appToolbarTitle
import com.dbs.articles.model.ArticleDetail
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.fragment_edit_article.*

class ArticleEditFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        save_button.setOnClickListener {
            onSaveClick()
        }
    }

    override fun onStop() {
        super.onStop()
        hideProgressSpinnerDialog()
    }

    private fun setToolBar(title: String) {
        toolbar.appToolbarTitle(title)
        btnToolbarAction.text = getString(R.string.cancel)
        toolbar.onNavBackPressed(View.OnClickListener {
            activity?.onBackPressed()
        })
        btnToolbarAction?.let {
            toolbar.appToolbarActionTitle(getString(R.string.cancel))
            it.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun getData() {
        arguments?.let {

            if (it.containsKey(TITLE)) {
                setToolBar(getString(R.string.edit) + " " + it.getString(TITLE))
            }
            if (it.containsKey(ARTICLE_DETAIL)) {
                val articleDetail = it.getParcelable<ArticleDetail>(ARTICLE_DETAIL) as ArticleDetail
                setArticleData(articleDetail.text)
            }
        }
    }

    private fun setArticleData(text: String) {
        article_description_edit_text_view.setText(text)
    }

    private fun onSaveClick() {
        if (article_description_edit_text_view.text.isNotEmpty()) {
            activity?.onBackPressed()
        } else {
            article_description_edit_text_view.error = getString(R.string.description_empty_error)
        }
    }
}