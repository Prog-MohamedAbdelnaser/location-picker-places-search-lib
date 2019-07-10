package com.softtech.android_structure.features.settings.fragmentes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.SingleChoiceListener
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.softtech.android_structure.R
import com.softtech.android_structure.base.fragment.BaseFragment
import com.softtech.android_structure.features.common.LanguageViewModel
import com.softtech.android_structure.features.common.showSnackbar
import com.softtech.android_structure.features.home.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment :BaseFragment(){

    private lateinit var dialog: MaterialDialog
    private val languageViewModel:LanguageViewModel by viewModel()
    private val languageLabels: List<String> by lazy {
        resources.getStringArray(R.array.language_labels).toList()
    }

    private val languageValues: List<String> by lazy {
        resources.getStringArray(R.array.language_values).toList()
    }

    override fun layoutResource(): Int =R.layout.fragment_setting
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        initUI()
    }

    private fun initUI() {

        loutLanguage.setOnClickListener { showSelectLanguageDialog() }
        loutFeedback.setOnClickListener { showSnackbar(view!!,getString(R.string.option_not_activ_now)) }
    }


    private fun showSelectLanguageDialog() {
        context?.apply {
            dialog = MaterialDialog(this)
            dialog.show {
                title(R.string.select_language)
                listItemsSingleChoice(
                    items = languageLabels,
                    initialSelection = languageValues.indexOf(languageViewModel.getLanguage()),
                    selection = object : SingleChoiceListener {
                        override fun invoke(dialog: MaterialDialog, index: Int, text: String) {
                            onLanguageSelected(index)
                        }
                    }
                )
            }
        }

    }

    private fun onLanguageSelected(index: Int) {

        val selectedLanguage = languageValues[index]

        if (selectedLanguage.equals(languageViewModel.getLanguage(), true).not()) {
            context?.apply {
                languageViewModel.setLanguage(selectedLanguage)
                val i = Intent(requireActivity(), HomeActivity::class.java)
                activity!!.overridePendingTransition(0,0)
               requireActivity(). startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                System.exit(0)


            }

        }

    }
}
