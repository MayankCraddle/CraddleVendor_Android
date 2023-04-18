package com.cradlevendor.utils

import android.content.Context
import android.graphics.Typeface


object FontCache {
    private val fontCache = HashMap<String, Typeface>()

    fun getTypeface(fontname: String, context: Context): Typeface? {
        var typeface = fontCache[fontname]

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets, "fonts/$fontname")
            } catch (e: Exception) {
                /*getContext().getAssets(), "fonts/" + fontName*/
                return null
            }

            fontCache[fontname] = typeface
        }

        return typeface
    }

}

