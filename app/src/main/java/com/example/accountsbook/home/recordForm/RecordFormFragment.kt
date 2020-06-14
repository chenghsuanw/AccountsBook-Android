package com.example.accountsbook.home.recordForm

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.BaseFragment
import com.example.accountsbook.R
import com.example.accountsbook.database.CategoryEntity
import com.example.accountsbook.navigation.ToolbarConfig
import com.example.accountsbook.util.color
import com.example.accountsbook.util.dp
import com.example.accountsbook.util.extraRequired
import com.example.accountsbook.view.DividerDecoration
import kotlin.properties.Delegates

class RecordFormFragment : BaseFragment(), RecordFormAdapter.EventListener,
    PopupMenu.OnMenuItemClickListener {

    companion object {

        private const val ARG_IS_NEW_RECORD = "IS_NEW_RECORD"
        private const val ARG_DATE = "DATE"
        private const val ARG_IS_INCOME = "IS_INCOME"
        private const val ARG_AMOUNT = "AMOUNT"
        private const val ARG_DESCRIPTION = "DESCRIPTION"
        private const val ARG_CATEGORY = "CATEGORY"

        fun newInstance(
            date: String,
            isNewRecord: Boolean = true,
            isIncome: Boolean? = null,
            amount: Int? = null,
            description: String? = null,
            category: CategoryEntity? = null
        ): RecordFormFragment {
            return RecordFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DATE, date)
                    putBoolean(ARG_IS_NEW_RECORD, isNewRecord)
                    isIncome?.let { putBoolean(ARG_IS_INCOME, it) }
                    amount?.let { putInt(ARG_AMOUNT, it) }
                    description?.let { putString(ARG_DESCRIPTION, it) }
                    category?.let { putSerializable(ARG_CATEGORY, it) }
                }
            }
        }
    }

    override val toolbarConfig: ToolbarConfig?
        get() = ToolbarConfig(
            title = "紀錄",
            homeIcon = R.drawable.ic_back
        )

    // TODO: get data from database
    private val categories = listOf(
        CategoryEntity("食物", R.drawable.category_food),
        CategoryEntity("購物", R.drawable.category_shopping),
        CategoryEntity("住宅", R.drawable.category_house),
        CategoryEntity("交通", R.drawable.category_transport),
        CategoryEntity("教育", R.drawable.category_educate),
        CategoryEntity("娛樂", R.drawable.category_fun),
        CategoryEntity("旅行", R.drawable.category_travel),
        CategoryEntity("醫療", R.drawable.category_hospital),
        CategoryEntity("投資", R.drawable.category_invest),
        CategoryEntity("轉帳", R.drawable.category_transfer)
    )
    private lateinit var recyclerView: RecyclerView

    private lateinit var date: String
    private lateinit var selectedCategory: CategoryEntity
    private var isIncome by Delegates.notNull<Boolean>()
    private var description: String? = null
    private var amount: Int? = null
    private val isNewRecord by extraRequired<Boolean>(ARG_IS_NEW_RECORD)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_fragment_record_form)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecordFormAdapter(this@RecordFormFragment)
            val divider = DividerDecoration(
                dividerColor = R.color.bg_gray.color(),
                dividerHeight = 4.dp()
            )
            addItemDecoration(divider)
        }

        getDataFromArguments()
        combinedItems()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_category_edit -> {
                // TODO: implement
                true
            }
            R.id.menu_category_delete -> {
                // TODO: implement
                true
            }
            R.id.menu_category_add -> {
                // TODO: implement
                true
            }
            else -> false
        }
    }

    private fun getDataFromArguments() {
        date = arguments?.getString(ARG_DATE, "") ?: ""
        description = arguments?.getString(ARG_DESCRIPTION)
        selectedCategory =
            (arguments?.getSerializable(ARG_CATEGORY) as? CategoryEntity) ?: categories[0]
        isIncome = arguments?.getBoolean(ARG_IS_INCOME, false) ?: false
        amount = arguments?.getInt(ARG_AMOUNT)
    }

    private fun combinedItems() {
        val items = listOf(
            RecordFormItem.Receipt(
                date = date,
                isIncome = isIncome,
                amount = if (isNewRecord) null else amount,
                description = description
            ),
            RecordFormItem.Category(categories, selectedCategory),
            RecordFormItem.Confirm(if (isNewRecord) "新增" else "完成")
        )
        (recyclerView.adapter as RecordFormAdapter).submitList(items)
    }

    override fun onMoreIconClicked(view: View) {
        val context = context ?: return
        PopupMenu(context, view).apply {
            setOnMenuItemClickListener(this@RecordFormFragment)
            gravity = Gravity.END
            inflate(R.menu.category)
            show()
        }
    }

    override fun onTypeChanged(isIncome: Boolean) {
        // TODO: implement
    }

    override fun onAmountChanged(amount: Int) {
        // TODO: implement
    }

    override fun onDescriptionDetailClicked() {
        // TODO: implement
    }

    override fun onCategoryChanged(category: CategoryEntity) {
        // TODO: implement
    }

    override fun onConfirmClicked() {
        // TODO: implement
    }
}
