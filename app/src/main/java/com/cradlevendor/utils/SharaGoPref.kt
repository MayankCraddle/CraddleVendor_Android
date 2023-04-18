package com.cradlevendor.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharaGoPref constructor() {

    private enum class Keys private constructor(val label: String) {
        LOGIN("LOGIN"),LOGIN_TOKEN("LOGIN"),SERVER_KEY("SERVERKEY"),CART_ID("CARTID"),YOUTUBE_ID("youtube_id"),CART_SIZE("0"),
        COUNTRYIMAGE("country_image"),
        CART_ITEM("cart_item"),
        SCREENPATH("screen_path"),
        SECTION_ID("section_id"),
        USERPATH("user_path"),
        COUNTRYNAME1("country"),
        EncryptedId("encryptedId"),
        REMEMBERME("checkbox"),
        STOCK("stock"),UUSERID("user_id"),
        NewUserPath("user"),
        COUNTRYFLAG("countryflag"),
        USERNAME("user_name"),COUNTRYNAME("country_name"),TYPE("type"), ACCESS_TOKEN("access_token"),BUSINESS_NAME("business-name"), EMAIL("email_id"), LANGUAGE("language"), TOKEN("token"),PRODUCT_CAT("cat"), FCMKEY("fcm_key"),  WHICHFRG("which_frg"), USERID(
            "user_id"),
        ROAL("roal"),FONT_FOLOR("font_color"),SITE_ID("site_id"),SITE_IMG_URL("site_image_url"), USERTYPE("user_type"), GOOGLE_ID("google_id"), PHONE("phone"), USERPROFILE("user_pic");


    }

    /**
     * This Method Clear shared preference.
     */
    fun clear() {
        val editor = _pref!!.edit()
        editor.clear()
        editor.commit()
    }

    fun setLoginToken(value: String) {
        setString(Keys.LOGIN_TOKEN.label, value)
    }
    fun getLoginToken(defaultValue: String): String? {
        return getString(Keys.LOGIN_TOKEN.label, defaultValue)
    }
    fun setServerKey(value: String) {
        setString(Keys.SERVER_KEY.label, value)
    }
    fun getServerKey(defaultValue: String): String? {
        return getString(Keys.SERVER_KEY.label, defaultValue)
    }
    fun setName(value: String) {
        setString(Keys.USERNAME.label, value)
    }
    fun getName(defaultValue: String): String? {
        return getString(Keys.USERNAME.label, defaultValue)
    }

    fun setFcmKey(value: String) {
        setString(Keys.FCMKEY.label, value)
    }

    fun getFcmKey(defaultValue: String): String? {
        return getString(Keys.FCMKEY.label, defaultValue)
    }

    fun setGoogleID(value: String) {
        setString(Keys.GOOGLE_ID.label, value)
    }

    fun getGoogleID(defaultValue: String): String? {
        return getString(Keys.GOOGLE_ID.label, defaultValue)
    }
    fun setEmailId(value: String) {
        setString(Keys.EMAIL.label, value)
    }

    fun getEmailId(defaultValue: String): String? {
        return getString(Keys.EMAIL.label, defaultValue)
    }



    fun setVideoID(value: String) {
        setString(Keys.YOUTUBE_ID.label, value)
    }

    fun getVideoID(defaultValue: String): String? {
        return getString(Keys.YOUTUBE_ID.label, defaultValue)
    }

    fun setColor(value: Int) {
        setInt(Keys.FONT_FOLOR.label, value)
    }

    fun getColor(defaultValue: Int): Int? {
        return getInt(Keys.FONT_FOLOR.label, defaultValue)
    }
    fun setCountry(value: String) {
        setString(Keys.COUNTRYNAME.label, value)
    }
    fun setUserType(value: String) {
        setString(Keys.TYPE.label, value)
    }

    fun setEncryptedId(value: String) {
        setString(Keys.EncryptedId.label, value)
    }
    fun getEncryptedId(defaultValue: String): String? {
        return getString(Keys.EncryptedId.label, defaultValue)
    }




    fun getUserType(defaultValue: String): String? {
        return getString(Keys.TYPE.label, defaultValue)
    }

    fun getCountry(defaultValue: String): String? {
        return getString(Keys.COUNTRYNAME.label, defaultValue)
    }
    fun setCountryFlag(value: String) {
        setString(Keys.COUNTRYFLAG.label, value)
    }

    fun getCountryFlag(defaultValue: String): String? {
        return getString(Keys.COUNTRYFLAG.label, defaultValue)
    }




    private fun getString(key: String?, defaultValue: String): String? {
        return if (_pref != null && key != null && _pref!!.contains(key)) {
            _pref!!.getString(key, defaultValue)
        } else defaultValue
    }

    private fun getInt(key: String?, defaultValue: Int): Int {
        return if (_pref != null && key != null && _pref!!.contains(key)) {
            _pref!!.getInt(key, defaultValue)
        } else defaultValue
    }

    private fun getLong(key: String?, defaultValue: Long): Long {
        return if (_pref != null && key != null && _pref!!.contains(key)) {
            _pref!!.getLong(key, defaultValue)
        } else defaultValue
    }

    fun setInt(key: String?, value: Int) {
        if (key != null) {
            try {
                if (_pref != null) {
                    val editor = _pref!!.edit()
                    editor.putInt(key, value)
                    editor.commit()
                }
            } catch (e: Exception) {
                Log.e(
                        TAG, "Unable to set " + key + "= " + value
                        + "in shared preference", e
                )
            }

        }
    }


    private fun setBoolean(key: String?, value: Boolean) {
        if (key != null) {
            try {
                if (_pref != null) {
                    val editor = _pref!!.edit()
                    editor.putBoolean(key, value)
                    editor.commit()
                }
            } catch (e: Exception) {
                Log.e(
                    TAG, "Unable to set " + key + "= " + value
                            + "in shared preference", e
                )
            }

        }
    }
    fun setUSERID(value: String) {
        setString(Keys.USERID.label, value)
    }


    fun getUSERID(defaultValue: String): String? {
        return getString(Keys.USERID.label, defaultValue)
    }
    fun setWhichFrag(value: String) {
        setString(Keys.WHICHFRG.label, value)
    }

    fun getWhichFrag(defaultValue: String): String? {
        return getString(Keys.WHICHFRG.label, defaultValue)
    }

    fun setStock(value: String) {
        setString(Keys.STOCK.label, value)
    }

    fun getStock(defaultValue: String): String? {
        return getString(Keys.STOCK.label, defaultValue)
    }

    fun setCartId(value: String) {
        setString(Keys.CART_ID.label, value)
    }

    fun getCartId(defaultValue: String): String? {
        return getString(Keys.CART_ID.label, defaultValue)
    }
    fun setCartSize(value: String) {
        setString(Keys.CART_SIZE.label, value)
    }

    fun getCartSize(defaultValue: String): String? {
        return getString(Keys.CART_SIZE.label, defaultValue)
    }

    fun setYotubeID(value: String) {
        setString(Keys.YOUTUBE_ID.label, value)
    }

    fun getYoutubeID(defaultValue: String): String? {
        return getString(Keys.YOUTUBE_ID.label, defaultValue)
    }

    fun setSectionId(value: String) {
        setString(Keys.SECTION_ID.label, value)
    }

    fun getSectionId(defaultValue: String): String? {
        return getString(Keys.CART_ITEM.label, defaultValue)
    }

    fun setCartInItem(value: String) {
        setString(Keys.CART_ITEM.label, value)
    }

    fun getsetCartInItem(defaultValue: String): String? {
        return getString(Keys.SECTION_ID.label, defaultValue)
    }

    private fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return if (_pref != null && key != null && _pref!!.contains(key)) {
            _pref!!.getBoolean(key, defaultValue)
        } else defaultValue
    }

    private fun setString(key: String?, value: String?) {
        if (key != null && value != null) {
            try {
                if (_pref != null) {
                    val editor = _pref!!.edit()
                    editor.putString(key, value)
                    editor.commit()
                }
            } catch (e: Exception) {
                Log.e(
                    TAG, "Unable to set " + key + "= " + value
                            + "in shared preference", e
                )
            }

        }





    }




    companion object {
        val TAG = SharaGoPref::class.java.name
        private var _pref: SharedPreferences? = null
        private var _instance: SharaGoPref? = null
        private val PRIVATE_MODE = 0
        val SHARED_PREF_NAME = "RMC_"

        fun getInstance(context: Context): SharaGoPref {
            if (_pref == null) {
                _pref = context
                    .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            }
            if (_instance == null) {
                _instance = SharaGoPref()
            }
            return _instance as SharaGoPref
        }
    }
}