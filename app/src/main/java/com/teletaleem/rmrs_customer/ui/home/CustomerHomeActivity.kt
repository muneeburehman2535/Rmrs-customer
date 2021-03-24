package com.teletaleem.rmrs_customer.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.shivtechs.maplocationpicker.LocationPickerActivity
import com.shivtechs.maplocationpicker.MapUtility
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.ActivityCustomerHomeBinding
import com.teletaleem.rmrs_customer.ui.home.cart.CartFragment
import com.teletaleem.rmrs_customer.ui.home.favourite.FavouriteFragment
import com.teletaleem.rmrs_customer.ui.home.profile.ProfileFragment
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import timber.log.Timber
import java.util.*


class CustomerHomeActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding:ActivityCustomerHomeBinding
    lateinit var mNavigation: BottomNavigationView
    private lateinit var drawerLayout:DrawerLayout
    private  lateinit var txtToolbarName:TextView
    private lateinit var mToolbarLayout:LinearLayout
    private lateinit var mToolbar:Toolbar
    private lateinit var locationMenu:MenuItem
    private var mCurrentLocation:String=""
    private val pLACE_PICKER_REQUEST = 4
    private lateinit var fields:List<Place.Field>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_customer_home)

        setBottomNavigationView()
        setDrawableLayout()
        loadFragment(HomeFragment())
        initializePlacePicker()

    }

    private fun initializePlacePicker() {
        MapUtility.apiKey = resources.getString(R.string.google_maps_api);
    }


    // Set Drawable Layout
    private fun setDrawableLayout() {
        mToolbar = findViewById(R.id.toolbar)
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        txtToolbarName=findViewById(R.id.txt_location_home)
        mToolbarLayout=findViewById(R.id.layout_home_toolbar)
        setSupportActionBar(mToolbar)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)

    }

    // Method to Set Bottom Navigation Layout
    private fun setBottomNavigationView() {
        mNavigation= findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        mNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashboard -> {
                    locationMenu.isVisible = true
                    mToolbarLayout.visibility = View.VISIBLE
                    txtToolbarName.text = mCurrentLocation
                    mToolbar.title = ""
                    replaceNewFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favourite -> {
                    locationMenu.isVisible = false
                    mToolbarLayout.visibility = View.GONE
                    mToolbar.title = getString(R.string.title_toolbar_favourite)
                    replaceNewFragment(FavouriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.cart -> {
                    locationMenu.isVisible = false
                    mToolbarLayout.visibility = View.GONE
                    mToolbar.title = getString(R.string.title_toolbar_cart)
                    replaceNewFragment(CartFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    locationMenu.isVisible = false
                    mToolbarLayout.visibility = View.GONE
                    mToolbar.title = getString(R.string.title_toolbar_profile)
                    replaceNewFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
                else -> true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.customer_home, menu)
        locationMenu=menu.findItem(R.id.action_location)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.action_location -> {
                checkPermissionForLocation(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*******************************************************************************************************************/
    //                                          Location Picker Section
    /*******************************************************************************************************************/

    private fun checkPermissionForLocation(accessFineLocation: String) {
        try {
            if (ContextCompat.checkSelfPermission(this, accessFineLocation) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(accessFineLocation), pLACE_PICKER_REQUEST)
            } else {
                pickLocation()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            pLACE_PICKER_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickLocation()
                } else {
                    AppGlobal.showToast("Location Permission Denied.", applicationContext)

                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
            }
        }
    }

    private fun pickLocation() {
        val intent = Intent(this, LocationPickerActivity::class.java)
        startActivityForResult(intent, pLACE_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pLACE_PICKER_REQUEST) {
            try {
                if (data?.getStringExtra(MapUtility.ADDRESS) != null) {
                    val currentLatitude: Double = data.getDoubleExtra(MapUtility.LATITUDE, 0.0)
                    val currentLongitude: Double = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0)
                    val completeAddress: Bundle? = data.getBundleExtra("fullAddress")
                    mCurrentLocation=completeAddress!!.getString("addressline2").toString()
                    txtToolbarName.text=mCurrentLocation
                    //Timber.d("Location: ${completeAddress!!.getString("addressline2")+"\t"+completeAddress.getString("city")}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace();
            }
        }
    }



    /********************************************************************************************************************/
    //                                          Fragments Attachments Section
    /*******************************************************************************************************************/

    private fun loadFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction()
        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .add(R.id.nav_host_fragment, fragment!!)
                .commit()
    }

    private fun replaceNewFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.nav_host_fragment, fragment!!)
        transaction.commit()
    }

    fun loadNewFragment(fragment: Fragment?,name:String) {
        locationMenu.isVisible = false
        mToolbarLayout.visibility = View.GONE
        mToolbar.title = getString(R.string.title_toolbar_restaurants)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.nav_host_fragment, fragment!!)
            .addToBackStack(name)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}