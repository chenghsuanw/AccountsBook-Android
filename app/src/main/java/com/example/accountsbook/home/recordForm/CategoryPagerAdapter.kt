package com.example.accountsbook.home.recordForm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.accountsbook.R
import com.example.accountsbook.database.CategoryEntity

class CategoryPagerAdapter : PagerAdapter() {

    private val categoryPages: MutableList<CategoryPage> = mutableListOf()

    override fun isViewFromObject(view: View, anyObject: Any): Boolean {
        return view == anyObject
    }

    override fun getCount(): Int {
        return categoryPages.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.list_item_category_page, container, false)
        view.findViewById<RecyclerView>(R.id.rv_list_item_category_page).apply {
            adapter = CategoryListAdapter().apply {
                submitList(categoryPages[position].categories)
            }
            layoutManager = GridLayoutManager(context, 5)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun setData(data: List<CategoryPage>) {
        categoryPages.clear()
        categoryPages.addAll(data)
        notifyDataSetChanged()
    }
}

data class CategoryPage(
    val categories: List<CategoryEntity>
)
