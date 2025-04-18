package com.example.newsapp.common.extension

import android.content.Context
import android.view.MotionEvent
import androidx.recyclerview.widget.*

fun RecyclerView.addLayoutManager(context: Context, spanCount: Int = 1) {
    layoutManager = if (spanCount > 1) {
        GridLayoutManager(context, spanCount)
    } else {
        LinearLayoutManager(context)
    }
}

fun RecyclerView.addDivider(
    context: Context,
    dividerOrientation: Int = RecyclerView.VERTICAL
) {
    addItemDecoration(DividerItemDecoration(context, dividerOrientation))
}

/**
 * Set a [GridLayoutManager] as `this` layoutManager.
 *
 * @param spanCount number of grid columns
 *
 * @return `this`
 */
fun RecyclerView.withGridLayoutManager(spanCount: Int): RecyclerView = apply {
    layoutManager = GridLayoutManager(context, spanCount)
}

/**
 * Set a [LinearLayoutManager] as `this` layoutManager.
 *
 * @param vertical whether `this` layout should be vertical, default is true
 * @param reversed whether `this` layout should be reverted, default is false
 *
 * @return `this`
 */
fun RecyclerView.withLinearLayoutManager(
    vertical: Boolean = true,
    reversed: Boolean = false
): RecyclerView = apply {
    layoutManager =
        LinearLayoutManager(context, if (vertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL, reversed)
}

/**
 * Creates a [PagerSnapHelper] and attaches to `this`.
 *
 * @return `this`
 */
fun RecyclerView.withPagerSnapHelper(): RecyclerView = apply {
    PagerSnapHelper().attachToRecyclerView(this)
}
// endregion

private object QuickAdapterClick {
    var delayTime = 0L
    var lastClickTime = 0L
}

private fun clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - QuickAdapterClick.lastClickTime >= QuickAdapterClick.delayTime) {
        QuickAdapterClick.lastClickTime = currentClickTime
        flag = true
    }
    return flag
}


fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

fun RecyclerView.linearLayoutManager(orientation: Int = RecyclerView.VERTICAL) = run {
    this.layoutManager = LinearLayoutManager(this.context, orientation, false)
    this
}

fun RecyclerView.gridLayoutManager(spanCount: Int) = run {
    this.layoutManager = GridLayoutManager(this.context, spanCount)
    this
}

fun RecyclerView.setRecyclerViewClickable(clickable: Boolean) {
    isEnabled = clickable
    if (!clickable) {
        val itemTouchListener = object : RecyclerView.OnItemTouchListener {

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return !rv.isEnabled
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }

        }
        addOnItemTouchListener(itemTouchListener)
        tag = itemTouchListener
    } else {
        (tag as? RecyclerView.OnItemTouchListener)?.let {
            requestDisallowInterceptTouchEvent(true)
            removeOnItemTouchListener(it)
        }
    }
}