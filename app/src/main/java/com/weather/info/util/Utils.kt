package com.weather.info.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.text.TextUtils.isEmpty
import android.util.Base64
import android.util.Patterns
import android.webkit.MimeTypeMap
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Utility class having all the common used methods
 * */
@Suppress("unused")
class Utils {

    companion object {
        /**
         * This will @return difference between in hours
         * @param unixEndTime and
         * @param unixStartTime
         * */
        fun findHourDiff(unixStartTime: Long, unixEndTime: Long): Long {
            val startDate = createDate(unixStartTime)
            val date = createDate(unixEndTime)
            var diff = date.time - startDate.time

            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60

            val elapsedHours = diff / hoursInMilli
            diff %= hoursInMilli

            return elapsedHours
        }

        /**
         * This will @return difference between in days
         * @param unixStartTime and
         * @param unixEndTime
         * */
        fun findDaysDiff(unixStartTime: Date, unixEndTime: Date): Long {
            return TimeUnit.DAYS.convert(
                    SimpleDateFormat("dd-MM-yy", Locale.getDefault()).parse(
                            SimpleDateFormat(
                                    "dd-MM-yy",
                                    Locale.getDefault()
                            ).format(unixEndTime)
                    )?.time!! - SimpleDateFormat(
                            "dd-MM-yy",
                            Locale.getDefault()
                    ).parse(
                            SimpleDateFormat(
                                    "dd-MM-yy",
                                    Locale.getDefault()
                            ).format(unixStartTime)
                    )?.time!!, TimeUnit.MILLISECONDS
            )
        }

        /**
         * This method will convert date to String in given Format
         * @param objDate -> Date that need to be converted
         * @param parseFormat -> format in which string of date needed
         * */
        fun convertDateToString(objDate: Date, parseFormat: String): String {
            return try {
                SimpleDateFormat(parseFormat, Locale.getDefault()).format(objDate)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        /**
         * This will @return [Date] from timeStamp
         * */
        private fun createDate(timestamp: Long): Date {
            val c = Calendar.getInstance()
            c.timeInMillis = timestamp
            return c.time
        }

        /**
         * This will @return true if
         * @param email -> is correct
         * else @return false
         * */
        fun isValidEmailId(email: String): Boolean {
            return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.trim { it <= ' ' }).matches()
        }

        /**
         * This will return true if
         * @param s -> is empty
         * */
        fun isStringEmpty(s: CharSequence): Boolean {
            return isEmpty(s) || isEmpty(s.toString().trim { it <= ' ' }) || s == "null"
        }

        /**
         * This will convert GMT String to Date and will @return [Date]
         * @param currentFormat -> current format of date
         * @param strDate -> string that need to be converted into date
         * */
        fun convertGMTStringToDate(strDate: String, currentFormat: String): Date? {
            return try {
                val sdf = SimpleDateFormat(currentFormat, Locale.getDefault())
                sdf.timeZone = TimeZone.getTimeZone("GMT")
                sdf.parse(strDate)
            } catch (e: Exception) {
                e.printStackTrace()
                //If It can not be parsed, return today's date instead of null. So return value of
                // this method does not create null pointer exception.
                Date()
            }
        }

        /**
         * This will @return String having relative difference between dates
         * */
        fun getRelativeTimeDifference(startDate: Date, now: Date): String {
            //milliseconds
            var different = now.time - startDate.time

            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val monthsInMilli = daysInMilli * 30
            val yearsInMilli = monthsInMilli * 12

            val elapsedYears = different / yearsInMilli
            different %= yearsInMilli

            val elapsedMonths = different / monthsInMilli
            different %= monthsInMilli

            val elapsedDays = different / daysInMilli
            different %= daysInMilli

            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            return when {
                elapsedYears > 1 -> "$elapsedYears years ago"
                elapsedYears > 0 -> "$elapsedYears year ago"
                elapsedMonths > 1 -> "$elapsedMonths months ago"
                elapsedMonths > 0 -> "$elapsedMonths month ago"
                elapsedDays > 1 -> "$elapsedDays days ago"
                elapsedDays > 0 -> "$elapsedDays day ago"
                elapsedHours > 0 -> "$elapsedHours hrs ago"
                elapsedMinutes > 0 -> "$elapsedMinutes mins ago"
                elapsedSeconds > 0 -> "$elapsedSeconds secs ago"
                else -> 1.toString() + " sec"
            }
        }

        /**
         * This will convert [Bitmap] to Base64 String
         * */
        fun bitmapToBase64(bitmap: Bitmap?): String? {
            val byteArrayOutputStream = ByteArrayOutputStream()
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                return Base64.encodeToString(byteArray, Base64.DEFAULT)
            }
            return null
        }

        /**
         * This will convert Base64 to [Bitmap]
         * */
        fun stringToBitmap(encodedString: String): Bitmap? {
            return try {
                val decodedString = Base64.decode(encodedString, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            } catch (e: NullPointerException) {
                e.message
                null
            } catch (e: OutOfMemoryError) {
                null
            }
        }

        /**
         * This will @return true if file is Image file
         * @param path ->  path of file
         * @param context -> context of Class
         * */
        fun isImageFile(path: Uri, context: Context): Boolean {
            val mimeType = getMimeType(path, context)
            return mimeType != null && mimeType.startsWith("image")
        }

        /**
         * This will @return true if file is Video file
         * @param path ->  path of file
         * @param context -> context of Class
         * */
        fun isVideoFile(path: Uri, context: Context): Boolean {
            val mimeType = getMimeType(path, context)
            return mimeType != null && mimeType.startsWith("video")
        }

        /**
         * This will @return MimeType of file from [Uri]
         * @param uri ->  uri of file
         * @param context -> context of Class
         * */
        private fun getMimeType(uri: Uri, context: Context): String? {
            return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val cr = context.contentResolver
                cr.getType(uri)
            } else {
                val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
            }
        }

        /**
         * Resets Activity Title
         */
        fun resetActivityTitle(a: Activity) {
            try {
                val info = a.packageManager.getActivityInfo(a.componentName, GET_META_DATA)
                if (info.labelRes != 0) {
                    a.setTitle(info.labelRes)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

        }

        /**
         * Checks the at-Least version of Android API
         * @param version -> to compare version
         */
        fun isAtLeastVersion(version: Int): Boolean {
            return Build.VERSION.SDK_INT >= version
        }

        /**
         * This will return Yesterday's date with start time
         */
        fun getYesterdayStartDateString(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            return "${dateFormat.format(cal.time)} 00:00:00"
        }

        /**
         * This will return Yesterday's date with end time
         */
        fun getYesterdayEndDateString(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            return "${dateFormat.format(cal.time)} 23:59:59"
        }

        fun getCurrentDateString(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            return "${dateFormat.format(cal.time)}"
        }


    }
}