package com.teletaleem.rmrs_customer.utilities

/**
 * Created by mmubashir on 6/30/17.
 */

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


class RecyclerItemClickListener(context: Context, recyclerView: androidx.recyclerview.widget.RecyclerView, private val mListener: OnItemClickListener?) :
        androidx.recyclerview.widget.RecyclerView.OnItemTouchListener {

    internal var mGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)

        fun onLongItemClick(view: View?, position: Int)
    }

    init {
        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(view: androidx.recyclerview.widget.RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(view: androidx.recyclerview.widget.RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}