package com.dbs.articles.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dbs.articles.R

class ArticleMainActivity : AppCompatActivity() {

    private fun provideStartArgBundle() = Bundle()
    private fun setStartFragment(): Int = 0

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        val graph = getNavController().navInflater.inflate(R.navigation.nav_graph_article)
        if (setStartFragment() != 0) {
            graph.startDestination = setStartFragment()
        }

        getNavController().setGraph(graph, provideStartArgBundle())
    }
}

//For Activity Call
fun FragmentActivity.getNavController(): NavController {
    return Navigation.findNavController(this, R.id.nav_frag_container)
}