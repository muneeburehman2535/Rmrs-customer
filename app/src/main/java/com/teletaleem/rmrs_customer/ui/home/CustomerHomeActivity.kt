package com.teletaleem.rmrs_customer.ui.home

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.shivtechs.maplocationpicker.LocationPickerActivity
import com.shivtechs.maplocationpicker.MapUtility
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.databinding.ActivityCustomerHomeBinding
import com.teletaleem.rmrs_customer.db.CustomerDatabase
import com.teletaleem.rmrs_customer.shared_view_models.SharedViewModel
import com.teletaleem.rmrs_customer.ui.home.cart.CartFragment
import com.teletaleem.rmrs_customer.ui.home.favourite.FavouriteFragment
import com.teletaleem.rmrs_customer.ui.home.profile.ProfileFragment
import com.teletaleem.rmrs_customer.ui.myorders.MyOrdersFragment
import com.teletaleem.rmrs_customer.ui.reservation.myreservation.MyReservationsFragment
import com.teletaleem.rmrs_customer.ui.review.restaurantreviews.ReviewsListFragment
import com.teletaleem.rmrs_customer.ui.updatepassword.UpdatePasswordFragment
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class CustomerHomeActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding:ActivityCustomerHomeBinding
    private lateinit var mNavigation: BottomNavigationView
    private lateinit var drawerLayout:DrawerLayout
    lateinit var txtToolbarName:TextView
    private lateinit var mToolbarLayout:LinearLayout
    private lateinit var mToolbar:Toolbar
    private  var locationMenu:MenuItem?=null
    private var editProfileMenu:MenuItem?=null
    private lateinit var infoMenu:MenuItem
    private var mCurrentLocation:String=""
    private val pLACE_PICKER_REQUEST = 4
    private lateinit var fields:List<Place.Field>
    lateinit var mModel:SharedViewModel
    private lateinit var  databaseCreator: CustomerDatabase
    private  var restaurantId:String="0"
    var locationManager: LocationManager? = null
    var latitude: String? = null
    var longitude:String? = null
    private val REQUEST_LOCATION = 1
    var mContext=this
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_customer_home)
        mModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        databaseCreator= CustomerDatabase.getInstance(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        setBottomNavigationView()
        setDrawableLayout()
        loadFragment(HomeFragment())
        initializePlacePicker()
        //getRestaurantId()

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
        mBinding.navView.setNavigationItemSelectedListener(this)

    }

    // Method to Set Bottom Navigation Layout
    private fun setBottomNavigationView() {
        mNavigation= findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        mNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashboard -> {

                    txtToolbarName.text = mCurrentLocation
                    setToolbarTitle("", HomeFragment(), false, View.VISIBLE, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favourite -> {
//                    locationMenu.isVisible = false
//                    mToolbarLayout.visibility = View.GONE
//                    mToolbar.title = getString(R.string.title_toolbar_favourite)
//                    editProfileMenu.isVisible = false
//                    replaceNewFragment(FavouriteFragment())
                    setToolbarTitle(getString(R.string.title_toolbar_favourite), FavouriteFragment(), false, View.GONE, false)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.cart -> {
//                    locationMenu.isVisible = false
//                    mToolbarLayout.visibility = View.GONE
//                    mToolbar.title = getString(R.string.title_toolbar_cart)
//                    editProfileMenu.isVisible = false
//                    replaceNewFragment(CartFragment())
                    setToolbarTitle(getString(R.string.title_toolbar_cart), CartFragment(), false, View.GONE, false)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
//                    locationMenu.isVisible = false
//                    mToolbarLayout.visibility = View.GONE
//                    mToolbar.title = getString(R.string.title_toolbar_profile)
//                    replaceNewFragment(ProfileFragment())

                    setToolbarTitle(getString(R.string.title_toolbar_profile), ProfileFragment(), true, View.GONE, false)
                    return@OnNavigationItemSelectedListener true
                }
                else -> true
            }
        })
    }
    private fun setToolbarTitle(title: String, fragment: Fragment, isProfileMenuVisible: Boolean, toolbarVisibility: Int, locationVisibility: Boolean){
        locationMenu?.isVisible = locationVisibility
        mToolbarLayout.visibility = toolbarVisibility
        mToolbar.title =title
        editProfileMenu?.isVisible = isProfileMenuVisible
        replaceNewFragment(fragment)
    }

    fun updateToolbarTitle(title: String, isProfileMenuVisible: Boolean, toolbarVisibility: Int, locationVisibility: Boolean){
        locationMenu?.isVisible = locationVisibility
        mToolbarLayout.visibility = toolbarVisibility
        mToolbar.title =title
        editProfileMenu?.isVisible = isProfileMenuVisible
    }

    fun updateToolbarAddress(){
        txtToolbarName.text=mCurrentLocation
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.customer_home, menu)
        locationMenu=menu.findItem(R.id.action_location)
        editProfileMenu=menu.findItem(R.id.action_edit)
        infoMenu=menu.findItem(R.id.action_info)
        val spanString = SpannableString(editProfileMenu?.title.toString())
        spanString.setSpan(ForegroundColorSpan(getColor(R.color.colorAccent)), 0, spanString.length, 0)
        editProfileMenu?.title = spanString
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.action_location -> {
                checkPermissionForLocation(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            R.id.action_edit -> {

                mModel.updateEditProfileData(true)
            }
            R.id.action_info -> {
                changeToolbarName(getString(R.string.title_reviews), isProfileMenuVisible = false, locationVisibility = false)
                loadNewFragment(
                        ReviewsListFragment(),
                        "review_list"
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateBottomNavigationCount(quantity: Int)
    {
        mNavigation.getOrCreateBadge(R.id.cart).number=quantity
    }

    fun changeToolbarName(toolbarName: String, isProfileMenuVisible: Boolean, locationVisibility: Boolean)
    {
       //Toolbar.title=""
        mToolbar.title = toolbarName
        if (locationMenu!=null&&editProfileMenu!=null){
            locationMenu?.isVisible = locationVisibility
            editProfileMenu?.isVisible = isProfileMenuVisible
        }

    }

    private fun getCartRecord(restaurantId: String) {
        val cartLiveData=databaseCreator.cartDao.fetch(restaurantId)
        cartLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val cartArray = it as ArrayList<Cart>
                updateBottomNavigationCount(cartArray.size)
            }
            cartLiveData.removeObservers(this)
        })

    }

    private fun getRestaurantId(){

        restaurantId=AppGlobal.readString(this, AppGlobal.restaurantId, "0")
        getCartRecord(restaurantId)
    }

    override fun onResume() {
        super.onResume()
         locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS()
        } else {
            getLocation()
        }
    }

    /**************************************************************************************************************************/
    //                                          Get Location Section
    /**************************************************************************************************************************/

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        } else {
            //val locationGPS: Location? = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            if (locationGPS != null) {
//                val lat: Double = locationGPS.getLatitude()
//                val longi: Double = locationGPS.getLongitude()
//                latitude = lat.toString()
//                longitude = longi.toString()
//                getAddress(latitude!!.toDouble(), longitude!!.toDouble())

//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
//            }
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        if (location!=null){
                            getAddress(location.latitude,location.longitude)
                        }
                        // Got last known location. In some rare situations this can be null.
                    }

        }
    }

    private fun onGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        val addresses: List<Address>
        val geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(latitude, longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        val address: String = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city: String = addresses[0].locality
        val state: String = addresses[0].adminArea
        val country: String = addresses[0].countryName
        val postalCode: String = addresses[0].postalCode
        val knownName: String = addresses[0].featureName
    }

    /*******************************************************************************************************************/
    //                                          Location Picker Section
    /*******************************************************************************************************************/

    private fun checkPermissionForLocation(accessFineLocation: String) {
        try {
            if (ContextCompat.checkSelfPermission(this, accessFineLocation) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(accessFineLocation),
                        pLACE_PICKER_REQUEST
                )
            } else {
                pickLocation()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray,
    ) {
        when (requestCode) {
            pLACE_PICKER_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickLocation()
                } else {
                    AppGlobal.showToast("Location Permission Denied.", applicationContext)

                }
            }
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentAddress()
                    getLocation()
                } else {
                    AppGlobal.showToast("Location Permission Denied.", applicationContext)

                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
            }
        }
    }

    private fun getCurrentAddress() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
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
        transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        transaction.replace(R.id.nav_host_fragment, fragment!!)
        transaction.commit()
    }

    fun loadNewFragment(fragment: Fragment?, name: String) {
        infoMenu.isVisible = name=="restaurant_detail"
        locationMenu?.isVisible = false
        mToolbarLayout.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.nav_host_fragment, fragment!!)
            .addToBackStack(name)
            .commit()
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.nav_home -> {
//                locationMenu.isVisible = true
//                mToolbarLayout.visibility = View.VISIBLE
                txtToolbarName.text = mCurrentLocation
//                mToolbar.title = ""
//                replaceNewFragment(HomeFragment())
//                editProfileMenu.isVisible = false
                setToolbarTitle("", HomeFragment(), false, View.VISIBLE, true)
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_cart -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_toolbar_cart)
//                editProfileMenu.isVisible = false
//                replaceNewFragment(CartFragment())
                setToolbarTitle(getString(R.string.title_toolbar_cart), CartFragment(), false, View.GONE, false)
                return true
            }
            R.id.nav_my_orders -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_my_orders)
//                editProfileMenu.isVisible = false
//                replaceNewFragment(MyOrdersFragment())
                setToolbarTitle(getString(R.string.title_my_orders), MyOrdersFragment(), false, View.GONE, false)
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_reservations -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_my_reservations)
//                editProfileMenu.isVisible = false
//                replaceNewFragment(MyReservationsFragment())
                setToolbarTitle(getString(R.string.title_my_reservations), MyReservationsFragment(), false, View.GONE, false)
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_profile -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_toolbar_profile)
//                editProfileMenu.isVisible = true
//                replaceNewFragment(ProfileFragment())

                setToolbarTitle(getString(R.string.title_toolbar_profile), ProfileFragment(), true, View.GONE, false)
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_update_password -> {
                locationMenu?.isVisible = false
                mToolbarLayout.visibility = View.GONE
                mToolbar.title = getString(R.string.title_toolbar_update_password)
                editProfileMenu?.isVisible = false
                replaceNewFragment(UpdatePasswordFragment())
                setToolbarTitle(getString(R.string.title_toolbar_update_password), UpdatePasswordFragment(), true, View.GONE, false)

                mBinding.drawerLayout.closeDrawers()
                return true
            }

            R.id.nav_logout -> {
                this.finish()
                return true
            }
            else-> false
        }
    }

    companion object{
        val mContext=this
    }


}