package com.weather.info.util

import android.content.Context
import com.google.gson.Gson
import com.weather.info.util.Constant.Companion.AUTH_TOKEN
import com.weather.info.util.Constant.Companion.FCM_TOKEN
import com.weather.info.util.Constant.Companion.IS_USER_LOGIN
import com.weather.info.util.Constant.Companion.LANGUAGE_ENGLISH
import com.weather.info.util.Constant.Companion.LANGUAGE_KEY
import com.weather.info.util.Constant.Companion.PREF_INSTALLATION_ID
import com.weather.info.util.Constant.Companion.PREF_UNIQUE_ID
import com.weather.info.util.Constant.Companion.USER_EMAIL
import com.weather.info.util.Constant.Companion.USER_OBJECT
import com.weather.info.util.Constant.Companion.USER_PASSWORD
import javax.inject.Inject

/**
 * Handles Shared Preferences through out the App
 */
@Suppress("unused")
class PrefUtils @Inject constructor(context: Context) {
    /**
     * Object of [android.content.SharedPreferences]
     * */
    private val mPref = context.getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE)
    /**
     * this will hold the constant value
     * */
    private val isGalleryPermissionAlreadyAsked = "isGalleryPermissionAlreadyAsked"
    /**
     * this will hold the constant value
     * */
    private val isAudioPermissionAlreadyAsked = "isAudioPermissionAlreadyAsked"
    /**
     * this will hold the constant value
     * */
    private val isCameraPermissionAlreadyAsked = "isCameraPermissionAlreadyAsked"
    /**
     * this will hold the constant value
     * */
    private val isLocationPermissionAlreadyAsked = "isLocationPermissionAlreadyAsked"

    /**
     * Generic Method to get Boolean from SharedPreference
     * @param key -> Pass key in String format
     */
    private fun getBoolean(key: String): Boolean {
        return mPref.getBoolean(key, false)
    }

    /**
     * Generic Method to get String from SharedPreference
     * @param key -> Pass key in String format
     */
    private fun getString(key: String): String? {
        return mPref.getString(key, null)
    }

    /**
     * Generic Method to store String from SharedPreference
     * @param key -> Pass key in String format
     * @param value -> Pass value in String format
     */
    private fun putString(key: String, value: String?) {
        val mEditor = mPref.edit()
        mEditor.putString(key, value)
        mEditor.apply()
    }

    /**
     * Generic Method to store Int from SharedPreference
     * @param key -> Pass key in String format
     * @param value -> Pass value in String format
     */
    private fun putInt(key: String, value: Int) {
        val mEditor = mPref.edit()
        mEditor.putInt(key, value)
        mEditor.apply()
    }

    /**
     * Generic Method to get Int from SharedPreference
     * @param key -> Pass key in String format
     */
    private fun getInt(key: String): Int {
        return mPref.getInt(key, -1)
    }

    /**
     * Generic Method to store Boolean from SharedPreference
     * @param key -> Pass key in String format
     * @param b -> Pass value in String format
     */
    private fun putBoolean(key: String, b: Boolean) {
        val mEditor = mPref.edit()
        mEditor.putBoolean(key, b)
        mEditor.apply()
    }

    /**
     * Method to clear All Stored Preferences
     */
    fun clearAll() {
        val mEditor = mPref.edit()
        mEditor.clear()
        mEditor.apply()
    }

    /**
     * @return Authentication Token of logged in User
     */
    fun getAuthenticationToken(): String {
        return getString(AUTH_TOKEN) ?: ""
    }

    /**
     * @return Authentication UserId of logged in User
     */
//    fun getAuthUserId(): Int {
//        return if (getUser()?.id != null) {
//            getUser()?.id!!
//        } else 0
//    }

    /**
     * To check if Gallery permission is asked before
     */
    fun isGalleryPermissionAlreadyAsked(): Boolean {
        return getBoolean(isGalleryPermissionAlreadyAsked)
    }

    /**
     * To check if Audio permission is asked before
     */
    fun isAudioPermissionAlreadyAsked(): Boolean {
        return getBoolean(isAudioPermissionAlreadyAsked)
    }

    /**
     * To check if Camera permission is asked before
     */
    fun isCameraPermissionAlreadyAsked(): Boolean {
        return getBoolean(isCameraPermissionAlreadyAsked)
    }

    /**
     * To check if Camera permission is asked before
     */
    fun isLocationPermissionAlreadyAsked(): Boolean {
        return getBoolean(isLocationPermissionAlreadyAsked)
    }

    /**
     * To set Gallery permission is asked once
     */
    fun setGalleryPermissionAsked() {
        putBoolean(isGalleryPermissionAlreadyAsked, true)
    }

    /**
     * To set Location permission is asked once
     */
    fun setLocationPermissionAsked() {
        putBoolean(isLocationPermissionAlreadyAsked, true)
    }

    /**
     * To set Audio permission is asked once
     */
    fun setAudioPermissionAsked() {
        putBoolean(isAudioPermissionAlreadyAsked, true)
    }

    /**
     * To set Camera permission is asked once
     */
    fun setCameraPermissionAsked() {
        putBoolean(isCameraPermissionAlreadyAsked, true)
    }

    /**
     * To check if user is logged In or not
     */
    fun isUserLogin(): Boolean {
        return getBoolean(IS_USER_LOGIN)
    }

    /**
     * Set User in Logged in Mode
     */
    fun setIsLogin(b: Boolean) {
        putBoolean(IS_USER_LOGIN, b)
    }

    /**
     * This will store FCM token
     * @param deviceToken -> token to be stored
     * */
    fun setFcmToken(deviceToken: String) {
        val mEditor = mPref.edit()
        mEditor.putString(FCM_TOKEN, deviceToken)
        mEditor.apply()
    }

    /**
     * This will @return stored User Email
     * */
    fun getUserEmail(): String {
        return getString(USER_EMAIL) ?: ""
    }

    /**

     * This will store User Email
     * @param email -> email to be stored
     * */
    fun setUserEmail(email: String) {
        val mEditor = mPref.edit()
        mEditor.putString(USER_EMAIL, email)
        mEditor.apply()
    }

    /**
     * This will @return stored User password
     * */
    fun getUserPassword(): String {
        return getString(USER_PASSWORD) ?: ""
    }

    /**

     * This will store User Password
     * @param password -> password to be stored
     * */
    fun setUserPassword(password: String) {
        val mEditor = mPref.edit()
        mEditor.putString(USER_PASSWORD, password)
        mEditor.apply()
    }

    /**
     * This will @return stored FCM token
     * */
    fun getFcmToken(): String {
        return getString(FCM_TOKEN) ?: ""
    }

    /**
     * This will store deviceId
     * @param deviceId -> id to be stored
     * */
    fun setDeviceId(deviceId: String) {
        val mEditor = mPref.edit()
        mEditor.putString(PREF_UNIQUE_ID, deviceId)
        mEditor.apply()
    }

    /**
     * This will @return stored deviceId
     * */
    fun getDeviceId(): String {
        return getString(PREF_UNIQUE_ID) ?: ""
    }

    /**
     * This will store deviceId
     * @param installationId -> id to be stored
     * */
    fun setInstallationId(installationId: String) {
        val mEditor = mPref.edit()
        mEditor.putString(PREF_INSTALLATION_ID, installationId)
        mEditor.apply()
    }

    /**
     * This will @return stored installationId
     * */
    fun getInstallationId(): String {
        return getString(PREF_INSTALLATION_ID) ?: ""
    }

    /**
     * This will store device current Language
     * @param language -> id to be stored
     * */
    fun setLanguage(language: String) {
        val mEditor = mPref.edit()
        mEditor.putString(LANGUAGE_KEY, language)
        mEditor.apply()
    }

    /**
     * This will @return stored Language
     * */
    fun getLanguage(): String {
        return getString(LANGUAGE_KEY) ?: LANGUAGE_ENGLISH
    }

    /**
     * Generic Method to store Long from SharedPreference
     * @param key -> Pass key in String format
     * @param value -> Pass value in Long format
     */
    private fun putLong(key: String, value: Long) {
        val mEditor = mPref.edit()
        mEditor.putLong(key, value)
        mEditor.apply()
    }

    /**
     * Generic Method to get Long from SharedPreference
     * @param key -> Pass key in String format
     */
    private fun getLong(key: String): Long {
        return mPref.getLong(key, 0L)
    }

    /**
     * This will store Trip end alarm time
     * @param timeInMill -> timeInMilliseconds to be stored
     * */
    fun setTripEndAlarmTime(timeInMill: Long) {
        putLong(Constant.TRIP_END_ALARM_TIME, timeInMill)
    }

    /**
     * This will @return stored Trip end alarm time
     * */
    fun getTripEndAlarmTime(): Long {
        return getLong(Constant.TRIP_END_ALARM_TIME)
    }
}