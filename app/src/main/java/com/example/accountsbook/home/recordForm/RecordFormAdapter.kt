package com.example.accountsbook.home.recordForm

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.R
import com.example.accountsbook.home.recordForm.delegate.ReceiptDelegate
import com.example.accountsbook.home.recordForm.receipt.ReceiptAdapter
import com.example.accountsbook.home.recordForm.receipt.ReceiptItem
import com.example.accountsbook.util.color
import com.example.accountsbook.util.dp
import com.example.accountsbook.view.DividerDecoration

class RecordFormAdapter : ListAdapter<RecordFormItem, RecyclerView.ViewHolder>(
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

    private val receiptDelegate = ReceiptDelegate()

    private val delegates = mapOf(
        RecordFormItem.Receipt::class.java to receiptDelegate
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

        private val dateTv: TextView = itemView.findViewById(R.id.tv_list_item_record_form_receipt_date)
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
}
