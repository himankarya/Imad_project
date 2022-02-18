package com.soomapps.universalremotecontrol.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerTouchListener(context: Context, private val mListener: OnItemClickListener) : RecyclerView.OnItemTouchListener {


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    // private var mListener: OnItemClickListener? = null
    private var mGestureDetector: GestureDetector = GestureDetector(context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }
            })

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView = rv.findChildViewUnder(e.x, e.y)
        // val exactView:View  = findExactChild(childView!!, e.x, e.x)!!
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }


}