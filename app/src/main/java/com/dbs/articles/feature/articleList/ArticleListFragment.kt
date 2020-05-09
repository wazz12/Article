package com.dbs.articles.feature.articleList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dbs.articles.R
import com.dbs.articles.feature.ArticleClickListener
import com.dbs.articles.feature.ArticleMainActivity.Companion.ARTICLE
import com.dbs.articles.feature.BaseFragment
import com.dbs.articles.feature.appToolbarTitle
import com.dbs.articles.model.Article
import com.dbs.articles.utils.isNetworkAvailable
import com.dbs.articles.utils.showAlertDialog
import com.dbs.articles.utils.visibleIfTrue
import com.dbs.articles.viewModel.ArticleViewModel
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.fragment_article_list.*

class ArticleListFragment : BaseFragment(), ArticleClickListener {

    private lateinit var articleViewModel: ArticleViewModel
    private var articleMutableList: MutableList<Article> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        setToolBar(getString(R.string.article_list))
        setObservers()

        if (isNetworkAvailable(requireActivity())) {
            showProgressSpinnerDialog()
            articleViewModel.getAllArticles()
        } else {
            showAlertDialog(
                requireContext(),
                R.string.network_error,
                R.string.no_network_error_message
            )
        }
    }

    override fun onStop() {
        super.onStop()

        articleViewModel.cancelAllRequests()
        hideProgressSpinnerDialog()
    }

    private fun setToolBar(title: String) {
        toolbarNavIcon.visibleIfTrue(false)
        btnToolbarAction.visibleIfTrue(false)
        toolbar.appToolbarTitle(title)
    }

    private fun setObservers() {
        articleViewModel.allArticlesLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressSpinnerDialog()
            it?.let { articleList ->
                articleMutableList = articleList.toMutableList()
                setArticleListAdapter()
            } ?: showAlertDialog(requireContext(), R.string.network_error, R.string.error_message)
        })
    }

    private fun setArticleListAdapter() {
        article_recycler_view.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        article_recycler_view.adapter = articleListAdapter
        articleListAdapter.setArticleClickListener(this)
    }

    private val articleListAdapter by lazy {
        ArticleListAdapter(articleMutableList)
    }

    override fun onArticleClick(position: Int) {
        val article = articleListAdapter.getItem(position)
        openArticleDetail(article)
    }

    private fun openArticleDetail(article: Article) {
        val bundle = Bundle()
        bundle.putParcelable(ARTICLE, article)
        Navigation.findNavController(requireActivity(), R.id.nav_frag_container)
            .navigate(R.id.action_articleListFragment_to_articleDetailFragment, bundle)
    }
}