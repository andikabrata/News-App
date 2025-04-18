package com.example.newsapp.common.extension

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.UrlQuerySanitizer
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.util.Patterns
import com.github.ajalt.timberkt.Timber
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLDecoder
import java.net.UnknownHostException
import java.security.MessageDigest
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
inline fun CharSequence.isEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

inline fun CharSequence.isIp() = Patterns.IP_ADDRESS.matcher(this).matches()

inline fun CharSequence.isUrl() = Patterns.WEB_URL.matcher(this).matches()

inline fun CharSequence.isPhone() = Patterns.PHONE.matcher(this).matches()

fun String.containsNewLine(): Boolean {
    val regex = Pattern.compile("^(.*)$", Pattern.MULTILINE)
    return regex.split(this).isNotEmpty()
}

inline fun String.md5(): String {
    return try {
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(toByteArray())
        val number = BigInteger(1, messageDigest)
        var md5 = number.toString(16)

        while (md5.length < 32)
            md5 = "0$md5"

        md5
    } catch (e: Throwable) {
        ""
    }
}

inline fun String.getStackTraceString(tr: Throwable?): String {
    if (tr == null) {
        return ""
    }

    var t = tr
    while (t != null) {
        if (t is UnknownHostException) {
            return ""
        }
        t = t.cause
    }

    val sw = StringWriter()
    val pw = PrintWriter(sw)
    tr.printStackTrace(pw)
    pw.flush()
    return sw.toString()
}

inline fun String.replaceWithBlank(value: String): String = this.replace(value.toRegex(), "")

fun String.splitQuery(): Map<String, String> {

    val queryPairs = LinkedHashMap<String, String>()
    try {
        val queryIndex = this.indexOf("?")
        if (queryIndex < 0) {
            return queryPairs
        }
        val query = this.substring(queryIndex + 1)
        val pairs = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            queryPairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
        }
    } catch (e: Exception) {
        Timber.e { "SplitQuery_Error ${e.message}" }
    }
    return queryPairs
}

fun compileRegex(url: String): Pattern? {
    var pattern: Pattern? = null
    try {
        pattern = Pattern.compile(url)
    } catch (e: Exception) {
        Timber.e { "compileRegex Error ${e.message}" }
    }

    return pattern
}

fun String.getValueQueryPage(): String {
    return this.splitQuery()["page"].orEmpty()
}

inline fun Spanned.toHtml(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.toHtml(this, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
    } else {
        @Suppress("DEPRECATION")
        return Html.toHtml(this)
    }
}

inline fun String.fromHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

inline fun String.fromHtml(imageGetter: Html.ImageGetter, tagHandler: Html.TagHandler?): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY, imageGetter, tagHandler)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this, imageGetter, tagHandler)
    }
}

fun String.fromHtml(context: Context, height: Int): Spanned {
    val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY) as Spannable
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this) as Spannable
    }
    return result
}


inline fun String.format(format: String, vararg args: Any?): String =
    java.lang.String.format(format, *args)

val String.uppercaseFirst: String
    @SuppressLint("DefaultLocale")
    get() = when (this.length) {
        0, 1 -> this.uppercase(Locale.getDefault())
        else -> this[0].uppercaseChar() + this.substring(1)
    }

inline fun String.parseContent(): String = this.replace("\n", "<br>")

inline fun String.couponTypeIsGroupCoupon(): Boolean {
    var result = false
    if (this == "GroupCoupon") {
        result = true
    }
    return result
}

inline fun String.rateToDecimal(): String = "$this.0"

inline fun String.addPercent(): String = "$this %"

inline fun String.addPlusOfRight(): String = "+$this"

inline fun String.addComa(): String = "$this, "

inline fun String?.orNoData(): String = this ?: "Tidak Ada Data"

inline fun String?.replaceBoolean(): Boolean = this == "1"

@SuppressLint("DefaultLocale")
fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") {
        it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}

fun CharSequence.toStyleSpan(style: Int = Typeface.BOLD, range: IntRange): SpannableString {
    return SpannableString(this).apply {
        setSpan(StyleSpan(style), range.first, range.last, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun String.toBold(): String {
    return "<b>${this}</b>"
}

fun String.getQueryPage(): Int {
    val sanitizer = UrlQuerySanitizer(this)
    return sanitizer.getValue("page").toInt()
}

@SuppressLint("DefaultLocale")
fun String.checkCity(): String {
    return if (this.lowercase(Locale.getDefault()) == "semua") {
        ""
    } else {
        this
    }
}

fun menuContent(tags: ArrayList<String>): String {
    return tags.toString().replace("[\\[\\]()]".toRegex(), "")
}

fun String.getTimeMilliSec(pattern: String = "dd MMM yyyy HH:mm:ss"): Long {
    val format = SimpleDateFormat(pattern, Locale("en", "us"))
    try {
        val date = format.parse(this)
        return date.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0
}

fun String.getBitmapFromURL(): Bitmap? {
    return try {
        val url = URL(this)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        null
    }
}

fun thisMonth(): String {
    val calendar = Calendar.getInstance()
    return calendar.time.toStringDate("MM/yyyy")
}

fun Throwable.errorMessage(): String {
    val msg: String
    var code: Int? = null

    when (this) {
        is HttpException -> {
            val responseBody = this.response()?.errorBody()
            code = response()?.code()
            msg = when (code) {
                500 -> {
                    "Terjadi masalah koneksi ke server"
                }
                else -> {
                    responseBody.getErrorMessage()
                }
            }
            println("HttpException checkApiError onError Code : $code : $msg")

        }
        is SocketTimeoutException -> {
            msg = "Timeout, Coba lagi"
        }
        else -> {
            msg = "Terjadi masalah koneksi $this"
        }
    }
    println("ApiOnError : $code $msg")
    return msg
}

private fun ResponseBody?.getErrorMessage(): String {
    return try {
        val jsonObject = JSONObject(this?.string() ?: "")
        println("jsonObjectError : $jsonObject")
        return when {
            jsonObject.has("code") -> jsonObject.getString("code")
            jsonObject.has("message") -> jsonObject.getString("message")
            else -> "Terjadi kesalahan, Coba lagi"
        }
    } catch (e: JSONException) {
        e.message.toString()
    }

}

inline fun String?.spannedFromHtml(): Spanned {
    return when {
        this == null -> {
            // return an empty spannable if the html is null
            SpannableString("")
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        }
        else -> {
            Html.fromHtml(this)
        }
    }
}

fun currentDatetimeString(): String {
    val currentDatetime = Calendar.getInstance().time
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("id", "ID"))
    return sdf.format(currentDatetime)
}

@SuppressLint("LogNotTimber")
fun modelToJson(param: Any): String {
    val gson = Gson()
    val jsonIn = gson.toJson(param)
    Log.d("utils", "json in $jsonIn")
    return jsonIn
}