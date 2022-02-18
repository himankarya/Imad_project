package com.soomapps.universalremotecontrol.utils

import android.content.Context
import android.view.MotionEvent
import android.text.method.Touch.onTouchEvent
import android.view.ViewGroup
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.GestureDetector
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class RecyclerItemClickListenerMain(context: Context, recyclerView: RecyclerView, private val mListener: OnItemClickListener?) : RecyclerView.OnItemTouchListener {
    private val mGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }

    init {
        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                // Important: x and y are translated coordinates here
                val childViewGroup = recyclerView.findChildViewUnder(e.x, e.y) as ViewGroup?

                if (childViewGroup != null && mListener != null) {
                    val viewHierarchy = ArrayList<View>()
                    // Important: x and y are raw screen coordinates here
                    getViewHierarchyUnderChild(childViewGroup, e.rawX, e.rawY, viewHierarchy)

                    var touchedView: View? = childViewGroup
                    if (viewHierarchy.size > 0) {
                        touchedView = viewHierarchy[0]
                    }
                    mListener.onItemClick(touchedView, recyclerView.getChildPosition(childViewGroup))
                    return true
                }
                return false
            }

            override fun onLongPress(e: MotionEvent) {
                val childView = recyclerView.findChildViewUnder(e.x, e.y)

                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView))
                }
            }
        })
    }

    fun getViewHierarchyUnderChild(root: ViewGroup?, x: Float, y: Float, viewHierarchy: MutableList<View>) {
        val location = IntArray(2)
        val childCount = root!!.childCount

        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            child.getLocationOnScreen(location)
            val childLeft = location[0]
            val childRight = childLeft + child.width
            val childTop = location[1]
            val childBottom = childTop + child.height

            if (child.isShown && x >= childLeft && x <= childRight && y >= childTop && y <= childBottom) {
                viewHierarchy.add(0, child)
            }
            if (child is ViewGroup) {
                getViewHierarchyUnderChild(child, x, y, viewHierarchy)
            }
        }
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(e)

        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}