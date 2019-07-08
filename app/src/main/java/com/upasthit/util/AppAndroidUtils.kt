package com.upasthit.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.upasthit.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AppAndroidUtils {

    // UTC - 2019-01-29T19:58:20.087+05:30
//    val UTCFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    val UTCFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    fun appInformationDlg(context: Context, title: String, msg: String) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    fun hideKeyboard(activity: Activity) {

        val view = activity.currentFocus
        if (view != null) {
            val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun hideKeyboardFragment(activity: Activity, view: View) {
        if (true) {
            val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun showKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    fun showLongToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun startFwdAnimation(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun startBackAnimation(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun getActivityWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getActivityHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getActivityWidthAndHeight(activity: Activity): IntArray {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return intArrayOf(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }

    fun getAppVersion(context: Context): Int {
        val manager = context.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(
                    context.packageName, 0
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return info!!.versionCode
    }

    /**
     * Making notification bar transparent
     */
    fun changeStatusBarColor(appCompatActivity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = appCompatActivity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun changeStatusBarColorBlack(appCompatActivity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = appCompatActivity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.BLACK
        }
    }

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun isTablet(context: Context): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun getDeviceId(context: Context): String {

        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isAndroidEmulator(): Boolean {
        val product = Build.PRODUCT
        var isEmulator = false
        if (product != null) {
            isEmulator = product == "sdk" || product.contains("_sdk") || product.contains("sdk_")
        }
        return isEmulator
    }

    fun getDeviceScreenHeight(_activity: Activity): Int {
        val display = _activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        return size.y
    }


    /*
      *   Date related functions
      * */

    fun checkDigit(number: Int): String {
        return if (number <= 9) "0$number" else number.toString()
    }

    fun getDateWithDashSeparator(day: Int, month: Int, year: Int): String {
        return "$year-${checkDigit(month)}-${checkDigit(day)}"
    }

    fun getDateMMMddYYYYformat(dateWithDashSeparator: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var sourceDate: Date? = null
        try {
            sourceDate = dateFormat.parse(dateWithDashSeparator);
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(sourceDate)
    }

    fun getDateWithDashSeparator(dateWithMMMddYYYYFormat: String): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        var sourceDate: Date? = null
        try {
            sourceDate = dateFormat.parse(dateWithMMMddYYYYFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(sourceDate)
    }

    fun getDateFromUTC(utcDateTime: String?): String {

        if (utcDateTime.isNullOrEmpty()) return ""

        var sourceDate: Date? = null
        try {
            sourceDate = UTCFormat.parse(utcDateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(sourceDate)
    }

    fun getDateObjectFromUTC(utcDateTime: String): Date? {

        var sourceDate: Date? = null
        try {
            sourceDate = UTCFormat.parse(utcDateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return sourceDate
    }

    fun getDateFromUTCyyyyMMdd(utcDateTime: String): String {
        var sourceDate: Date? = null
        try {
            sourceDate = UTCFormat.parse(utcDateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(sourceDate)
    }

    fun getUTCDateFromyyyyMMdd(utcDateTime: String): String {
        var sourceDate: Date? = null
        try {
            sourceDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(utcDateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return UTCFormat.format(sourceDate)
    }

    fun getDateFromUTCddMMyyyy(utcDateTime: String): String {
        var sourceDate: Date? = null
        try {
            sourceDate = UTCFormat.parse(utcDateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(sourceDate)
    }

    fun getTimeFromUTC(utcDateTime: String): String {
        var sourceDate: Date? = null
        try {
            sourceDate = UTCFormat.parse(utcDateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(sourceDate)
    }

    fun getTotalMonthsFromUTCdate(utcDate: String?): Int {
        var monthsVal = 0
        if (utcDate.isNullOrEmpty()) {
            return monthsVal
        }
        try {
            val currentDate = Date()
            val dob = UTCFormat.parse(utcDate)

            val startCalendar = Calendar.getInstance()
            startCalendar.time = dob
            val endCalendar = Calendar.getInstance()
            endCalendar.time = currentDate

            val diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
            monthsVal = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)

            //long difference = currentDate.getTime() - date_of_birth.getTime();
            //monthsVal = (int) (difference / (AVERAGE_MILLIS_PER_MONTH));
        } catch (e: ParseException) {
            e.printStackTrace()
            return monthsVal
        }

        return if (monthsVal == 0) 1 else monthsVal
    }
}
