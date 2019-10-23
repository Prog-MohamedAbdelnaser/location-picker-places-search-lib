package com.softtech.android_structure.features.home.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.softtech.android_structure.R
import com.softtech.android_structure.base.activity.BaseActivity
import com.softtech.android_structure.domain.entities.account.User
import com.softtech.android_structure.features.common.CommonState
import com.softtech.android_structure.features.common.hideKeyboard
import com.softtech.android_structure.features.myaccount.vm.AccountViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeActivity : BaseActivity(), NavController.OnDestinationChangedListener  {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val myAccountViewModel:AccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupNavigation()
        setupActionBar()
        initObservers()

    }

    private fun initObservers() {

        myAccountViewModel.apply {
            userStateLiveData.observe(this@HomeActivity,androidx.lifecycle.Observer {
                handleGetUserState(it)
            })
        }
    }

    private fun handleGetUserState(state: CommonState<User>?) {
        Timber.i("handleGetUserState${state.toString()}")
        when(state){
            is CommonState.Success->{
               setUpHeader(state.data)
            }

        }
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        toolbar.title=destination.label
    }


    private fun setupActionBar() {

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START) else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        hideKeyboard(this.window)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        drawerNavigationView.menu.clear()
        drawerNavigationView.inflateMenu(R.menu.home)
        navController = Navigation.findNavController(this, R.id.home_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        findNavController(R.id.home_nav_fragment).addOnDestinationChangedListener(this)
        NavigationUI.setupWithNavController(drawerNavigationView, findNavController(R.id.home_nav_fragment))

    }

    private fun setUpHeader(user: User) {
        val headerView = drawerNavigationView.getHeaderView(0)
        headerView.tvUserName.text=user.name

    }


}
