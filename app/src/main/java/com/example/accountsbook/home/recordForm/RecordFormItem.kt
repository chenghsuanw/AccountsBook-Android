package com.example.accountsbook.home.recordForm

sealed class RecordFormItem {

    companion object {

        const val UNDEFINED = -1

        private val mapping =
            mapOf(
                Receipt::class.java to 0,
                Confirm::class.java to 99
            )

        fun getClazzFromViewType(viewType: Int): Class<out RecordFormItem>? {
            return mapping.entries.first { it.value == viewType }.key
        }
    }

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int

    val viewType: Int
        get() = mapping[this::class.java] ?: UNDEFINED

    data class Receipt(
        val date: String,
        val isIncome: Boolean? = false,
        val amount: Int?,
        val description: String?
    ) : RecordFormItem()

    data class Confirm(val text: String) : RecordFormItem()
}
