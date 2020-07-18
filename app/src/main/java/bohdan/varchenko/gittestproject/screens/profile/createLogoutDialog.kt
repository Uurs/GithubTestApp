package bohdan.varchenko.gittestproject.screens.profile

import android.content.Context
import androidx.appcompat.app.AppCompatDialog
import bohdan.varchenko.gittestproject.R
import kotlinx.android.synthetic.main.dialog_logout.*

fun createLogoutDialog(
    context: Context,
    onLogoutClick: () -> Unit
): AppCompatDialog =
    AppCompatDialog(context, R.style.CommonDialog)
        .apply {
            setContentView(R.layout.dialog_logout)
            setCanceledOnTouchOutside(true)
            bCancel.setOnClickListener { dismiss() }
            bLogout.setOnClickListener {
                dismiss()
                onLogoutClick()
            }
        }