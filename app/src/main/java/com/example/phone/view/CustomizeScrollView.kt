package com.example.phone.view

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView

class CustomizeScrollView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    HorizontalScrollView(context, attrs, defStyleAttr) {

    private var mListener: ((view: CustomizeScrollView, l: Int, t: Int, oldl: Int, oldt: Int) -> Unit)? =
        null

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mListener?.invoke(this, l, t, oldl, oldt)
    }

    fun setScrollViewListener(viewListener: (view: CustomizeScrollView, l: Int, t: Int, oldl: Int, oldt: Int) -> Unit) {
        mListener = viewListener
    }
}