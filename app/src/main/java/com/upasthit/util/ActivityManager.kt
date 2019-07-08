package com.upasthit.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

object ActivityManager {

    /**
     * To start normal activity
     */
    fun startActivity(a: Activity, c: Class<*>) {
        a.startActivity(Intent(a, c))
    }

    /**
     * To start activity for result
     */
    fun startActivityForResult(a: Activity, c: Class<*>, requestCode: Int) {
        a.startActivityForResult(Intent(a, c), requestCode)
    }

    /**
     * Finish existing work and start new activity
     */
    fun startFreshActivity(a: Activity, c: Class<*>) {
        a.apply {
            startActivity(Intent(a, c))
            finish()
        }

    }

    /**
     * To start new activity with passing data
     */
    fun startActivityWithBundle(activity: Activity, aClass: Class<*>, bundle: Bundle) {
        val intent = Intent(activity, aClass).putExtras(bundle)
        activity.startActivity(intent)
    }

    /**
     * To start activity for result with passing data
     */
    fun startActivityForResultWithBundle(activity: Activity, aClass: Class<*>, requestCode: Int, bundle: Bundle) {
        val intent = Intent(activity, aClass)
        intent.putExtras(bundle)
        activity.startActivityForResult(intent, requestCode)
    }

    /**
     * To start new activity and clear background activity stack
     */
    fun startFreshActivityClearStack(activity: Activity, aClass: Class<*>) {
        val intent = Intent(activity, aClass)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
        activity.finish()
    }

    /**
     * To start new activity and clear background activity stack with bundle
     */

    fun startFreshActivityClearStackWithBundle(activity: Activity, aClass: Class<*>, bundle: Bundle) {
        val intent = Intent(activity, aClass).putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
        activity.finish()
    }


}