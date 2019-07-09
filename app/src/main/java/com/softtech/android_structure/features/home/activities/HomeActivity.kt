package com.softtech.android_structure.features.home.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.softtech.android_structure.R
import com.softtech.android_structure.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : BaseActivity(), NavController.OnDestinationChangedListener  {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupActionBar()
        setupDrawerToggle()
        setupNavigation()



    }
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        toolbar.title=destination.label
    }


    private fun setupActionBar() {

        setSupportActionBar(toolbar)
    }


    private fun setupNavigation() {
        drawerNavigationView.menu.clear()

        drawerNavigationView.inflateMenu(R.menu.home)

        navController = Navigation.findNavController(this, R.id.home_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        findNavController(R.id.home_nav_fragment).addOnDestinationChangedListener(this)
        setupActionBarWithNavController(navController, appBarConfiguration)

        NavigationUI.setupWithNavController(drawerNavigationView, findNavController(R.id.home_nav_fragment))

    }


    private fun setupDrawerToggle() {
        drawerToggle = object : ActionBarDrawerToggle(
            this, /* Host Activity */
            drawerLayout, /* DrawerLayout object */
            toolbar,
            R.string.drawer_open, /* "Open drawer" description for accessibility */
            R.string.drawer_close  /* "Close drawer" description for accessibility */
        ) {}

        drawerLayout.addDrawerListener(drawerToggle)

        drawerToggle.syncState()
    }
}
