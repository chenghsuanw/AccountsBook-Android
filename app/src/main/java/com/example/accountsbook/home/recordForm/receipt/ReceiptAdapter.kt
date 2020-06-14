package com.example.accountsbook.home.recordForm.receipt

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.R
import com.example.accountsbook.view.SimpleTextWatcher
import com.google.android.material.chip.ChipGroup

class ReceiptAdapter(
    private val listener: EventListener?
) : ListAdapter<ReceiptItem, RecyclerView.ViewHolder>(diffCallback) {

    interface EventListener : TypeViewHolder.EventListener, AmountViewHolder.EventListener,
        DescriptionViewHolder.EventListener

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ReceiptItem>() {
            override fun areItemsTheSame(oldItem: ReceiptItem, newItem: ReceiptItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ReceiptItem, newItem: ReceiptItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ReceiptItem.getClazzFromViewType(viewType)) {
            ReceiptItem.Type::class.java -> {
                TypeViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_receipt_type,
                        parent,
                        false
                    ), listener
                )
            }
            ReceiptItem.Amount::class.java -> {
                AmountViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_receipt_amount,
                        parent,
                        false
                    ), listener
                )
            }
            ReceiptItem.Description::class.java -> {
                DescriptionViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_receipt_description,
                        parent,
                        false
                    ), listener
                )
            }
            else -> error("unknown type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val clazz = ReceiptItem.getClazzFromViewType(viewType)
        val item = getItem(position)
        when (clazz) {
            ReceiptItem.Type::class.java -> {
                (holder as? TypeViewHolder)?.bindView(item as ReceiptItem.Type)
            }
            ReceiptItem.Amount::class.java -> {
                (holder as? AmountViewHolder)?.bindView(item as ReceiptItem.Amount)
            }
            ReceiptItem.Description::class.java -> {
                (holder as? DescriptionViewHolder)?.bindView(item as ReceiptItem.Description)
            }
        }
    }

    class TypeViewHolder(
        itemView: View, listener: EventListener?
    ) : RecyclerView.ViewHolder(itemView) {

        interface EventListener {
            fun onTypeChanged(isIncome: Boolean)
        }

        private val typeChipGroup: ChipGroup =
            itemView.findViewById(R.id.chip_group_list_item_receipt_type)

        init {
            typeChipGroup.setOnCheckedChangeListener { _, checkedId ->
                listener?.onTypeChanged(isIncome = checkedId == R.id.type_income)
            }
        }

        fun bindView(item: ReceiptItem.Type) {
            typeChipGroup.check(
                if (item.isIncome == true) R.id.type_income
                else R.id.type_outcome
            )
        }
    }

    class AmountViewHolder(
        itemView: View, listener: EventListener?
    ) : RecyclerView.ViewHolder(itemView) {

        interface EventListener {
            fun onAmountChanged(amount: Int)
        }

        private val amountEt: EditText =
            itemView.findViewById(R.id.et_list_item_receipt_description)

        init {
            amountEt.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    listener?.onAmountChanged(s.toString().toInt())
                }
            })
        }

        fun bindView(item: ReceiptItem.Amount) {
            item.amount?.let {
                amountEt.setText(it.toString())
            }
        }
    }

    class DescriptionViewHolder(
        itemView: View, listener: EventListener?
    ) : RecyclerView.ViewHolder(itemView) {

        interface EventListener {
            fun onDescriptionDetailClicked()
        }

        private val descriptionTv: TextView =
            itemView.findViewById(R.id.tv_list_item_receipt_description)
        private val detailIv: ImageView =
            itemView.findViewById(R.id.iv_list_item_receipt_description_detail)

        init {
            detailIv.setOnClickListener {
                listener?.onDescriptionDetailClicked()
            }
        }

        fun bindView(item: ReceiptItem.Description) {
            item.description?.let {
                descriptionTv.text = it
            }
        }
    }
}
