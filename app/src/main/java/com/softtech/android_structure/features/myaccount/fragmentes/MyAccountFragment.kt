package com.softtech.android_structure.features.myaccount.fragmentes

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.softtech.android_structure.R
import com.softtech.android_structure.base.dialogs.AlertDialogManager.createAlertDialog
import com.softartch_lib.component.fragment.BaseFragment
import com.softtech.android_structure.domain.entities.account.User
import com.softtech.android_structure.features.authorization.AuthorizationActivity
import com.softtech.android_structure.features.common.CommonState
import com.softtech.android_structure.features.myaccount.vm.AccountViewModel
import kotlinx.android.synthetic.main.fragment_myaccount.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MyAccountFragment : BaseFragment(){
    private val accountViewModel :AccountViewModel by viewModel()
    override fun layoutResource(): Int = R.layout.fragment_myaccount
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        initObservers()
        initEventHandler()
    }

    private fun initObservers() {
        accountViewModel.apply {
            userStateLiveData.observe(this@MyAccountFragment, Observer {
                handleGetUserState(it)
            })
        }
    }

    private fun handleGetUserState(state: CommonState<User>?) {
        Timber.i("handleGetUserState${state.toString()}")
        when(state){
            is CommonState.LoadingShow->showProgressDialog()
            is CommonState.LoadingFinished->hideProgressDialog()
            is CommonState.Success->{
                initUserUI(state.data)
            }

        }
    }
    fun initEventHandler(){
        loutLogout.setOnClickListener {
            createAlertDialog(requireContext(),getString(R.string.are_you_sure_you_want_to_sign_out_q),"",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    accountViewModel.clearUserData()
                    restart()
                }).show()
        }
    }

    fun restart(){
        activity!!.finish()
        activity!!.overridePendingTransition(0,0)
        startActivity(Intent(activity, AuthorizationActivity::class.java))
    }

    private fun initUserUI(user: User) {
        tvUserName.text=user.name
    }
}
