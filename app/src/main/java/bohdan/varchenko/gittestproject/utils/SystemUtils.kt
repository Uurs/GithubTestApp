package bohdan.varchenko.gittestproject.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN
import android.widget.EditText
import androidx.core.content.ContextCompat

fun Context.hideKeyboard(input: EditText) {
    val manager = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        ?: return
    manager.hideSoftInputFromWindow(input.windowToken, RESULT_UNCHANGED_SHOWN)

}