package bohdan.varchenko.gittestproject.screens.profile

import android.content.Context
import androidx.appcompat.app.AppCompatDialog
import bohdan.varchenko.gittestproject.R
import kotlinx.android.synthetic.main.dialog_login.*

fun createLoginDialog(
    context: Context,
    onLoginClickListener: (token: String) -> Unit
): AppCompatDialog {
    return AppCompatDialog(context, R.style.CommonDialog)
        .apply {
            setContentView(R.layout.dialog_login)
            setCanceledOnTouchOutside(true)
            bLogin.setOnClickListener {
                dismiss()
                onLoginClickListener(
                    etToken.text?.toString() ?: ""
                )
            }
        }
}