package com.soomapps.universalremotecontrol.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context: Context, private val mListener: OnItemClickListener?) : RecyclerView.OnItemTouchListener {
    private var childView: View? = null
    private var viewRecycle: RecyclerView? = null

    internal var mGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)

        fun onLongItemClick(view: View?, position: Int)
    }

    init {
        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, viewRecycle!!.getChildAdapterPosition(childView!!))
                }
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                if (childView != null && mListener != null) {
                    mListener.onLongItemClick(childView, viewRecycle!!.getChildAdapterPosition(childView!!))
                }
                super.onLongPress(e)
            }
        })
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        // AppHelper.AppLogger("inside");
        childView = view.findChildViewUnder(e.x, e.y)
        viewRecycle = view
        mGestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}
