package com.example.accountsbook.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class DividerDecoration(
    @ColorInt dividerColor: Int,
    private val dividerHeight: Float,
    private val paddingStart: Float = 0F,
    private val paddingEnd: Float = paddingStart
) : RecyclerView.ItemDecoration() {
    private val paint: Paint = Paint().apply {
        color = dividerColor
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        if (parent.clipToPadding) {
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        }

        0.until(parent.childCount).mapNotNull { index ->
            parent.getChildAt(index)
        }.filter { child ->
            shouldDrawDivider(parent, child)
        }.forEach { childShouldDraw ->
            val bottom = childShouldDraw.bottom + childShouldDraw.translationY
            val top = (bottom - dividerHeight).toInt()
            canvas.drawRect(
                left.toFloat() + paddingStart,
                top.toFloat(),
                right.toFloat() - paddingEnd,
                bottom,
                paint
            )
        }

        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (shouldDrawDivider(parent, view)) {
            outRect.set(0, 0, 0, dividerHeight.toInt())
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    private fun shouldDrawDivider(parent: RecyclerView, child: View): Boolean {
        val adapter = parent.adapter ?: return false
        val pos = parent.getChildAdapterPosition(child)
            .takeIf { it != RecyclerView.NO_POSITION } ?: return false

        val isLastItem = pos == (adapter.itemCount - 1)
        return !isLastItem
    }
}
