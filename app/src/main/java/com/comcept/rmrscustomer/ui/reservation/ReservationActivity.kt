package com.comcept.rmrscustomer.ui.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.reservation.Reservation
import com.comcept.rmrscustomer.data_class.review.Review
import com.comcept.rmrscustomer.databinding.ActivityReservationBinding
import com.comcept.rmrscustomer.utilities.AppGlobal
import java.util.*

class ReservationActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private lateinit var txtToolbarText:TextView
    private lateinit var mBinding:ActivityReservationBinding
    private lateinit var mViewModel:ReservationViewModel
    private lateinit var mCalendar: Calendar
    private lateinit var progressDialog: KProgressHUD

    private var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        mViewModel=ViewModelProvider(this).get(ReservationViewModel::class.java)
        mBinding.reservationViewModel=mViewModel
        progressDialog=AppGlobal.setProgressDialog(this)

        setSupportActionBar(mBinding.toolbarReservation.findViewById(R.id.toolbar_reservation))

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        txtToolbarText=mBinding.toolbarReservation.findViewById(R.id.txt_title_toolbar)

        mBinding.edtxtReservationTimeAr.setOnClickListener(this)
        mBinding.btnReservationAr.setOnClickListener(this)
        txtToolbarText.setText(R.string.title_reservation)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.edtxt_reservation_time_ar->{

                val calendar: Calendar = Calendar.getInstance()
                //calendar.add(Calendar.DAY_OF_MONTH,1)
                day = calendar.get(Calendar.DAY_OF_MONTH)
                month = calendar.get(Calendar.MONTH)
                year = calendar.get(Calendar.YEAR)
                val datePickerDialog = DatePickerDialog(this, this, year, month,day)
                datePickerDialog.datePicker.minDate = System.currentTimeMillis()-1000
                datePickerDialog.show()
            }
            R.id.btn_reservation_ar->{
                if (checkCredentials())
                {
                    val reservation=Reservation(AppGlobal.readString(this,AppGlobal.restaurantId,"0")
                            ,AppGlobal.readString(this,AppGlobal.customerId,"0")
                            ,mBinding.edtxtCustomerNameAr.text.toString()
                            ,mBinding.edtxtPeopleNoAr.text.toString()
                            ,mBinding.edtxtCustomerPhnNoAr.text.toString()
                            ,Date(mCalendar.timeInMillis)
                            ,AppGlobal.readString(this,AppGlobal.restaurantName,""))

                    if (AppGlobal.isInternetAvailable(this))
                    {
                        addRestaurantDetail(reservation)
                    }
                    else{
                        AppGlobal.showDialog(getString(R.string.title_alert),getString(R.string.err_no_internet),this)
                    }
                }
            }
        }
    }

    private fun checkCredentials(): Boolean {
        var msg=""

        if (TextUtils.isEmpty(mBinding.edtxtCustomerNameAr.text.toString().trim()))
        {
            msg="${msg}Enter customer name. \n"
        }
        if (TextUtils.isEmpty(mBinding.edtxtCustomerPhnNoAr.text.toString().trim()))
        {
            msg="${msg}Enter customer mobile number. \n"
        }

        if (TextUtils.isEmpty(mBinding.edtxtPeopleNoAr.text.toString().trim()))
        {
            msg="${msg}Add number of peoples. \n"
        }

        if (TextUtils.isEmpty(mBinding.edtxtReservationTimeAr.text.toString().trim()))
        {
            msg="${msg}Select Reservation date & time. \n"
        }

        return if (msg=="")
        {
            true
        }
        else{
            AppGlobal.showDialog(getString(R.string.title_alert),msg,this)
            false
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this))

        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        //mBinding.edtxtReservationTimeAr.text = " myYear + "\n" + "Month: " + myMonth + "\n" + "Day: " + myDay + "\n" + "Hour: " + myHour + "\n" + "Minute: " + myMinute"
        mCalendar=Calendar.getInstance()
        mCalendar.set(myYear,myMonth,myDay,myHour,myMinute)
        if (mCalendar.timeInMillis>Calendar.getInstance().timeInMillis){
            mBinding.edtxtReservationTimeAr.setText(AppGlobal.dateToTimeStamp(Date(mCalendar.timeInMillis),"MMM dd, yyyy hh:mm a"))
        }
        else{
            AppGlobal.snackBar(mBinding.layoutParent,getString(R.string.err_greater_time),AppGlobal.LONG)
        }

    }

    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Reservation API Method
    * */
    private fun addRestaurantDetail(reservation: Reservation){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        mViewModel.getReviewResponse(reservation).observe(this, {
            progressDialog.dismiss()
            if (it.Message=="Success")
            {
                AppGlobal.showDialogWithCloseActivity(getString(R.string.title_alert),it.data.description,this)
            }
            else{
                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,this)
            }
        })
    }
}