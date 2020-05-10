package com.example.accountsbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.BaseFragment
import com.example.accountsbook.R
import com.example.accountsbook.navigation.ToolbarConfig
import com.example.accountsbook.util.color
import com.example.accountsbook.util.dp
import com.example.accountsbook.util.milliToDateString
import com.example.accountsbook.view.DividerDecoration
import java.util.GregorianCalendar
import kotlin.math.absoluteValue

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val toolbarConfig: ToolbarConfig?
        get() = ToolbarConfig(
            title = "薪資帳戶"
        )

    private lateinit var calendarView: CalendarView
    private lateinit var summaryLayout: ConstraintLayout
    private lateinit var summaryDateTv: TextView
    private lateinit var summaryAmountTv: TextView
    private lateinit var recordRecyclerView: RecyclerView

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.Factory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = view.findViewById(R.id.cv_fragment_home_calendar)
        summaryLayout = view.findViewById(R.id.summary_fragment_home)
        summaryDateTv = view.findViewById(R.id.tv_daily_summary_date)
        summaryAmountTv = view.findViewById(R.id.tv_daily_summary_amount)
        recordRecyclerView = view.findViewById(R.id.rv_fragment_records)

        recordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecordListAdapter()
            val divider = DividerDecoration(
                dividerColor = R.color.gray.color(),
                dividerHeight = 1.dp(),
                paddingStart = 32.dp()
            )
            addItemDecoration(divider)
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            GregorianCalendar(year, month, dayOfMonth).timeInMillis.apply {
                view.date = this
                viewModel.updateRecordsOnDate(this.milliToDateString())
            }
        }

        viewModel.records.observe(viewLifecycleOwner, Observer { records ->
            (recordRecyclerView.adapter as? RecordListAdapter)?.submitList(records)
            summaryLayout.isVisible = records.isNotEmpty()
        })
        viewModel.amount.observe(viewLifecycleOwner, Observer { amount ->
            summaryAmountTv.text = amount.absoluteValue.toString()
            summaryAmountTv.setTextColor(if (amount > 0) R.color.colorPrimary.color() else R.color.black.color())
        })
        viewModel.date.observe(viewLifecycleOwner, Observer { date ->
            summaryDateTv.text = date
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateRecordsOnDate(calendarView.date.milliToDateString())
    }
}
