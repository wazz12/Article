package com.dbs.articles.feature.articleDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dbs.articles.R
import com.dbs.articles.feature.ArticleMainActivity.Companion.ARTICLE
import com.dbs.articles.feature.BaseFragment
import com.dbs.articles.feature.appToolbarActionTitle
import com.dbs.articles.feature.appToolbarTitle
import com.dbs.articles.model.Article
import com.dbs.articles.model.ArticleDetail
import com.dbs.articles.utils.isNetworkAvailable
import com.dbs.articles.utils.setAvatarImage
import com.dbs.articles.utils.showAlertDialog
import com.dbs.articles.viewModel.ArticleViewModel
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.fragment_article_detail.*

class ArticleDetailsFragment : BaseFragment() {

    private lateinit var articleViewModel: ArticleViewModel
    private var avatar: String? = null
    private var title: String? = null
    private var articleDetail: ArticleDetail? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        setObservers()
        getData()
    }

    override fun onStop() {
        super.onStop()

        articleViewModel.cancelAllRequests()
        hideProgressSpinnerDialog()
    }

    private fun setToolBar() {
        toolbar.appToolbarTitle(title)
        btnToolbarAction.text = getString(R.string.edit)
        toolbar.onNavBackPressed(View.OnClickListener {
            activity?.onBackPressed()
        })
        btnToolbarAction?.let {
            toolbar.appToolbarActionTitle(getString(R.string.edit))
            it.setOnClickListener {
                // open edit screen
            }
        }
    }

    private fun setObservers() {
        articleViewModel.articleDetailLiveData.observe(this, Observer {
            hideProgressSpinnerDialog()
            it?.let { articleDetail ->
                this.articleDetail = articleDetail
                setArticleData(articleDetail.text)
            } ?: showAlertDialog(requireContext(), R.string.network_error, R.string.error_message)
        })
    }

    private fun getData() {
        arguments?.let {
            if (it.containsKey(ARTICLE)) {
                val article = it.getParcelable<Article>(ARTICLE) as Article
                title = article.title
                setToolBar()
                avatar = article.avatar

                if (isNetworkAvailable(requireActivity())) {
                    showProgressSpinnerDialog()
                    articleViewModel.getArticleDetail(article.id)
                } else {
                    showAlertDialog(
                        requireContext(),
                        R.string.network_error,
                        R.string.no_network_error_message
                    )
                }
            }
        }
    }

    private fun setArticleData(text: String) {
        article_detail_description_text_view.text = text
        setAvatarImage(requireContext(), avatar, avatar_image_view)
    }
}