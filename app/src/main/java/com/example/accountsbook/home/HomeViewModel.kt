package com.example.accountsbook.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.accountsbook.database.DatabaseHelper
import com.example.accountsbook.database.RecordEntity
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _records = MutableLiveData<List<RecordEntity>>()
    val records: LiveData<List<RecordEntity>> = _records
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    private val _amount = MutableLiveData<Int>()
    val amount: LiveData<Int> = _amount

    fun updateRecordsOnDate(date: String) {
        viewModelScope.launch {
            _date.value = date
            DatabaseHelper.recordDao.getRecordsOnDate(date).let { recordList ->
                _records.value = recordList
                _amount.value = recordList.sumBy { record ->
                    if (record.isIncome) record.amount else -record.amount
                }
            }
        }
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            @Suppress("UNCHECKED_CAST")
            return HomeViewModel() as T
        }
    }
}
