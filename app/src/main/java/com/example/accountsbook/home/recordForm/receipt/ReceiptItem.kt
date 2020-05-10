package com.example.accountsbook.home.recordForm.receipt

sealed class ReceiptItem {

    companion object {

        const val UNDEFINED = -1

        private val mapping =
            mapOf(
                Type::class.java to 0,
                Amount::class.java to 1,
                Description::class.java to 2
            )

        fun getClazzFromViewType(viewType: Int): Class<out ReceiptItem>? {
            return mapping.entries.first { it.value == viewType }.key
        }
    }

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int

    val viewType: Int
        get() = mapping[this::class.java] ?: UNDEFINED

    data class Type(val isIncome: Boolean?) : ReceiptItem()
    data class Amount(val amount: Int?) : ReceiptItem()
    data class Description(val description: String?) : ReceiptItem()
}
