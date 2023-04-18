package com.cradlevendor.customui

import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatButton
import android.util.AttributeSet
import com.cradlevendor.R


import com.cradlevendor.utils.FontCache


class CustomButton : AppCompatButton {
    private var ctx: Context? = null

    constructor(context: Context) : super(context) {
        this.ctx = context
        applyCustomFont(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.ctx = context
        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.ctx = context
        applyCustomFont(context, attrs)
    }

    private fun applyCustomFont(context: Context, attrs: AttributeSet?) {

        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        val fontName = attributeArray.getString(R.styleable.CustomView_fontcustom)

        val textStyle = attrs!!.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)

        val customFont = selectTypeface(context, fontName, textStyle)
        typeface = customFont

        attributeArray.recycle()
    }


    private fun selectTypeface(context: Context, fontName: String?, textStyle: Int): Typeface? {
        return if (fontName == null) {
            FontCache.getTypeface("Roboto-Regular.ttf", context)
        } else if (fontName.contentEquals("Roboto-Regular.ttf")) {
            FontCache.getTypeface("Roboto-Regular.ttf", context)

        } else {
            FontCache.getTypeface("Roboto-Regular.ttf", context)
        }
    }

    companion object {

        val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"
    }

    /* @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPaint().setShader( new LinearGradient(
                        0, 0,getWidth(), 0,
                        context.getResources().getColor(R.color.pale_red,null), context.getResources().getColor(R.color.tomato,null),
                        Shader.TileMode.CLAMP ) );
            }else{
                getPaint().setShader( new LinearGradient(
                        0, 0,getWidth(), 0,
                        context.getResources().getColor(R.color.pale_red), context.getResources().getColor(R.color.tomato),
                        Shader.TileMode.CLAMP ) );
            }
        }
    }
*/
}
