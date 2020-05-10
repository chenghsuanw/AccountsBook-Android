package com.example.accountsbook.home.recordForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.BaseFragment
import com.example.accountsbook.R
import com.example.accountsbook.navigation.ToolbarConfig
import com.example.accountsbook.util.color
import com.example.accountsbook.util.dp
import com.example.accountsbook.view.DividerDecoration

class RecordFormFragment : BaseFragment() {

    companion object {
        fun newInstance() = RecordFormFragment()
    }

    override val toolbarConfig: ToolbarConfig?
        get() = ToolbarConfig(
            title = "紀錄",
            homeIcon = R.drawable.ic_back
        )

    private lateinit var recyclerView: RecyclerView

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
            adapter = RecordFormAdapter()
            val divider = DividerDecoration(
                dividerColor = R.color.bg_gray.color(),
                dividerHeight = 4.dp(),
                paddingStart = 32.dp()
            )
            addItemDecoration(divider)
        }
    }
}
