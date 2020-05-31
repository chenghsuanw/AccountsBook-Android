package com.example.accountsbook.home.recordForm

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.accountsbook.R
import com.example.accountsbook.home.recordForm.delegate.CategoryDelegate
import com.example.accountsbook.home.recordForm.delegate.ConfirmDelegate
import com.example.accountsbook.home.recordForm.delegate.ReceiptDelegate
import com.example.accountsbook.home.recordForm.receipt.ReceiptAdapter
import com.example.accountsbook.home.recordForm.receipt.ReceiptItem
import com.example.accountsbook.util.color
import com.example.accountsbook.util.dp
import com.example.accountsbook.view.DividerDecoration

class RecordFormAdapter(listener: EventListener?) :
    ListAdapter<RecordFormItem, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RecordFormItem>() {
            override fun areItemsTheSame(
                oldItem: RecordFormItem,
                newItem: RecordFormItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecordFormItem,
                newItem: RecordFormItem
            ): Boolean {
                return oldItem == newItem
            }
        }

        private const val CATEGORY_COUNT_PER_PAGE = 10
    }

    interface TypedDelegate<T : RecordFormItem> {

        fun onCreateViewHolder(
            parent: ViewGroup,
            clazz: Class<out RecordFormItem>
        ): RecyclerView.ViewHolder {
            return onCreateViewHolder(parent)
        }

        fun onBindViewHolder(
            item: RecordFormItem,
            holder: RecyclerView.ViewHolder,
            clazz: Class<out RecordFormItem>
        ) {
            onBindViewHolder(item as T, holder)
        }

        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

        fun onBindViewHolder(item: T, holder: RecyclerView.ViewHolder)
    }

    interface EventListener : CategoryViewHolder.EventListener

    private val receiptDelegate = ReceiptDelegate()
    private val categoryDelegate = CategoryDelegate(listener)
    private val confirmDelegate = ConfirmDelegate()

    private val delegates = mapOf(
        RecordFormItem.Receipt::class.java to receiptDelegate,
        RecordFormItem.Category::class.java to categoryDelegate,
        RecordFormItem.Confirm::class.java to confirmDelegate
    )

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val clazz = RecordFormItem.getClazzFromViewType(
            viewType
        ) ?: error("unknown type")
        return delegates[clazz]?.onCreateViewHolder(parent, clazz) ?: error("unknown type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val clazz = RecordFormItem.getClazzFromViewType(
            viewType
        ) ?: return
        delegates[clazz]?.onBindViewHolder(getItem(position), holder, clazz)
    }

    class ReceiptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateTv: TextView =
            itemView.findViewById(R.id.tv_list_item_record_form_receipt_date)
        private val recyclerView: RecyclerView =
            itemView.findViewById(R.id.rv_list_item_record_form_receipt)

        init {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ReceiptAdapter()
                val divider = DividerDecoration(
                    dividerColor = R.color.bg_gray.color(),
                    dividerHeight = 1.dp(),
                    paddingStart = 32.dp()
                )
                addItemDecoration(divider)
            }
        }

        fun bindView(item: RecordFormItem.Receipt) {
            dateTv.text = item.date
            (recyclerView.adapter as? ReceiptAdapter)?.submitList(
                listOf(
                    ReceiptItem.Type(item.isIncome),
                    ReceiptItem.Amount(item.amount),
                    ReceiptItem.Description(item.description)
                )
            )
        }
    }

    class CategoryViewHolder(itemView: View, listener: EventListener?) :
        RecyclerView.ViewHolder(itemView) {

        interface EventListener {
            fun onMoreIconClicked(view: View)
        }

        private val moreIv: ImageView =
            itemView.findViewById(R.id.iv_list_item_record_form_category_more)
        private val viewPager: ViewPager =
            itemView.findViewById(R.id.vp_list_item_record_form_category)

        init {
            moreIv.setOnClickListener {
                listener?.onMoreIconClicked(it)
            }
            viewPager.adapter = CategoryPagerAdapter()
        }

        fun bindView(item: RecordFormItem.Category) {
            val categories = item.categories
            val categoryPages = categories.chunked(CATEGORY_COUNT_PER_PAGE).map { CategoryPage(it) }
            (viewPager.adapter as CategoryPagerAdapter).setData(categoryPages)
        }
    }

    class ConfirmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val button: Button = itemView.findViewById(R.id.btn_list_item_record_form_confirm)

        init {
            button.setOnClickListener {
                // TODO: implement
            }
        }

        fun bindView(item: RecordFormItem.Confirm) {
            button.text = item.text
        }
    }
}
