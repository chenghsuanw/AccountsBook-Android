package com.example.accountsbook.navigation

import android.content.Intent
import com.example.accountsbook.BaseFragment

interface Navigator {

    fun execute(command: Command)

    sealed class Command {

        class PushFragment private constructor(
            val fragment: BaseFragment,
            val tag: String
        ) : Command() {

            class Builder(
                private val fragment: BaseFragment
            ) {

                private var tag: String? = null

                fun setTag(tag: String?): Builder {
                    this.tag = tag
                    return this
                }

                fun build(): PushFragment {
                    return PushFragment(
                        fragment = fragment,
                        tag = tag ?: fragment::class.java.simpleName
                    )
                }
            }
        }

        object PopFragment : Command()

        object PopAllFragment : Command()

        class LaunchActivity(
            val intent: Intent,
            val requestCode: Int? = null
        ) : Command()

        class ChangeTab(
            val tab: Tab
        ) : Command()
    }
}
