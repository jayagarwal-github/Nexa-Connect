package com.example.nexaconnect

class Utils {
    companion object {


        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS

       fun getTimeAgo(time : Long) :String?{

           val now : Long = System.currentTimeMillis()

            if (time > now || time <= 0) {
                return null
            }


            val diff = now - time;
           return if (diff < MINUTE_MILLIS) {
                return "just now"
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago"
            } else if (diff < 50 * MINUTE_MILLIS) {
                return (diff / MINUTE_MILLIS).toString() + " minutes ago";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago"
            } else if (diff < 24 * HOUR_MILLIS) {
                return (diff / HOUR_MILLIS).toString() + " hours ago";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday"
            } else {
                return (diff / DAY_MILLIS).toString() + " days ago"
            }
        }
    }
}