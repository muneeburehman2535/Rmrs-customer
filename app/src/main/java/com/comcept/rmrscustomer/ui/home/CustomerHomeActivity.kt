package com.comcept.rmrscustomer.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.comcept.rmrscustomer.BuildConfig
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.kaopiz.kprogresshud.KProgressHUD
import com.shivtechs.maplocationpicker.LocationPickerActivity
import com.shivtechs.maplocationpicker.MapUtility
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.cart.Cart
import com.comcept.rmrscustomer.data_class.fcm.FcmNotification
import com.comcept.rmrscustomer.databinding.ActivityCustomerHomeBinding
import com.comcept.rmrscustomer.db.CustomerDatabase
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.shared_view_models.SharedViewModel
import com.comcept.rmrscustomer.ui.home.cart.CartFragment
import com.comcept.rmrscustomer.ui.home.favourite.FavouriteFragment
import com.comcept.rmrscustomer.ui.home.profile.ProfileFragment
import com.comcept.rmrscustomer.ui.home.verifyInvoice.VerifyInvoiceFragment
import com.comcept.rmrscustomer.ui.login.LoginActivity
import com.comcept.rmrscustomer.ui.myorders.MyOrdersFragment
import com.comcept.rmrscustomer.ui.reservation.myreservation.MyReservationsFragment
import com.comcept.rmrscustomer.ui.review.restaurantreviews.ReviewsListFragment
import com.comcept.rmrscustomer.ui.updatepassword.UpdatePasswordFragment
import com.comcept.rmrscustomer.utilities.AlertUtils
import com.comcept.rmrscustomer.utilities.AppGlobal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import timber.log.Timber
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class CustomerHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActivityCustomerHomeBinding
    private lateinit var mViewModel: CustomerHomeViewModel
    private lateinit var mNavigation: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    lateinit var txtToolbarName: TextView
    private lateinit var mToolbarLayout: LinearLayout
    private lateinit var mToolbar: Toolbar
    private var locationMenu: MenuItem? = null
    var editProfileMenu: MenuItem? = null
    lateinit var infoMenu: MenuItem
    var mCurrentLocation: String = ""
    private val pLACE_PICKER_REQUEST = 4
    private lateinit var fields: List<Place.Field>
    lateinit var mModel: SharedViewModel
    private lateinit var databaseCreator: CustomerDatabase
    private var restaurantId: String = "0"
    var locationManager: LocationManager? = null
    var latitude: String? = null
    var longitude: String? = null
    private val REQUEST_LOCATION = 1
    var mContext = this
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var progressDialog: KProgressHUD
    var deviceName = Build.MODEL
    var deviceId = Build.ID
    private lateinit var txtLuckyDrawPoints: TextView
    private lateinit var txtCustomerName: TextView

    var showUpdate = MutableLiveData<Boolean>().apply { setValue(false) }

    var mLatitude: Double = 0.0
    var mLongitude: Double = 0.0


    val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_home)
        mModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        mViewModel = ViewModelProvider(this).get(CustomerHomeViewModel::class.java)
        databaseCreator = CustomerDatabase.getInstance(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        progressDialog = AppGlobal.setProgressDialog(this)


        val headerView: View = mBinding.navView.getHeaderView(0)
        txtLuckyDrawPoints = headerView.findViewById(R.id.txt_lucky_draw_points_home)
        txtCustomerName = headerView.findViewById(R.id.txt_customer_name_home)
        txtCustomerName.text = AppGlobal.readString(this, AppGlobal.customerName, "")
        val deviceID = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        Timber.d("Device Name: ${deviceName} \n Device Id: ${deviceId} \nSecond Device Id: ${deviceID}")





        setBottomNavigationView()
        setDrawableLayout()
        loadFragment(HomeFragment())
        initializePlacePicker()

        getVersionUpdate()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS()
        } else {
            getLocation()
        }
        //getRestaurantId()

        //setSummaryFragment();

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("ERROR:", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val newToken = task.result
            Timber.d("Token: $newToken")
            val fcmNotification = FcmNotification(
                AppGlobal.readString(
                    this,
                    AppGlobal.customerId,
                    "0"
                ), "Android", newToken, deviceID
            )
            updateFcmToken(fcmNotification)

        })


        mBinding.versionNumber.text = "Version: ${BuildConfig.VERSION_NAME}"


        showUpdate.observe(this, { value ->


            if (value) {
                AlertUtils.showAlertDialogUpdate(
                    this,
                    "Update!",
                    "We have updated our app with new features and important bug fixes. Please click on the Update button below."
                ) { _, _ ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppGlobal.APP_URL)));
                }
            }
        })

    }

    private fun initializePlacePicker() {
        MapUtility.apiKey = resources.getString(R.string.google_maps_api);
    }


    // Set Drawable Layout
    private fun setDrawableLayout() {
        mToolbar = findViewById(R.id.toolbar)
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        txtToolbarName = findViewById(R.id.txt_location_home)
        mToolbarLayout = findViewById(R.id.layout_home_toolbar)
        setSupportActionBar(mToolbar)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
        mBinding.navView.setNavigationItemSelectedListener(this)

    }

    // Method to Set Bottom Navigation Layout
    private fun setBottomNavigationView() {
        mNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        mNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashboard -> {

                    txtToolbarName.text = mCurrentLocation
                    //getCallerFragment()
                    setToolbarTitle(
                        "",
                        HomeFragment(),
                        false,
                        View.VISIBLE,
                        true,
                        isMenuVisibility = false
                    )

//                    locationMenu?.isVisible = true
//                    mToolbarLayout.visibility = View.VISIBLE
//                    mToolbar.title =title
//                    editProfileMenu?.isVisible = false
//                    loadFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favourite -> {
//                    locationMenu.isVisible = false
//                    mToolbarLayout.visibility = View.GONE
//                    mToolbar.title = getString(R.string.title_toolbar_favourite)
//                    editProfileMenu.isVisible = false
//                    replaceNewFragment(FavouriteFragment())
                    setToolbarTitle(
                        getString(R.string.title_toolbar_favourite),
                        FavouriteFragment(),
                        false,
                        View.GONE,
                        false, isMenuVisibility = false
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.cart -> {
//                    locationMenu.isVisible = false
//                    mToolbarLayout.visibility = View.GONE
//                    mToolbar.title = getString(R.string.title_toolbar_cart)
//                    editProfileMenu.isVisible = false
//                    replaceNewFragment(CartFragment())
                    setToolbarTitle(
                        getString(R.string.title_toolbar_cart),
                        CartFragment(),
                        false,
                        View.GONE,
                        false, isMenuVisibility = false
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
//                    locationMenu.isVisible = false
//                    mToolbarLayout.visibility = View.GONE
//                    mToolbar.title = getString(R.string.title_toolbar_profile)
//                    replaceNewFragment(ProfileFragment())

                    setToolbarTitle(
                        getString(R.string.title_toolbar_profile),
                        ProfileFragment(),
                        true,
                        View.GONE,
                        false,
                        isMenuVisibility = false
                    )
                    return@OnNavigationItemSelectedListener true
                }
                else -> true
            }
        })
    }

    fun setToolbarTitle(
        title: String,
        fragment: Fragment,
        isProfileMenuVisible: Boolean,
        toolbarVisibility: Int,
        locationVisibility: Boolean,
        isMenuVisibility: Boolean
    ) {
        locationMenu?.isVisible = locationVisibility
        mToolbarLayout.visibility = toolbarVisibility
        infoMenu.isVisible = isMenuVisibility
        mToolbar.title = title
        editProfileMenu?.isVisible = isProfileMenuVisible
        replaceNewFragment(fragment)
    }

    fun setHomeToolbarTitle(
        title: String,
        isProfileMenuVisible: Boolean,
        toolbarVisibility: Int,
        locationVisibility: Boolean,
        isMenuVisibility: Boolean
    ) {
        locationMenu?.isVisible = locationVisibility
        mToolbarLayout.visibility = toolbarVisibility
        infoMenu.isVisible = isMenuVisibility
        mToolbar.title = title
        editProfileMenu?.isVisible = isProfileMenuVisible

    }

    fun updateToolbarTitle(
        title: String,
        isProfileMenuVisible: Boolean,
        toolbarVisibility: Int,
        locationVisibility: Boolean
    ) {
        locationMenu?.isVisible = locationVisibility
        mToolbarLayout.visibility = toolbarVisibility
        mToolbar.title = title
        editProfileMenu?.isVisible = isProfileMenuVisible
    }

    fun updateToolbarAddress() {
        mToolbarLayout.visibility = View.VISIBLE
        txtToolbarName.text = mCurrentLocation
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.customer_home, menu)
        locationMenu = menu.findItem(R.id.action_location)
        editProfileMenu = menu.findItem(R.id.action_edit)
        infoMenu = menu.findItem(R.id.action_info)
        val spanString = SpannableString(editProfileMenu?.title.toString())
        spanString.setSpan(
            ForegroundColorSpan(getColor(R.color.colorAccent)),
            0,
            spanString.length,
            0
        )
        editProfileMenu?.title = spanString
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.action_location -> {
                checkPermissionForLocation(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            R.id.action_edit -> {

                mModel.updateEditProfileData(true)
            }
            R.id.action_info -> {
//                changeToolbarName(
//                    getString(R.string.title_reviews),
//                    isProfileMenuVisible = false,
//                    locationVisibility = false
//                )
                loadNewFragment(
                    ReviewsListFragment(),
                    "review_list"
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateBottomNavigationCount(quantity: Int) {
        mNavigation.getOrCreateBadge(R.id.cart).number = quantity
    }

    fun changeToolbarName(
        toolbarName: String,
        isProfileMenuVisible: Boolean,
        locationVisibility: Boolean,
        isMenuVisibility: Boolean
    ) {
        //Toolbar.title=""
        mToolbar.title = toolbarName
        if (locationMenu != null && editProfileMenu != null) {
            locationMenu?.isVisible = locationVisibility
            editProfileMenu?.isVisible = isProfileMenuVisible
        }

        infoMenu.isVisible = isMenuVisibility


    }

    private fun getCartRecord(restaurantId: String) {
        val cartLiveData = databaseCreator.cartDao.fetch(restaurantId)
        cartLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val cartArray = it as ArrayList<Cart>
                updateBottomNavigationCount(cartArray.size)
            }
            cartLiveData.removeObservers(this)
        })

    }

    private fun getRestaurantId() {

        restaurantId = AppGlobal.readString(this, AppGlobal.restaurantId, "0")
        getCartRecord(restaurantId)
    }


    /**************************************************************************************************************************/
    //                                          Get Location Section
    /**************************************************************************************************************************/

    @SuppressLint("MissingPermission")
    private fun getLocation() {

        uiScope.launch {


            if (ActivityCompat.checkSelfPermission(
                    this@CustomerHomeActivity, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@CustomerHomeActivity, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@CustomerHomeActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION
                )
            } else {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            getAddress(location.latitude, location.longitude)
                            mLatitude = location.latitude
                            mLongitude = location.longitude
                            mModel.updateLatitude(mLatitude)
                            mModel.updateLongitude(mLongitude)

                        }
                        // Got last known location. In some rare situations this can be null.
                    }

            }

        }


    }

    private fun onGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, which ->
                startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }).setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun getAddress(latitude: Double, longitude: Double) {

        uiScope.launch{


            val addresses: List<Address>
            val geocoder = Geocoder(this@CustomerHomeActivity, Locale.getDefault())

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1)
                val address: String =
                    addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                AppGlobal.showToast(address, this@CustomerHomeActivity)
                mCurrentLocation = address
                txtToolbarName.text = mCurrentLocation
                AppGlobal.writeString(this@CustomerHomeActivity, AppGlobal.customerAddress, mCurrentLocation)
            }
            catch (e:IOException){
                when{
                    e.message == "grpc failed" ->{
                        Toast.makeText(this@CustomerHomeActivity, "GRPC Failed", Toast.LENGTH_SHORT).show()
                    }
                    else -> throw e
                }
            }




        }

    }

    /*******************************************************************************************************************/
    //                                          Location Picker Section
    /*******************************************************************************************************************/

    private fun checkPermissionForLocation(accessFineLocation: String) {
        try {
            if (ContextCompat.checkSelfPermission(
                    this,
                    accessFineLocation
                ) != PackageManager.PERMISSION_GRANTED
            ) {
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
                    AppGlobal.showDialog("Location Alert!","Please Enable Your Location for better Results",this)

                }
            }
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentAddress()
                    getLocation()
                } else {
                    AppGlobal.showToast("Location Permission Denied.", applicationContext)
                    AppGlobal.showDialog("Location Alert!","Please Enable Your Location for better Results",this)

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
                    mCurrentLocation = completeAddress!!.getString("addressline2").toString()


                    mLatitude = currentLatitude
                    mLongitude = currentLongitude
                    mModel.updateLatitude(mLatitude)
                    mModel.updateLongitude(mLongitude)


                    txtToolbarName.text = mCurrentLocation
                    AppGlobal.writeString(this, AppGlobal.customerAddress, mCurrentLocation)
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


//        infoMenu.isVisible = name=="restaurant_detail"
        locationMenu?.isVisible = false
        mToolbarLayout.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.nav_host_fragment, fragment!!)
            .addToBackStack(name)
            .commit()
    }

    fun removeFragment() {
        supportFragmentManager.popBackStack()
    }

    fun getCallerFragment() {
        val fm: FragmentManager = supportFragmentManager

        for (entry in 0 until fm.backStackEntryCount) {
            Timber.d("Found fragment: " + fm.getBackStackEntryAt(entry).name)
            fm.popBackStack()
        }

    }

    fun getNewCallerFragment() {
        val fm: FragmentManager = supportFragmentManager

        for (entry in 0 until fm.backStackEntryCount) {
            Timber.d("Found fragment: " + fm.getBackStackEntryAt(entry).name)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_home -> {
//                locationMenu.isVisible = true
//                mToolbarLayout.visibility = View.VISIBLE
                txtToolbarName.text = mCurrentLocation
//                mToolbar.title = ""
//                replaceNewFragment(HomeFragment())
//                editProfileMenu.isVisible = false
                setToolbarTitle(
                    "",
                    HomeFragment(),
                    false,
                    View.VISIBLE,
                    true,
                    isMenuVisibility = false
                )
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_cart -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_toolbar_cart)
//                editProfileMenu.isVisible = false
//                replaceNewFragment(CartFragment())
                setToolbarTitle(
                    getString(R.string.title_toolbar_cart),
                    CartFragment(),
                    false,
                    View.GONE,
                    false,
                    isMenuVisibility = false
                )
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_my_orders -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_my_orders)
//                editProfileMenu.isVisible = false
//                replaceNewFragment(MyOrdersFragment())
                setToolbarTitle(
                    getString(R.string.title_my_orders),
                    MyOrdersFragment(),
                    false,
                    View.GONE,
                    false,
                    isMenuVisibility = false
                )
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_reservations -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_my_reservations)
//                editProfileMenu.isVisible = false
//                replaceNewFragment(MyReservationsFragment())
                setToolbarTitle(
                    getString(R.string.title_my_reservations),
                    MyReservationsFragment(),
                    false,
                    View.GONE,
                    false,
                    isMenuVisibility = false
                )
                mBinding.drawerLayout.closeDrawers()
                return true
            }

            R.id.nav_favourite -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_my_reservations)
//                editProfileMenu.isVisible = false
//                replaceNewFragment(MyReservationsFragment())
                setToolbarTitle(
                    getString(R.string.title_favourite),
                    FavouriteFragment(),
                    false,
                    View.GONE,
                    false,
                    isMenuVisibility = false
                )
                mBinding.drawerLayout.closeDrawers()
                return true
            }


            R.id.nav_profile -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_toolbar_profile)
//                editProfileMenu.isVisible = true
//                replaceNewFragment(ProfileFragment())

                setToolbarTitle(
                    getString(R.string.title_toolbar_profile),
                    ProfileFragment(),
                    true,
                    View.GONE,
                    false,
                    isMenuVisibility = false
                )
                mBinding.drawerLayout.closeDrawers()
                return true
            }

            R.id.nav_verify -> {
//                locationMenu.isVisible = false
//                mToolbarLayout.visibility = View.GONE
//                mToolbar.title = getString(R.string.title_toolbar_profile)
//                editProfileMenu.isVisible = true
//                replaceNewFragment(ProfileFragment())

                setToolbarTitle(
                    "Verify Invoice",
                    VerifyInvoiceFragment(),
                    false,
                    View.GONE,
                    false,
                    isMenuVisibility = false
                )
                mBinding.drawerLayout.closeDrawers()
                return true
            }
            R.id.nav_update_password -> {
                locationMenu?.isVisible = false
                mToolbarLayout.visibility = View.GONE
                mToolbar.title = getString(R.string.title_toolbar_update_password)
                editProfileMenu?.isVisible = false
                replaceNewFragment(UpdatePasswordFragment())
                setToolbarTitle(
                    getString(R.string.title_toolbar_update_password),
                    UpdatePasswordFragment(),
                    false,
                    View.GONE,
                    false,
                    isMenuVisibility = false
                )

                mBinding.drawerLayout.closeDrawers()
                return true
            }

            R.id.nav_logout -> {
                AppGlobal.writeString(this, AppGlobal.tokenId, "0")
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
                return true
            }
            else -> false
        }
    }

    companion object {
        val mContext = this
    }

    /**************************************************************************************************************************/
    //                                          API's Calling Section
    /**************************************************************************************************************************/

    /*
   * Get Categories API Method
   * */
    private fun updateFcmToken(fcmNotification: FcmNotification) {


        mViewModel.updateFcmTokenResponse(fcmNotification).observe(this, {


            when(it){

                is Response.Loading ->{
                    progressDialog.setLabel("Please Wait")
                    progressDialog.show()

                }

                is Response.Success ->{


                    it.data?.let {

                        progressDialog.dismiss()
                        if (it != null && it.Message == "Success") {
                            Timber.d("Updated Token: ${it.data.result.Token}")

                        }
                    }

                }

                is Response.Error ->{
                    AppGlobal.showDialog(getString(R.string.title_alert),it.message.toString(),this)

                    if (progressDialog.isShowing) {

                        progressDialog.dismiss()

                    }
                }


            }



        })
    }

    fun getLuckyDrawPoints(customerID: String) {


        uiScope.launch {

            if (progressDialog.isShowing){
                progressDialog.dismiss()

            }


            mViewModel.getLuckyDrawPointsResponse(customerID).observe(this@CustomerHomeActivity, {


                when(it){


                    is Response.Loading ->{
                        progressDialog.setLabel("Please Wait")
                        progressDialog.show()
                    }


                    is Response.Success ->{


                        it.data?.let {

                            progressDialog.dismiss()
                            if (it != null && it.Message == "Success") {
                                Timber.d("Updated Token: ${it.data.LuckyDrawPoints}")
                                txtLuckyDrawPoints.text =
                                    "${getString(R.string.title_lucky_points)} ${AppGlobal.roundTwoPlaces(it.data.LuckyDrawPoints.toDouble())} "
                            }

                        }

                    }

                    is Response.Error ->{
                        AppGlobal.showDialog(getString(R.string.title_alert),it.message.toString(),this@CustomerHomeActivity)
                        if (progressDialog.isShowing) {

                            progressDialog.dismiss()

                        }

                    }

                }

            })
        }




    }




    override fun onBackPressed() {




        if (supportFragmentManager.backStackEntryCount == 0) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ -> super.onBackPressed() }
                .setNegativeButton(
                    "No"
                ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()

        } else {

            super.onBackPressed()

        }

    }


    private fun getVersionUpdate(): Unit {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            //Background work here

            var onlineVersion: String? = null
            try {
                onlineVersion =
                    Jsoup.connect(AppGlobal.APP_URL)
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")[7]
                        .ownText()
            } catch (e: Exception) {
            }
            //onlineVersion="1"
            onlineVersion?.let { Log.d("newVersion__", it) }
            val versionName = BuildConfig.VERSION_NAME
            Log.d("update", "Current version " + versionName + "playstore version " + onlineVersion)
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
//                if (Double.valueOf(versionName) < Double.valueOf(onlineVersion)) {
//                    //show dialog
//                }

                val splititem= onlineVersion.split(".")

                var   onlineversionCode= splititem[2]

                if (BuildConfig.VERSION_CODE >= onlineversionCode.toInt()) {
                    //Toast.makeText(context, "not", Toast.LENGTH_SHORT).show();

//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//                    builder1.setTitle("Alert!");
//                    builder1.setMessage("There is new application on play store of Version "+onlineVersion+". Current version is "+versionName);
//                    builder1.setCancelable(false);
//
//                    builder1.setPositiveButton(
//                            "Update",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.onsitedetail.employee")));
//                                    dialog.cancel();
//                                }
//                            });
//
//                    builder1.setNegativeButton(
//                            "Cancel",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
                } else {
                    handler.post {



                        showUpdate.value = true
                    }
                    //Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }




}