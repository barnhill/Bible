package com.pnuema.bible.android.ui.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.NestedScrollType
import com.pnuema.bible.android.R
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class BottomNavigationViewBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<TextView>(context, attrs) {
    private var height: Int = -1
    private var scrollSum = 0f

    override fun onLayoutChild(parent: CoordinatorLayout, child: TextView, layoutDirection: Int): Boolean {
        if (height == -1) {
            height = child.resources.getDimension(R.dimen.read_pane_bottom_reminder_height).toInt()
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: TextView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyConsumed > 0) {
            scrollSum = max(scrollSum - abs(dyConsumed), 0f)
        } else if (dyConsumed < 0) {
            scrollSum = min(scrollSum + abs(dyConsumed), height.toFloat())
        }

        child.translationY = scrollSum
    }
}