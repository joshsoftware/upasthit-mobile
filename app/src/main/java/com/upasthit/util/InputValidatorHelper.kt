package com.upasthit.util

import android.text.TextUtils


object InputValidatorHelper {

    /**
     * To validate email address
     */
    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    /**
     * To validate string is numeric
     */
    fun isNumeric(string: String): Boolean {
        return TextUtils.isDigitsOnly(string)
    }

    /**
     * To validate string is alpha betic
     */
    fun isAlphabetic(string: String): Boolean {
        return (string.matches(Regex("^[a-zA-Z ]+$")))
    }

    /**
     * To validate min length
     */
    fun isMinLengthGreaterThan(phoneNo: String?, length: Int): Boolean {
        return phoneNo != null && phoneNo.length > length
    }

    /**
     * To validate Phone number
     */
    fun isValidPhoneNumber(phone: String): Boolean {
        return !phone.isEmpty() && isNumeric(phone) && phone.length >= 10
    }

    /**
     * To validate OTP
     */
    fun isValidOtp(otp: String): Boolean {
        return !otp.isEmpty() && isNumeric(otp) && otp.length == 6
    }

    /**
     * To validate postal code
     */
    fun isValidPostalCode(postalCode: String): Boolean {
        return isNumeric(postalCode) && postalCode.length == 6
    }

    fun isValidPassword(password: String): Boolean {
        return !password.isEmpty() && password.length >= 8
    }

    fun isPasswordAlphaNumeric(password: String): Boolean {
        return (password.matches(Regex("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{5,15})$")))
        //return (password.matches(Regex("[a-zA-Z0-9]+")))
    }

}