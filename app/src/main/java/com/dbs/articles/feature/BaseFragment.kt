package com.dbs.articles.feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dbs.articles.utils.ProgressSpinnerDialog

open class BaseFragment : Fragment() {
    private var progressSpinnerDialog: ProgressSpinnerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressSpinnerDialog = activity?.let { ProgressSpinnerDialog(it) }
    }

    override fun onDetach() {
        super.onDetach()
        hideProgressSpinnerDialog()
    }

    open fun showProgressSpinnerDialog() {
        progressSpinnerDialog?.showDialog()
    }

    open fun hideProgressSpinnerDialog() {
        progressSpinnerDialog?.hideDialog()
    }
}