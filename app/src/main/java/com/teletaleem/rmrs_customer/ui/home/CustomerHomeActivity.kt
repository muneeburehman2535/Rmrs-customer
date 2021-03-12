package com.teletaleem.rmrs_customer.ui.home

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.ActivityCustomerHomeBinding
import com.teletaleem.rmrs_customer.ui.home.cart.CartFragment
import com.teletaleem.rmrs_customer.ui.home.favourite.FavouriteFragment
import com.teletaleem.rmrs_customer.ui.home.profile.ProfileFragment


class CustomerHomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var mBinding:ActivityCustomerHomeBinding
    lateinit var mNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_customer_home)

        setBottomNavigationView()
        setDrawableLayout()

    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun setDrawableLayout() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.cart,R.id.favourite,R.id.profile), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)

        actionBarDrawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.colorAccent)
    }

    private fun setBottomNavigationView() {
        mNavigation= findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        mNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            when (id) {
                R.id.dashboard -> {
                    //toolbar.setTitle(resources.getString(R.string.title_home))
                    loadFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favourite -> {
                    //toolbar.setTitle(resources.getString(R.string.title_category))
                    loadFragment(FavouriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.cart -> {
                    //toolbar.setTitle(resources.getString(R.string.title_notifications))
                    loadFragment(CartFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {

                    loadFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
                else -> true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.customer_home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /********************************************************************************************************************/
    //                                          Fragments Attachments
    /*******************************************************************************************************************/

//    fun attachBottomNavigationFragment(): Unit {
//        fragmentTransaction=supportFragmentManager.beginTransaction()
//        val galleryFragment= BottomNavigationFragment()
//        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        fragmentTransaction.replace(R.id.nav_host_fragment, galleryFragment, "bsf").addToBackStack("abc")
//        fragmentTransaction.commit()
//    }

    fun loadFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.nav_host_fragment, fragment!!)
        transaction.commit()
    }
}